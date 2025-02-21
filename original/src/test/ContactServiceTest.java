/* Joey Grippi
 *
 * CS-320
 *
 * Project One
 *
 * April 13, 2024
 */

package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import contact.Contact;
import contact.ContactService;

class ContactServiceTest {

    // Clear the array before each test
    @BeforeEach
    void setUp() throws Exception {
        ContactService.contacts.clear();
    }

	/**
	 * Testing the default constructor
	 */

	@DisplayName("Default constructor test")
    @Test
    public void testDefaultConstructor() {
        new ContactService();
	}

    /**
     * Testing if the contacts list is instantiated properly
     */

	@DisplayName("Contacts list test")
    @Test
    void testContactsList() {
        assertNotNull(ContactService.contacts);
    }

    /**
	 * Testing the addContact() function
	 * by first creating a new contact object
	 * then adding it to the array,
	 * then testing to validate the object is in the array
	 */

	@DisplayName("Add contact test")
	@Test
	void testAddContact() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Adding the object, then testing to validate it was added
		ContactService.addContact(testContact);
		assertTrue(ContactService.contacts.contains(testContact));
	}

	/**
	 * Testing the addContact() function
	 * to validate that it cannot add
	 * a duplicate contactID into the array
	 */

	@DisplayName("Unique contact ID test")
	@Test
	void testUniqueContactID() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Adding the object, then testing to validate it was added
		ContactService.addContact(testContact);
		assertTrue(ContactService.contacts.contains(testContact));

		// Creating a second testContact object with a duplicate ID
        Contact testContact2 = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Adding the object, then testing to validate it wasn't added due to a duplicate ID
        ContactService.addContact(testContact2);
		assertEquals(false, ContactService.contacts.contains(testContact2));
	}

   /**
    * Testing deleteContact by adding 2 contacts,
    * then deleting one and validating deletion
    * by checking the size of the array
    */

    @DisplayName("Delete contact test")
    @Test
    void testDeleteContact() {

        // First, validating that the array is empty
        assertTrue(ContactService.contacts.isEmpty());

		// Creating a testContact object
        Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

        // Adding the object, then testing to validate it was added
		ContactService.addContact(testContact);
        assertTrue(ContactService.addContact(testContact));
		assertTrue(ContactService.contacts.contains(testContact));

        // Creating a second testContact object
        Contact testContact2 = new Contact("2", "John", "Doe", "0123456789", "123 Loch Ness Road");

        // Adding the second object, then testing to validate it was added
		ContactService.addContact(testContact2);
        assertTrue(ContactService.addContact(testContact2));
		assertTrue(ContactService.contacts.contains(testContact2));

        // Validating the size of the array, then deleting and re-testing to validate deletion
        assertEquals(2, ContactService.contacts.size());
        ContactService.deleteContact("2");
        assertEquals(1, ContactService.contacts.size());
        assertFalse(ContactService.contacts.contains(testContact2));
    }

    /**
     * Testing deleteContact by trying to delete an contact that doesn't exist
     * and validating that the size of the contacts array remains the same
     */

     @DisplayName("Delete non-existent contact test")
     @Test
     void testDeleteNonExistentContact() {

        // First, validating that the array is empty
        assertTrue(ContactService.contacts.isEmpty());

		// Creating a testContact object
        Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

        // Adding the object, then testing to validate it was added
		ContactService.addContact(testContact);
        assertTrue(ContactService.addContact(testContact));
		assertTrue(ContactService.contacts.contains(testContact));

         // Validating the size of the array before trying to delete a non-existent contact
         assertEquals(1, ContactService.contacts.size());

         // Deleting a non-existent contact
         ContactService.deleteContact("2");

         // Validating that the size of the array remains the same
         assertEquals(1, ContactService.contacts.size());
     }

 	/**
 	 * Testing updateFirstName by adding a contact and validating,
 	 * then updating the first name and re-validating.
 	 * Also try to update first name of a non-existent contact.
 	 */

 	@DisplayName("Update first name test")
 	@Test
 	void testUpdateFirstName() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "Test", "Doe", "0123456789", "123 Loch Ness Road");

 		// Adding the object, then testing to validate it was added
 		ContactService.addContact(testContact);
 		assertTrue(ContactService.contacts.contains(testContact));

 		// Validating the test first name, then updating it and re-testing for validation
 		assertEquals("Test", ContactService.getFirstNameFromID("1"));
 		ContactService.updateFirstName("1", "Updated");
 		assertEquals("Updated", ContactService.getFirstNameFromID("1"));

 		// Trying to update the first name of a non-existent contact,
 		// then testing to ensure the correct first name remains the same
 		ContactService.updateFirstName("2", "Test2");
 		assertEquals("Updated", ContactService.getFirstNameFromID("1"));
 	}

 	/**
 	 * Testing the helper method getFirstNameFromID()
 	 * by creating a test contact, adding it to the list,
 	 * returning the ID, then trying to return a non-existent ID.
 	 */

 	@DisplayName("Get first name ID test")
 	@Test
 	void testGetFirstNameID() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "Test", "Doe", "0123456789", "123 Loch Ness Road");

 		// Adding the object, then testing to validate it was added
 		ContactService.addContact(testContact);
 		assertTrue(ContactService.contacts.contains(testContact));

 		// Getting first name by ID
 		String firstName = ContactService.getFirstNameFromID("1");

 		// Testing to ensure correct first name is returned
 		assertEquals("Test", firstName);

 		// Trying to get the first name of a non-existent contact
 		String firstName2 = ContactService.getFirstNameFromID("2");

 		// Testing to ensure null is returned
 		assertNull(firstName2);
 	}

 	/**
 	 * Testing updateLastName by adding a contact and validating,
 	 * then updating the last name and re-validating.
 	 * Also try to update last name of a non-existent contact.
 	 */

 	@DisplayName("Update last name test")
 	@Test
 	void testUpdateLastName() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "John", "Test", "0123456789", "123 Loch Ness Road");

 		// Adding the object, then testing to validate it was added
 		ContactService.addContact(testContact);
 		assertTrue(ContactService.contacts.contains(testContact));

 		// Validating the test last name, then updating it and re-testing for validation
 		assertEquals("Test", ContactService.getLastNameFromID("1"));
 		ContactService.updateLastName("1", "Updated");
 		assertEquals("Updated", ContactService.getLastNameFromID("1"));

 		// Trying to update the last name of a non-existent contact,
 		// then testing to ensure the correct last name remains the same
 		ContactService.updateLastName("2", "Test2");
 		assertEquals("Updated", ContactService.getLastNameFromID("1"));
 	}

 	/**
 	 * Testing the helper method getLastNameFromID()
 	 * by creating a test contact, adding it to the list,
 	 * returning the ID, then trying to return a non-existent ID.
 	 */

 	@DisplayName("Get last name ID test")
 	@Test
 	void testGetLastNameID() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "John", "Test", "0123456789", "123 Loch Ness Road");

 		// Adding the object, then testing to validate it was added
 		ContactService.addContact(testContact);
 		assertTrue(ContactService.contacts.contains(testContact));

 		// Getting first name by ID
 		String lastName = ContactService.getLastNameFromID("1");

 		// Testing to ensure correct last name is returned
 		assertEquals("Test", lastName);

 		// Trying to get the last name of a non-existent contact
 		String lastName2 = ContactService.getLastNameFromID("2");

 		// Testing to ensure null is returned
 		assertNull(lastName2);
 	}

 	/**
 	 * Testing updatePhone by adding a contact and validating,
 	 * then updating the phone and re-validating.
 	 * Also try to update phone of a non-existent contact.
 	 */

 	@DisplayName("Update phone test")
 	@Test
 	void testUpdatePhone() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

 		// Adding the object, then testing to validate it was added
 		ContactService.addContact(testContact);
 		assertTrue(ContactService.contacts.contains(testContact));

 		// Validating the test phone, then updating it and re-testing for validation
 		assertEquals("0123456789", ContactService.getPhoneFromID("1"));
 		ContactService.updatePhone("1", "9999999999");
 		assertEquals("9999999999", ContactService.getPhoneFromID("1"));

 		// Trying to update the phone of a non-existent contact,
 		// then testing to ensure the correct phone remains the same
 		ContactService.updatePhone("2", "1111111111");
 		assertEquals("9999999999", ContactService.getPhoneFromID("1"));
 	}

 	/**
 	 * Testing the helper method getPhoneFromID()
 	 * by creating a test contact, adding it to the list,
 	 * returning the ID, then trying to return a non-existent ID.
 	 */

 	@DisplayName("Get phone ID test")
 	@Test
 	void testGetPhoneID() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

 		// Adding the object, then testing to validate it was added
 		ContactService.addContact(testContact);
 		assertTrue(ContactService.contacts.contains(testContact));

 		// Getting phone by ID
 		String phone = ContactService.getPhoneFromID("1");

 		// Testing to ensure correct phone is returned
 		assertEquals("0123456789", phone);

 		// Trying to get the phone of a non-existent contact
 		String phone2 = ContactService.getPhoneFromID("2");

 		// Testing to ensure null is returned
 		assertNull(phone2);
 	}

 	/**
 	 * Testing updateAddress by adding a contact and validating,
 	 * then updating the address and re-validating.
 	 * Also try to update address of a non-existent contact.
 	 */

 	@DisplayName("Update address test")
 	@Test
 	void testUpdateAddress() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "Test", "Doe", "0123456789", "123 Test Road");

 		// Adding the object, then testing to validate it was added
 		ContactService.addContact(testContact);
 		assertTrue(ContactService.contacts.contains(testContact));

 		// Validating the test address, then updating it and re-testing for validation
 		assertEquals("123 Test Road", ContactService.getAddressFromID("1"));
 		ContactService.updateAddress("1", "456 Updated Street");
 		assertEquals("456 Updated Street", ContactService.getAddressFromID("1"));

 		// Trying to update the address of a non-existent contact,
 		// then testing to ensure the correct address remains the same
 		ContactService.updateAddress("2", "Test Address");
 		assertEquals("456 Updated Street", ContactService.getAddressFromID("1"));
 	}

 	/**
 	 * Testing the helper method getAddressFromID()
 	 * by creating a test contact, adding it to the list,
 	 * returning the ID, then trying to return a non-existent ID.
 	 */

 	@DisplayName("Get address ID test")
 	@Test
 	void testGetAddressID() {

		// Creating a testContact object
        Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Test Road");

 		// Adding the object, then testing to validate it was added
 		ContactService.addContact(testContact);
 		assertTrue(ContactService.contacts.contains(testContact));

 		// Getting address by ID
 		String address = ContactService.getAddressFromID("1");

 		// Testing to ensure correct address is returned
 		assertEquals("123 Test Road", address);

 		// Trying to get the address of a non-existent contact
 		String address2 = ContactService.getAddressFromID("2");

 		// Testing to ensure null is returned
 		assertNull(address2);
 	}
}