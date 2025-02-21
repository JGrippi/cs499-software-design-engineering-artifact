package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import appointment.Appointment;

import java.util.Date;
import java.util.Calendar;

/**
 * Test class for Appointment
 * Provides 100% coverage for Appointment.java
 */
class AppointmentTest {
    private static final String VALID_ID = "12345";
    private static final String VALID_DESC = "Valid Description";
    private Date validFutureDate;
    private Appointment testAppt;
    
    @BeforeEach
    void setUp() {
        // Create a date 1 day in the future
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        validFutureDate = cal.getTime();
        
        testAppt = new Appointment(VALID_ID, validFutureDate, VALID_DESC);
    }

    @Test
    @DisplayName("Test valid appointment creation")
    void testValidConstruction() {
        Appointment appointment = new Appointment(VALID_ID, validFutureDate, VALID_DESC);
        
        assertEquals(VALID_ID, appointment.getApptID());
        assertEquals(validFutureDate, appointment.getApptDate());
        assertEquals(VALID_DESC, appointment.getApptDesc());
    }

    @Test
    @DisplayName("Test appointment ID validation")
    void testAppointmentIDValidation() {
        // Test null ID
        Exception e1 = assertThrows(IllegalArgumentException.class, () ->
            new Appointment(null, validFutureDate, VALID_DESC));
        assertTrue(e1.getMessage().contains("Appointment ID cannot be null"));

        // Test empty ID
        Exception e2 = assertThrows(IllegalArgumentException.class, () ->
            new Appointment("", validFutureDate, VALID_DESC));
        assertTrue(e2.getMessage().contains("cannot be null or empty"));

        // Test ID too long
        Exception e3 = assertThrows(IllegalArgumentException.class, () ->
            new Appointment("12345678901", validFutureDate, VALID_DESC));
        assertTrue(e3.getMessage().contains("cannot exceed 10 characters"));
    }

    @Test
    @DisplayName("Test date validation")
    void testDateValidation() {
        // Test null date
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment(VALID_ID, null, VALID_DESC));

        // Test past date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date pastDate = new Date(cal.getTimeInMillis());
        
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Appointment(VALID_ID, pastDate, VALID_DESC));
        assertTrue(e.getMessage().contains("must be in the future"));

        // Test current date
        // First create the test date
        Date testDate = new Date();
        try {
            Thread.sleep(100); // Sleep longer to ensure time difference
            new Appointment(VALID_ID, testDate, VALID_DESC);
            fail("Should have thrown IllegalArgumentException for current/past date");
        } catch (IllegalArgumentException | InterruptedException expected) {
            assertTrue(expected.getMessage().contains("must be in the future"));
        }

        // Verify that a future date is accepted
        Calendar futureCal = Calendar.getInstance();
        futureCal.add(Calendar.HOUR, 24); // Add 24 hours to ensure it's in the future
        Date futureDate = futureCal.getTime();
        assertDoesNotThrow(() -> 
            new Appointment(VALID_ID, futureDate, VALID_DESC),
            "Should accept appointment with future date");
    }

    @Test
    @DisplayName("Test field exact length validation")
    void testExactLengthValidation() {
        // Use reflection to access private validateField method
        try {
            java.lang.reflect.Method validateField = Appointment.class.getDeclaredMethod(
                "validateField", String.class, String.class, int.class, boolean.class);
            validateField.setAccessible(true);

            // Test shorter than required length
            try {
                validateField.invoke(testAppt, "Test Field", "1234", 5, true);
                fail("Should have thrown an exception");
            } catch (Exception e) {
                assertTrue(e.getCause() instanceof IllegalArgumentException);
                assertTrue(e.getCause().getMessage().contains("must be exactly 5 characters"));
            }

            // Test longer than required length
            try {
                validateField.invoke(testAppt, "Test Field", "123456", 5, true);
                fail("Should have thrown an exception");
            } catch (Exception e) {
                assertTrue(e.getCause() instanceof IllegalArgumentException);
                assertTrue(e.getCause().getMessage().contains("must be exactly 5 characters"));
            }

            // Test exact length
            Object result = validateField.invoke(testAppt, "Test Field", "12345", 5, true);
            assertEquals("12345", result);

        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test description validation")
    void testDescriptionValidation() {
        // Test null description
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment(VALID_ID, validFutureDate, null));

        // Test empty description
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment(VALID_ID, validFutureDate, ""));

        // Test description too long
        String longDesc = "This description is way too long to be valid as it exceeds fifty chars";
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment(VALID_ID, validFutureDate, longDesc));

        // Test description setter validation
        assertThrows(IllegalArgumentException.class, () -> 
            testAppt.setApptDesc(null));
        assertThrows(IllegalArgumentException.class, () -> 
            testAppt.setApptDesc(""));
        assertThrows(IllegalArgumentException.class, () -> 
            testAppt.setApptDesc(longDesc));

        // Test valid description update
        testAppt.setApptDesc("New valid description");
        assertEquals("New valid description", testAppt.getApptDesc());
    }

    @Test
    @DisplayName("Test date setter")
    void testDateSetter() {
        // Test setting null date
        assertThrows(IllegalArgumentException.class, () ->
            testAppt.setApptDate(null));

        // Test setting past date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date pastDate = cal.getTime();
        
        assertThrows(IllegalArgumentException.class, () ->
            testAppt.setApptDate(pastDate));

        // Test setting valid future date
        Calendar futureCal = Calendar.getInstance();
        futureCal.add(Calendar.DAY_OF_MONTH, 2);
        Date newFutureDate = futureCal.getTime();
        
        testAppt.setApptDate(newFutureDate);
        assertEquals(newFutureDate, testAppt.getApptDate());
    }

    @Test
    @DisplayName("Test date immutability")
    void testDateImmutability() {
        Date originalDate = testAppt.getApptDate();
        Date retrievedDate = testAppt.getApptDate();
        
        // Verify that modifying the retrieved date doesn't affect the appointment
        retrievedDate.setTime(retrievedDate.getTime() + 1000);
        assertNotEquals(retrievedDate, testAppt.getApptDate());
        assertEquals(originalDate, testAppt.getApptDate());
    }

    @SuppressWarnings("unlikely-arg-type")
	@Test
    @DisplayName("Test equals and hashCode")
    void testEqualsAndHashCode() {
        // Create appointments with same ID but different data
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date differentDate = cal.getTime();
        
        Appointment sameIdAppt = new Appointment(VALID_ID, differentDate, "Different Desc");
        Appointment differentAppt = new Appointment("54321", validFutureDate, VALID_DESC);

        // Test equals
        assertTrue(testAppt.equals(testAppt)); // Same object
        assertTrue(testAppt.equals(sameIdAppt)); // Same ID
        assertFalse(testAppt.equals(differentAppt)); // Different ID
        assertFalse(testAppt.equals(null)); // null
        assertFalse(testAppt.equals("not an appointment")); // Different type

        // Test hashCode
        assertEquals(testAppt.hashCode(), sameIdAppt.hashCode());
        assertNotEquals(testAppt.hashCode(), differentAppt.hashCode());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        String expected = String.format("Appointment[ID=%s, date=%s, description=%s]",
                                      VALID_ID, validFutureDate, VALID_DESC);
        assertEquals(expected, testAppt.toString());
    }
}