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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import appointment.AppointmentService;
import appointment.Appointment;

class AppointmentServiceTest { 
    
    // Clear the array before each test
    @BeforeEach
    void setUp() throws Exception {
        AppointmentService.appointments.clear(); 
    }
    
	/**
	 * Testing the default constructor
	 */
	
	@DisplayName("Default constructor test")
    @Test
    public void testDefaultConstructor() {
        new AppointmentService();
	}
	
    /**
     * Testing if the appointments list is instantiated properly
     */
	
	@DisplayName("Appointments list test")
    @Test
    void testAppointmentsList() {
        assertNotNull(AppointmentService.appointments);
    }
	
    /**
	 * Testing the addAppt() function
	 * by first creating a new appointment object
	 * then adding it to the array,
	 * then testing to validate the object is in the array
	 */
	
	@DisplayName("Add appointment test")
	@Test
	void testAddAppt() {
		
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
		
		// Creating a testAppt object
		Appointment testAppt = new Appointment("1", validDate, "ValidDescription");
		
		// Adding the object, then testing to validate it was added
		AppointmentService.addAppt(testAppt);
		assertTrue(AppointmentService.appointments.contains(testAppt));
	}
	
	/**
	 * Testing the addAppt() function
	 * to validate that it cannot add
	 * a duplicate apptID into the array
	 */
	
	@DisplayName("Unique appointment ID test")
	@Test
	void testUniqueApptID() {
		
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
		
		// Creating a testAppt object
		Appointment testAppt = new Appointment("1", validDate, "ValidDescription");
		
		// Adding the object, then testing to validate it was added
		AppointmentService.addAppt(testAppt);
		assertTrue(AppointmentService.appointments.contains(testAppt));
		
		// Creating a second testAppt object with a duplicate ID
		Appointment testAppt2 = new Appointment("1", validDate, "ValidDescription");
		
		// Adding the object, then testing to validate it wasn't added due to a duplicate ID
		AppointmentService.addAppt(testAppt2);
		assertEquals(false, AppointmentService.appointments.contains(testAppt2));
	}

    /**
    * Testing deleteAppt by adding 2 appointments,
    * then deleting one and validating deletion
    * by checking the size of the array
    */
    
    @DisplayName("Delete appointment test")              
    @Test 
    void testDeleteAppt() {      
        
        // First, validating that the array is empty
        assertTrue(AppointmentService.appointments.isEmpty());
        
		// Initializing a valid date that is in the future
		Date validDate = new Date(System.currentTimeMillis() + 99999);
        
        // Creating a testAppt object
        Appointment testAppt = new Appointment("1", validDate, "ValidDescription");
        
        // Adding the object, then testing to validate it was added
        AppointmentService.addAppt(testAppt);
        assertTrue(AppointmentService.addAppt(testAppt));
        assertTrue(AppointmentService.appointments.contains(testAppt));
        
        // Creating a second testAppt object
        Appointment testAppt2 = new Appointment("2", validDate, "ValidDescription");
        
        // Adding the second object, then testing to validate it was added
        AppointmentService.addAppt(testAppt2);
        assertTrue(AppointmentService.addAppt(testAppt2));
        assertTrue(AppointmentService.appointments.contains(testAppt2));
        
        // Validating the size of the array, then deleting and re-testing to validate deletion
        assertEquals(2, AppointmentService.appointments.size());
        AppointmentService.deleteAppt("2");
        assertEquals(1, AppointmentService.appointments.size());
        assertFalse(AppointmentService.appointments.contains(testAppt2));
    }
    
    /**
     * Testing deleteAppt by trying to delete an appointment that doesn't exist
     * and validating that the size of the appointments array remains the same
     */
     
     @DisplayName("Delete non-existent appointment test")              
     @Test 
     void testDeleteNonExistentAppt() {      
         
         // First, validating that the array is empty
         assertTrue(AppointmentService.appointments.isEmpty());
         
 		// Initializing a valid date that is in the future
 		Date validDate = new Date(System.currentTimeMillis() + 99999);
         
         // Creating a testAppt object
         Appointment testAppt = new Appointment("1", validDate, "ValidDescription");
         
         // Adding the object, then testing to validate it was added
         AppointmentService.addAppt(testAppt);
         assertTrue(AppointmentService.addAppt(testAppt));
         assertTrue(AppointmentService.appointments.contains(testAppt));
         
         // Validating the size of the array before trying to delete a non-existent appointment
         assertEquals(1, AppointmentService.appointments.size());
         
         // Deleting a non-existent appointment
         AppointmentService.deleteAppt("2");
         
         // Validating that the size of the array remains the same
         assertEquals(1, AppointmentService.appointments.size());
     }
}