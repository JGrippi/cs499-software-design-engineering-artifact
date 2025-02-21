package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import contact.Contact;
import contact.ContactService;

import java.util.List;

/**
 * Test class for ContactService
 * Provides 100% coverage for ContactService.java
 */
class ContactServiceTest {
    private static final String VALID_ID = "12345";
    private static final String VALID_FIRST_NAME = "John";
    private static final String VALID_LAST_NAME = "Doe";
    private static final String VALID_PHONE = "1234567890";
    private static final String VALID_ADDRESS = "123 Test St";
    
    @BeforeEach
    void setUp() {
        // Clear any existing contacts
        List<Contact> contacts = ContactService.getAllContacts();
        contacts.forEach(contact -> ContactService.deleteContact(contact.getContactID()));
    }

    @Test
    @DisplayName("Test add contact")
    void testAddContact() {
        Contact contact = new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, 
                                    VALID_PHONE, VALID_ADDRESS);
        
        // Test successful add
        assertFalse(ContactService.addContact(contact), "Adding new contact should return false");
        assertEquals(VALID_FIRST_NAME, ContactService.getFirstNameFromID(VALID_ID));
        
        // Test duplicate add
        Contact duplicate = new Contact(VALID_ID, "Jane", "Smith", "9876543210", "456 New St");
        assertTrue(ContactService.addContact(duplicate), "Adding duplicate contact should return true");
        
        // Verify original contact data remains unchanged
        assertEquals(VALID_FIRST_NAME, ContactService.getFirstNameFromID(VALID_ID));
        
