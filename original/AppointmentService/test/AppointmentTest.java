/* Joey Grippi
 * 
 * CS-320
 * 
 * Project One
 * 
 * April 13, 2024
 */

package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import appointment.Appointment;

class AppointmentTest {

	/**
	 * Testing with a valid constructor for appointment object creation
	 */
	
	@DisplayName("Valid constructor test")
    @Test
    void testValidConstructor() {
        
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
        
		// Creating a test appointment object 
		Appointment testAppt = new Appointment("1", validDate, "ValidDescription");
		
		// Testing that the test appointment object has a valid ID, date, and description
        assertEquals("1", testAppt.getApptID());
        assertEquals(validDate, testAppt.getApptDate());
        assertEquals("ValidDescription", testAppt.getApptDesc());
    }
	
	/**
	 * Testing the boundary values of the apptID
	 */
	
	@DisplayName("Appointment ID Boundary test")
	@Test
	void testApptIDBoundary() {
		
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
		
		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("", validDate, "ValidDescription");
	    });
		
		// Testing the minimum length for the apptID
        new Appointment("1", validDate, "ValidDescription");
		
		// Testing the maximum length for the apptID
        new Appointment("1234567890", validDate, "ValidDescription");
		
		// Testing that an exception is thrown for a apptID over 10 characters
		assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("01234567890", validDate, "ValidDescription");
	    });
	}
	
	/**
	 * Testing the boundary values of the apptDate
	 */
	
	@DisplayName("Appointment Date Boundary test")
	@Test
	void testApptDateBoundary() {
		
	    // First, initializing the current date (1 millisecond behind for better accuracy)
	    Date currentDate = new Date(System.currentTimeMillis() - 1);

	    // Testing that an exception is thrown when the date is at the exact current time (-1 millisecond)
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Appointment("1", currentDate, "ValidDescription");
	    });
	    
	    // Testing that an exception is thrown when the date is set in the past
	    Date pastDate = new Date(currentDate.getTime() - 10);
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Appointment("1", pastDate, "ValidDescription");
	    });

	    // Testing a date slightly in the future (ten milliseconds ahead)
	    Date futureDate = new Date(currentDate.getTime() + 10);
	    new Appointment("1", futureDate, "ValidDescription");

	    // Testing a date in the distant future (10 years ahead)
	    Date distantFutureDate = new Date(currentDate.getTime() + (10L * 365 * 24 * 60 * 60 * 1000));
	    new Appointment("1", distantFutureDate, "ValidDescription");
	}
	
	/**
	 * Testing the boundary values of apptDesc
	 */
	
	@DisplayName("Appointment Description Boundary test")
	@Test
	void testApptDescriptionBoundary() {
		
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
		
		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
			new Appointment("1", validDate, "");;
	    });
		
		// Testing the minimum length for apptDesc
		new Appointment("1", validDate, "1");
		
		// Testing the maximum length for apptDesc
		new Appointment("1", validDate, "12345678901234567890123456789012345678901234567890");
		
		// Testing that an exception is thrown for a apptDesc over 50 characters
		assertThrows(IllegalArgumentException.class, () -> {
			new Appointment("1", validDate, "123456789012345678901234567890123456789012345678901");
	    });
	}
    
	/**
	 * Testing for a appointment ID that's null and a ID length of more than 10 characters
	 */
	
	@DisplayName("Appointment ID invalid test")
    @Test
    void testInvalidApptID() {
        
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
       
    	// Testing that an exception is thrown with a null appointment ID
    	assertThrows(IllegalArgumentException.class, () -> {
            new Appointment(null, validDate, "ValidDescription");
        });
    	
    	// Testing that an exception is thrown with an appointment ID that is longer than 10 characters
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("12345678901", validDate, "ValidDescription");
        });
    }
    
	/**
	 * Testing for a appointment date that's null and a date that is in the past
	 */
	
	@DisplayName("Appointment date invalid test")
    @Test
    void testInvalidApptDate() {
       
		// Testing that an exception is thrown with a null appointment date
		assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1", null, "ValidDescription");
        });
		
		// Initializing a date that is in the past
        Date past = new Date(System.currentTimeMillis() - 99999);
       
        // Testing that an exception is thrown with an appointment date that is in the past
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1", past, "ValidDescription");
        });
    }
    
	/**
	 * Testing for a appointment description that's null and a description that's longer than 50 characters
	 */
	
	@DisplayName("Appointment Description invalid test")
    @Test 
    void testInvalidApptDesc() {
        
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
    	
    	// Testing that an exception is thrown with a null appointment description
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1", validDate, null);
        });
        
    	// Testing that an exception is thrown with an appointment description that is longer than 50 characters
        assertThrows(IllegalArgumentException.class, () -> {
            new Appointment("1", validDate, "Invalid description because this description is longer than 50 characters");
        });
    }
    
	/**
	 * Testing setting a valid appointment date by 
	 * using setter method to put date in the future
	 */
	
	@DisplayName("Set valid Appointment Date test")
    @Test
    void testSetApptDateValid() {
        
		// Initializing a valid date that is in the future
		Date future = new Date(System.currentTimeMillis() + 99999);
        
		// Creating a testAppt object
		Appointment testAppt = new Appointment("1", new Date(), "ValidDescription");
        
		// Using the setter method to set the appointment date in the future, which is valid
		testAppt.setApptDate(future);
		
		// Testing that the setter method worked, by ensuring it equals the set date
        assertEquals(future, testAppt.getApptDate());
    }
    
	/**
	 * Testing setting a invalid appointment date by 
	 * using setter method to put date in past and null
	 */
	
	@DisplayName("Set invalid Appointment Date test")
    @Test
    void testSetApptDateInvalid() {
        
		// Creating a testAppt object
		Appointment testAppt = new Appointment("1", new Date(), "ValidDescription");
		
		// Testing that an exception is thrown by 
		// using the setter method to set the appointment date as null, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testAppt.setApptDate(null);
        });
        
        // Initializing a date that is in the past
        Date past = new Date(System.currentTimeMillis() - 99999);
        
        // Testing that an exception is thrown by 
     	// using the setter method to set the appointment date in the past, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testAppt.setApptDate(past);
        });
    }
    
	/**
	 * Testing setting a valid appointment description by 
	 * using setter method make description not greater than 50 characters
	 */
	
	@DisplayName("Set valid Appointment Description test")
    @Test
    void testSetApptDescValid() {
        
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
		
		// Creating a testAppt object
		Appointment testAppt = new Appointment("1", validDate, "ValidDescription");
        
		// Using the setter method to set the appointment description not greater than 50 characters, which is valid
		testAppt.setApptDesc("This is a valid description");
        
		// Testing that the setter method worked, by ensuring it equals the set description
		assertEquals("This is a valid description", testAppt.getApptDesc());
    }
    
	/**
	 * Testing setting a invalid appointment description by 
	 * using setter method to set the description to null and greater than 50 characters
	 */
	
	@DisplayName("Set invalid Appointment Description test")
    @Test
    void testSetApptDescInvalid() {
        
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
		
		// Creating a testAppt object
		Appointment testAppt = new Appointment("1", validDate, "ValidDescription");
        
		// Testing that an exception is thrown by 
		// using the setter method to set the appointment description as null, which is invalid
		assertThrows(IllegalArgumentException.class, () -> {
            testAppt.setApptDesc(null);
        });
		
		// Testing that an exception is thrown by 
		// using the setter method to set the appointment description greater than 50 characters, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testAppt.setApptDesc("Invalid description because this description is longer than 50 characters");
        });
    }
}