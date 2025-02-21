/* Joey Grippi
 *
 * CS-320
 *
 * Project One
 *
 * April 13, 2024
 */

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import contact.Contact;

class ContactTest {

	/**
	 * Testing with a valid constructor for contact object creation
	 */

	@DisplayName("Valid constructor test")
    @Test
    void testValidConstructor() {

        // Creating a testContact object
        Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Testing that the test contact object has a valid ID, first name, last name, phone, and address
        assertEquals("1", testContact.getContactID());
        assertEquals("John", testContact.getFirstName());
        assertEquals("Doe", testContact.getLastName());
        assertEquals("0123456789", testContact.getPhone());
        assertEquals("123 Loch Ness Road", testContact.getAddress());
    }

	/**
	 * Testing the boundary values of the contactID
	 */

	@DisplayName("Contact ID Boundary test")
	@Test
	void testContactIDBoundary() {

		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("", "John", "Doe", "0123456789", "123 Loch Ness Road");
	    });

		// Testing the minimum length for the contactID
		new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Testing the maximum length for the contactID
		new Contact("1234567890", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Testing that an exception is thrown for a contactID over 10 characters
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("01234567890", "John", "Doe", "0123456789", "123 Loch Ness Road");
	    });
	}

	/**
	 * Testing the boundary values of firstName
	 */

	@DisplayName("First Name Boundary test")
	@Test
	void testFirstNameBoundary() {

		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "", "Doe", "0123456789", "123 Loch Ness Road");
	    });

		// Testing the minimum length for first name
		new Contact("1", "A", "Doe", "0123456789", "123 Loch Ness Road");

		// Testing the maximum length for first name
		new Contact("1", "ABCDEFGHIJ", "Doe", "0123456789", "123 Loch Ness Road");

		// Testing that an exception is thrown for a first name over 10 characters
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "ABCDEFGHIJK", "Doe", "0123456789", "123 Loch Ness Road");
	    });
	}

	/**
	 * Testing the boundary values of lastName
	 */

	@DisplayName("Last Name Boundary test")
	@Test
	void testLastNameBoundary() {

		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "", "0123456789", "123 Loch Ness Road");
	    });

		// Testing the minimum length for last name
		new Contact("1", "John", "A", "0123456789", "123 Loch Ness Road");

		// Testing the maximum length for last name
		new Contact("1", "John", "ABCDEFGHIJ", "0123456789", "123 Loch Ness Road");

		// Testing that an exception is thrown for a last name over 10 characters
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "ABCDEFGHIJK", "0123456789", "123 Loch Ness Road");
	    });
	}

	/**
	 * Testing the boundary values of phone
	 */

	@DisplayName("Phone Boundary test")
	@Test
	void testPhoneBoundary() {

		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "Doe", "", "123 Loch Ness Road");
	    });

		// Testing the exact length for the phone number
		new Contact("1", "A", "Doe", "1111111111", "123 Loch Ness Road");

		// Testing that an exception is thrown for a phone number under 10 characters
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "Doe", "000000000", "123 Loch Ness Road");
	    });

		// Testing that an exception is thrown for a phone number over 10 characters
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "Doe", "00000000000", "123 Loch Ness Road");
	    });
	}

	/**
	 * Testing the boundary values of address
	 */

	@DisplayName("Address Boundary test")
	@Test
	void testAddressBoundary() {

		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "Doe", "0123456789", "");
	    });

		// Testing the minimum length for address
		new Contact("1", "John", "Doe", "0123456789", "1");

		// Testing the maximum length for address
		new Contact("1", "John", "Doe", "0123456789", "ThisAddressIs30CharactersLong1");

		// Testing that an exception is thrown for an address over 30 characters
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "Doe", "0123456789", "ThisAddressIsOver30Characters12");
	    });
	}

	/**
	 * Testing for a contact ID that's null and a ID length of more than 10 characters
	 */

	@DisplayName("Contact ID invalid test")
	@Test
	void testInvalidContactID() {

		// Testing that an exception is thrown with a null contact ID
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact(null, "John", "Doe", "0123456789", "123 Loch Ness Road");
	    });

		// Testing that an exception is thrown with an contact ID that is longer than 10 characters
	    assertThrows(IllegalArgumentException.class, () -> {
	    	new Contact("12345678901", "John", "Doe", "0123456789", "123 Loch Ness Road");
	    });
	}

	/**
	 * Testing for a first name that's null and a first name that's longer than 10 characters
	 */

	@DisplayName("First Name invalid test")
    @Test
    void testInvalidFirstName() {

		// Testing that an exception is thrown with a null first name
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", null, "Doe", "0123456789", "123 Loch Ness Road");
	    });

    	// Testing that an exception is thrown with a first name that is longer than 10 characters
        assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "InvalidFirstName", "Doe", "0123456789", "123 Loch Ness Road");
        });
    }

	/**
	 * Testing for a last name that's null and a last name that's longer than 10 characters
	 */

	@DisplayName("Last Name invalid test")
    @Test
    void testInvalidLastName() {

		// Testing that an exception is thrown with a null last name
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", null, "0123456789", "123 Loch Ness Road");
	    });

    	// Testing that an exception is thrown with a last name that is longer than 10 characters
        assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "InvalidLastName", "0123456789", "123 Loch Ness Road");
        });
    }

	/**
	 * Testing for a phone that's null and a phone that's not exactly 10 characters
	 */

	@DisplayName("Phone invalid test")
    @Test
    void testInvalidPhone() {

		// Testing that an exception is thrown with a null phone
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "Doe", null, "123 Loch Ness Road");
	    });

    	// Testing that an exception is thrown with a phone that is longer than 10 characters
        assertThrows(IllegalArgumentException.class, () -> {
        	new Contact("1", "John", "Doe", "01234567890" , "123 Loch Ness Road");
        });

    	// Testing that an exception is thrown with a phone that is shorter than 10 characters
        assertThrows(IllegalArgumentException.class, () -> {
        	new Contact("1", "John", "Doe", "012345678" , "123 Loch Ness Road");
        });
    }

	/**
	 * Testing for a address that's null and a last name that's longer than 30 characters
	 */

	@DisplayName("Address invalid test")
    @Test
    void testInvalidAddress() {

		// Testing that an exception is thrown with a null address
		assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "Doe", "0123456789", null);
	    });

    	// Testing that an exception is thrown with a last name that is longer than 30 characters
        assertThrows(IllegalArgumentException.class, () -> {
	        new Contact("1", "John", "Doe", "0123456789", "This is an invalid address because it is longer than 30 characters");
        });
    }

	/**
	 * Testing setting a valid first name by
	 * using setter method make first name not greater than 10 characters
	 */

	@DisplayName("Set valid First Name test")
    @Test
    void testSetFirstNameValid() {

		// Creating a testContact object
		Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Using the setter method to set the first name not greater than 10 characters, which is valid
		testContact.setFirstName("Joe");

		// Testing that the setter method worked, by ensuring it equals the set name
		assertEquals("Joe", testContact.getFirstName());
    }

	/**
	 * Testing setting a invalid first name by
	 * using setter method to set the first name to null and greater than 10 characters
	 */

	@DisplayName("Set invalid First Name test")
    @Test
    void testSetFirstNameInvalid() {

		// Creating a testContact object
		Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Testing that an exception is thrown by
		// using the setter method to set the first name as null, which is invalid
		assertThrows(IllegalArgumentException.class, () -> {
            testContact.setFirstName(null);
        });

		// Testing that an exception is thrown by
		// using the setter method to set the first name greater than 10 characters, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testContact.setFirstName("Invalid First Name");
        });
    }

	/**
	 * Testing setting a valid last name by
	 * using setter method make last name not greater than 10 characters
	 */

	@DisplayName("Set valid Last Name test")
    @Test
    void testSetLastNameValid() {

		// Creating a testContact object
		Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Using the setter method to set the last name not greater than 10 characters, which is valid
		testContact.setLastName("Rogers");

		// Testing that the setter method worked, by ensuring it equals the set name
		assertEquals("Rogers", testContact.getLastName());
    }

	/**
	 * Testing setting a invalid last name by
	 * using setter method to set the last name to null and greater than 10 characters
	 */

	@DisplayName("Set invalid Last Name test")
    @Test
    void testSetLastNameInvalid() {

		// Creating a testContact object
		Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Testing that an exception is thrown by
		// using the setter method to set the last name as null, which is invalid
		assertThrows(IllegalArgumentException.class, () -> {
            testContact.setLastName(null);
        });

		// Testing that an exception is thrown by
		// using the setter method to set the last name greater than 10 characters, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testContact.setLastName("Invalid Last Name");
        });
    }

	/**
	 * Testing setting a valid phone by
	 * using setter method make phone exactly 10 characters
	 */

	@DisplayName("Set valid Phone test")
    @Test
    void testSetPhoneValid() {

		// Creating a testContact object
		Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Using the setter method to set the phone to exactly 10 characters, which is valid
		testContact.setPhone("9999999999");

		// Testing that the setter method worked, by ensuring it equals the set phone
		assertEquals("9999999999", testContact.getPhone());
    }

	/**
	 * Testing setting a invalid phone by
	 * using setter method to set the phone to null and not equal to exactly 10 characters
	 */

	@DisplayName("Set invalid Phone test")
    @Test
    void testSetPhoneInvalid() {

		// Creating a testContact object
		Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Testing that an exception is thrown by
		// using the setter method to set the phone as null, which is invalid
		assertThrows(IllegalArgumentException.class, () -> {
            testContact.setPhone(null);
        });

		// Testing that an exception is thrown by
		// using the setter method to set the phone greater than 10 characters, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testContact.setPhone("01234567890");
        });

		// Testing that an exception is thrown by
		// using the setter method to set the phone less than 10 characters, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testContact.setPhone("012345678");
        });
    }

	/**
	 * Testing setting a valid address by
	 * using setter method make address not greater than 30 characters
	 */

	@DisplayName("Set valid Address test")
    @Test
    void testSetAddressValid() {

		// Creating a testContact object
		Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Using the setter method to set the address not greater than 30 characters, which is valid
		testContact.setAddress("123 Main Street");

		// Testing that the setter method worked, by ensuring it equals the set address
		assertEquals("123 Main Street", testContact.getAddress());
    }

	/**
	 * Testing setting a invalid address by
	 * using setter method to set the address to null and greater than 30 characters
	 */

	@DisplayName("Set invalid Address test")
    @Test
    void testSetAddressInvalid() {

		// Creating a testContact object
		Contact testContact = new Contact("1", "John", "Doe", "0123456789", "123 Loch Ness Road");

		// Testing that an exception is thrown by
		// using the setter method to set the address as null, which is invalid
		assertThrows(IllegalArgumentException.class, () -> {
            testContact.setAddress(null);
        });

		// Testing that an exception is thrown by
		// using the setter method to set the address greater than 10 characters, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testContact.setLastName("This is an invalid address because it is longer than 30 characters");
        });
    }
}