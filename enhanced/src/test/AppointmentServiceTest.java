package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import appointment.Appointment;
import appointment.AppointmentService;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

/**
 * Test class for AppointmentService
 * Provides 100% coverage for AppointmentService.java
 */
class AppointmentServiceTest {
    private static final String VALID_ID = "12345";
    private static final String VALID_DESC = "Valid Description";
    private Date validFutureDate;
    
    @BeforeEach
    void setUp() {
        // Clear any existing appointments
        List<Appointment> appointments = AppointmentService.getAllAppointments();
        appointments.forEach(appt -> AppointmentService.deleteAppt(appt.getApptID()));
        
        // Create a date 1 day in the future
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        validFutureDate = cal.getTime();
    }

    @Test
    @DisplayName("Test add appointment")
    void testAddAppointment() {
        Appointment appt = new Appointment(VALID_ID, validFutureDate, VALID_DESC);
        
        // Test successful add
        assertFalse(AppointmentService.addAppt(appt), "Adding new appointment should return false");
        Appointment retrieved = AppointmentService.getAppt(VALID_ID);
        assertNotNull(retrieved);
        assertEquals(VALID_DESC, retrieved.getApptDesc());
        
        // Test duplicate add
        Appointment duplicate = new Appointment(VALID_ID, validFutureDate, "Different Desc");
        assertTrue(AppointmentService.addAppt(duplicate), "Adding duplicate appointment should return true");
        
        // Verify original appointment data remains unchanged
        retrieved = AppointmentService.getAppt(VALID_ID);
        assertEquals(VALID_DESC, retrieved.getApptDesc());
        
        // Test null appointment
        assertThrows(IllegalArgumentException.class, () -> AppointmentService.addAppt(null));
    }

    @Test
    @DisplayName("Test delete appointment")
    void testDeleteAppointment() {
        Appointment appt = new Appointment(VALID_ID, validFutureDate, VALID_DESC);
        AppointmentService.addAppt(appt);
        
        // Test successful delete
        assertTrue(AppointmentService.deleteAppt(VALID_ID), "Deleting existing appointment should return true");
        assertNull(AppointmentService.getAppt(VALID_ID));
        
        // Test delete non-existent appointment
        assertFalse(AppointmentService.deleteAppt("nonexistent"), 
                   "Deleting non-existent appointment should return false");
    }

    @Test
    @DisplayName("Test get appointment")
    void testGetAppointment() {
        Appointment appt = new Appointment(VALID_ID, validFutureDate, VALID_DESC);
        AppointmentService.addAppt(appt);
        
        // Test successful retrieval
        Appointment retrieved = AppointmentService.getAppt(VALID_ID);
        assertNotNull(retrieved);
        assertEquals(VALID_ID, retrieved.getApptID());
        assertEquals(VALID_DESC, retrieved.getApptDesc());
        
        // Test retrieval of non-existent appointment
        assertNull(AppointmentService.getAppt("nonexistent"));
        
        // Verify appointment independence (modifications shouldn't affect stored appointment)
        Date retrievedDate = retrieved.getApptDate();
        retrievedDate.setTime(retrievedDate.getTime() + 1000);
        Appointment retrievedAgain = AppointmentService.getAppt(VALID_ID);
        assertNotEquals(retrievedDate, retrievedAgain.getApptDate());
    }