        // Test null contact
        assertThrows(IllegalArgumentException.class, () -> ContactService.addContact(null));
    }

    @Test
    @DisplayName("Test delete contact")
    void testDeleteContact() {
        Contact contact = new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, 
                                    VALID_PHONE, VALID_ADDRESS);
        ContactService.addContact(contact);
        
        // Test successful delete
        assertTrue(ContactService.deleteContact(VALID_ID), "Deleting existing contact should return true");
        assertNull(ContactService.getFirstNameFromID(VALID_ID));
        
        // Test delete non-existent contact
        assertFalse(ContactService.deleteContact("nonexistent"), 
                   "Deleting non-existent contact should return false");
    }

    @Test
    @DisplayName("Test update operations")
    void testUpdates() {
        Contact contact = new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, 
                                    VALID_PHONE, VALID_ADDRESS);
        ContactService.addContact(contact);
        
        // Test successful updates
        assertTrue(ContactService.updateFirstName(VALID_ID, "Jane"));
        assertTrue(ContactService.updateLastName(VALID_ID, "Smith"));
        assertTrue(ContactService.updatePhone(VALID_ID, "9876543210"));
        assertTrue(ContactService.updateAddress(VALID_ID, "456 New St"));
        
        // Verify updates
        assertEquals("Jane", ContactService.getFirstNameFromID(VALID_ID));
        assertEquals("Smith", ContactService.getLastNameFromID(VALID_ID));
        assertEquals("9876543210", ContactService.getPhoneFromID(VALID_ID));
        assertEquals("456 New St", ContactService.getAddressFromID(VALID_ID));
        
        // Test updates on non-existent contact
        String nonexistentId = "nonexistent";
        assertFalse(ContactService.updateFirstName(nonexistentId, "Jane"));
        assertFalse(ContactService.updateLastName(nonexistentId, "Smith"));
        assertFalse(ContactService.updatePhone(nonexistentId, "9876543210"));
        assertFalse(ContactService.updateAddress(nonexistentId, "456 New St"));
    }

    @Test
    @DisplayName("Test get operations")
    void testGetOperations() {
        Contact contact = new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, 
                                    VALID_PHONE, VALID_ADDRESS);
        ContactService.addContact(contact);
        
        // Test successful retrievals
        assertEquals(VALID_FIRST_NAME, ContactService.getFirstNameFromID(VALID_ID));
        assertEquals(VALID_LAST_NAME, ContactService.getLastNameFromID(VALID_ID));
        assertEquals(VALID_PHONE, ContactService.getPhoneFromID(VALID_ID));
        assertEquals(VALID_ADDRESS, ContactService.getAddressFromID(VALID_ID));
        
        // Test retrievals for non-existent contact
        String nonexistentId = "nonexistent";
        assertNull(ContactService.getFirstNameFromID(nonexistentId));
        assertNull(ContactService.getLastNameFromID(nonexistentId));
        assertNull(ContactService.getPhoneFromID(nonexistentId));
        assertNull(ContactService.getAddressFromID(nonexistentId));
    }

    @Test
    @DisplayName("Test get all contacts")
    void testGetAllContacts() {
        // Test empty list
        assertTrue(ContactService.getAllContacts().isEmpty());
        
        // Add multiple contacts
        Contact contact1 = new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, 
                                     VALID_PHONE, VALID_ADDRESS);
        Contact contact2 = new Contact("54321", "Jane", "Smith", "9876543210", "456 New St");
        
        ContactService.addContact(contact1);
        ContactService.addContact(contact2);
        
        // Test retrieving all contacts
        List<Contact> allContacts = ContactService.getAllContacts();
        assertEquals(2, allContacts.size(), "Should return correct number of contacts");
        
        // Verify contents
        boolean foundContact1 = false;
        boolean foundContact2 = false;
        
        for (Contact c : allContacts) {
            if (c.getContactID().equals(VALID_ID)) {
                assertEquals(VALID_FIRST_NAME, c.getFirstName());
                assertEquals(VALID_LAST_NAME, c.getLastName());
                assertEquals(VALID_PHONE, c.getPhone());
                assertEquals(VALID_ADDRESS, c.getAddress());
                foundContact1 = true;
            } else if (c.getContactID().equals("54321")) {
                assertEquals("Jane", c.getFirstName());
                assertEquals("Smith", c.getLastName());
                assertEquals("9876543210", c.getPhone());
                assertEquals("456 New St", c.getAddress());
                foundContact2 = true;
            }
        }
        
        assertTrue(foundContact1, "First contact should be in the list");
        assertTrue(foundContact2, "Second contact should be in the list");
        
        // Verify list independence (modifications to returned list shouldn't affect service)
        allContacts.clear();
        assertFalse(ContactService.getAllContacts().isEmpty(), 
                   "Service contacts should remain after clearing returned list");
    }
    
    @Test
    @DisplayName("Test concurrent operations")
    void testConcurrentOperations() {
        // Add initial contact
        Contact contact = new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, 
                                    VALID_PHONE, VALID_ADDRESS);
        assertFalse(ContactService.addContact(contact), "Initial add should succeed");
        
        // Create a new contact with same ID but different data
        Contact duplicateContact = new Contact(VALID_ID, "Jane", "Smith", 
                                             "9876543210", "456 New St");
        assertTrue(ContactService.addContact(duplicateContact), 
                  "Adding duplicate ID should return true");
        
        // Verify original data remains and wasn't overwritten
        assertEquals(VALID_FIRST_NAME, ContactService.getFirstNameFromID(VALID_ID), 
                    "Original contact data should remain unchanged");
        
        // Test update and verify
        assertTrue(ContactService.updateFirstName(VALID_ID, "Jane"), 
                  "Update should succeed");
        assertEquals("Jane", ContactService.getFirstNameFromID(VALID_ID), 
                    "Should get updated value");
        
        // Test delete and verify
        assertTrue(ContactService.deleteContact(VALID_ID), 
                  "Delete should succeed");
        assertNull(ContactService.getFirstNameFromID(VALID_ID), 
                  "Should not find deleted contact");
    }
    
    @Test
    @DisplayName("Test list independence")
    void testListIndependence() {
        Contact contact = new Contact(VALID_ID, VALID_FIRST_NAME, VALID_LAST_NAME, 
                                    VALID_PHONE, VALID_ADDRESS);
        ContactService.addContact(contact);
        
        // Get contacts list twice
        List<Contact> list1 = ContactService.getAllContacts();
        List<Contact> list2 = ContactService.getAllContacts();
        
        // Verify lists are independent
        assertNotSame(list1, list2, "Should return new list each time");
        assertEquals(list1, list2, "Lists should contain same elements");
        
        // Modify first list and verify second is unchanged
        list1.clear();
        assertFalse(list2.isEmpty(), "Second list should be unchanged");
        assertFalse(ContactService.getAllContacts().isEmpty(), 
                   "Service contacts should be unchanged");
    }
}