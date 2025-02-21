package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import contact.Contact;

/**
 * Test class for Contact
 * Provides 100% coverage for Contact.java
 */
class ContactTest {
    private static final String VALID_ID = "12345";
    private static final String VALID_FIRST_NAME = "John";
    private static final String VALID_LAST_NAME = "Doe";
    private static final String VALID_PHONE = "1234567890";
    private static final String VALID_ADDRESS = "123 Test St";
    
    private Contact testContact;
    
    @BeforeEach
    void setUp() {
        testContact = new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, 
                                VALID_PHONE, VALID_ADDRESS);
    }

    @Test
    @DisplayName("Test valid contact creation")
    void testValidConstruction() {
        Contact contact = new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME,
                                    VALID_PHONE, VALID_ADDRESS);
        
        assertEquals(VALID_ID, contact.getContactID());
        assertEquals(VALID_FIRST_NAME, contact.getFirstName());
        assertEquals(VALID_LAST_NAME, contact.getLastName());
        assertEquals(VALID_PHONE, contact.getPhone());
        assertEquals(VALID_ADDRESS, contact.getAddress());
    }

    @Test
    @DisplayName("Test contact ID validation")
    void testContactIDValidation() {
        // Test null ID
        Exception e1 = assertThrows(IllegalArgumentException.class, () ->
            new Contact(null, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS));
        assertTrue(e1.getMessage().contains("Contact ID cannot be null"));

        // Test empty ID
        Exception e2 = assertThrows(IllegalArgumentException.class, () ->
            new Contact("", VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS));
        assertTrue(e2.getMessage().contains("Contact ID cannot be null or empty"));

        // Test ID too long
        Exception e3 = assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345678901", VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS));
        assertTrue(e3.getMessage().contains("Contact ID cannot exceed 10 characters"));
    }

    @Test
    @DisplayName("Test first name validation")
    void testFirstNameValidation() {
        // Test null first name
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, null, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS));

        // Test empty first name
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, "", VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS));

        // Test first name too long
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, "JohnJohnJohn", VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS));

        // Test first name setter validation
        assertThrows(IllegalArgumentException.class, () -> testContact.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> testContact.setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> testContact.setFirstName("JohnJohnJohn"));

        // Test valid first name update
        testContact.setFirstName("Jane");
        assertEquals("Jane", testContact.getFirstName());
    }

    @Test
    @DisplayName("Test last name validation")
    void testLastNameValidation() {
        // Test null last name
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, null, VALID_PHONE, VALID_ADDRESS));

        // Test empty last name
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, "", VALID_PHONE, VALID_ADDRESS));

        // Test last name too long
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, "SmithSmithSmith", VALID_PHONE, VALID_ADDRESS));

        // Test last name setter validation
        assertThrows(IllegalArgumentException.class, () -> testContact.setLastName(null));
        assertThrows(IllegalArgumentException.class, () -> testContact.setLastName(""));
        assertThrows(IllegalArgumentException.class, () -> testContact.setLastName("SmithSmithSmith"));

        // Test valid last name update
        testContact.setLastName("Smith");
        assertEquals("Smith", testContact.getLastName());
    }

    @Test
    @DisplayName("Test phone validation")
    void testPhoneValidation() {
        // Test null phone
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, null, VALID_ADDRESS));

        // Test empty phone
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, "", VALID_ADDRESS));

        // Test phone wrong length
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, "123456789", VALID_ADDRESS));

        // Test non-numeric phone
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, "123abc4567", VALID_ADDRESS));

        // Test phone setter validation
        assertThrows(IllegalArgumentException.class, () -> testContact.setPhone(null));
        assertThrows(IllegalArgumentException.class, () -> testContact.setPhone(""));
        assertThrows(IllegalArgumentException.class, () -> testContact.setPhone("123456789"));
        assertThrows(IllegalArgumentException.class, () -> testContact.setPhone("123abc4567"));

        // Test valid phone update
        testContact.setPhone("9876543210");
        assertEquals("9876543210", testContact.getPhone());
    }

    @Test
    @DisplayName("Test address validation")
    void testAddressValidation() {
        // Test null address
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, null));

        // Test empty address
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, ""));

        // Test address too long
        String longAddress = "123 This Address Is Way Too Long To Be Valid St";
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, longAddress));

        // Test address setter validation
        assertThrows(IllegalArgumentException.class, () -> testContact.setAddress(null));
        assertThrows(IllegalArgumentException.class, () -> testContact.setAddress(""));
        assertThrows(IllegalArgumentException.class, () -> testContact.setAddress(longAddress));

        // Test valid address update
        testContact.setAddress("456 New St");
        assertEquals("456 New St", testContact.getAddress());
    }

    @Test
    @DisplayName("Test toString implementation")
    void testToString() {
        String expected = String.format("Contact[ID=%s, firstName=%s, lastName=%s, phone=%s, address=%s]",
                                      VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
        assertEquals(expected, testContact.toString());
    }

    @SuppressWarnings("unlikely-arg-type")
	@Test
    @DisplayName("Test equals and hashCode")
    void testEqualsAndHashCode() {
        Contact sameIdContact = new Contact(VALID_ID, "Jane", "Smith", "9876543210", "456 New St");
        Contact differentContact = new Contact("54321", VALID_FIRST_NAME, VALID_LAST_NAME, 
                                             VALID_PHONE, VALID_ADDRESS);

        // Test equals
        assertTrue(testContact.equals(testContact)); // Same object
        assertTrue(testContact.equals(sameIdContact)); // Same ID
        assertFalse(testContact.equals(differentContact)); // Different ID
        assertFalse(testContact.equals(null)); // null
        assertFalse(testContact.equals("not a contact")); // Different type

        // Test hashCode
        assertEquals(testContact.hashCode(), sameIdContact.hashCode());
        assertNotEquals(testContact.hashCode(), differentContact.hashCode());
    }
}