    @Test
    @DisplayName("Test get all appointments")
    void testGetAllAppointments() {
        // Test empty list
        assertTrue(AppointmentService.getAllAppointments().isEmpty());
        
        // Add multiple appointments
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date differentDate = cal.getTime();
        
        Appointment appt1 = new Appointment(VALID_ID, validFutureDate, VALID_DESC);
        Appointment appt2 = new Appointment("54321", differentDate, "Different Desc");
        
        AppointmentService.addAppt(appt1);
        AppointmentService.addAppt(appt2);
        
        // Test retrieving all appointments
        List<Appointment> allAppts = AppointmentService.getAllAppointments();
        assertEquals(2, allAppts.size(), "Should return correct number of appointments");
        
        // Verify contents
        boolean foundAppt1 = false;
        boolean foundAppt2 = false;
        
        for (Appointment a : allAppts) {
            if (a.getApptID().equals(VALID_ID)) {
                assertEquals(VALID_DESC, a.getApptDesc());
                foundAppt1 = true;
            } else if (a.getApptID().equals("54321")) {
                assertEquals("Different Desc", a.getApptDesc());
                foundAppt2 = true;
            }
        }
        
        assertTrue(foundAppt1, "First appointment should be in the list");
        assertTrue(foundAppt2, "Second appointment should be in the list");
        
        // Verify list independence
        allAppts.clear();
        assertFalse(AppointmentService.getAllAppointments().isEmpty(), 
                   "Service appointments should remain after clearing returned list");
    }

    @Test
    @DisplayName("Test get appointments by date")
    void testGetAppointmentsByDate() {
        // Create appointments on different dates
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DAY_OF_MONTH, 1);
        Date date1 = cal1.getTime();
        
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DAY_OF_MONTH, 2);
        Date date2 = cal2.getTime();
        
        Appointment appt1 = new Appointment("1", date1, "Desc 1");
        Appointment appt2 = new Appointment("2", date1, "Desc 2"); // Same day as appt1
        Appointment appt3 = new Appointment("3", date2, "Desc 3"); // Different day
        
        AppointmentService.addAppt(appt1);
        AppointmentService.addAppt(appt2);
        AppointmentService.addAppt(appt3);
        
        // Test getting appointments for date1
        List<Appointment> date1Appts = AppointmentService.getAppointmentsByDate(date1);
        assertEquals(2, date1Appts.size());
        assertTrue(date1Appts.stream().anyMatch(a -> a.getApptID().equals("1")));
        assertTrue(date1Appts.stream().anyMatch(a -> a.getApptID().equals("2")));
        
        // Test getting appointments for date2
        List<Appointment> date2Appts = AppointmentService.getAppointmentsByDate(date2);
        assertEquals(1, date2Appts.size());
        assertTrue(date2Appts.stream().anyMatch(a -> a.getApptID().equals("3")));
        
        // Test getting appointments for date with no appointments
        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.DAY_OF_MONTH, 3);
        Date date3 = cal3.getTime();
        List<Appointment> date3Appts = AppointmentService.getAppointmentsByDate(date3);
        assertTrue(date3Appts.isEmpty());
        
        // Test null date
        assertThrows(IllegalArgumentException.class, () -> 
            AppointmentService.getAppointmentsByDate(null));
            
        // Test null date comparison
        try {
            java.lang.reflect.Method isSameDay = AppointmentService.class.getDeclaredMethod(
                "isSameDay", Date.class, Date.class);
            isSameDay.setAccessible(true);
            
            // Test both dates null
            assertFalse((Boolean)isSameDay.invoke(null, null, null));
            
            // Test first date null
            assertFalse((Boolean)isSameDay.invoke(null, null, date1));
            
            // Test second date null
            assertFalse((Boolean)isSameDay.invoke(null, date1, null));
            
        } catch (Exception e) {
            fail("Failed to test null date comparison: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test list independence")
    void testListIndependence() {
        Appointment appt = new Appointment(VALID_ID, validFutureDate, VALID_DESC);
        AppointmentService.addAppt(appt);
        
        // Get appointments list twice
        List<Appointment> list1 = AppointmentService.getAllAppointments();
        List<Appointment> list2 = AppointmentService.getAllAppointments();
        
        // Verify lists are independent
        assertNotSame(list1, list2, "Should return new list each time");
        assertEquals(list1.size(), list2.size(), "Lists should contain same number of elements");
        
        // Modify first list and verify second is unchanged
        list1.clear();
        assertFalse(list2.isEmpty(), "Second list should be unchanged");
        assertFalse(AppointmentService.getAllAppointments().isEmpty(), 
                   "Service appointments should be unchanged");
    }
}