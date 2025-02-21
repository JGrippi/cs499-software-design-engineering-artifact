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

import task.Task;

class TaskTest {
	
	/**
	 * Testing with a valid constructor for task object creation
	 */
	
	@DisplayName("Valid constructor test")
    @Test
    void testValidConstructor() {
        
        // Creating a testTask object
        Task testTask = new Task("1", "ValidName", "ValidDescription"); 
		
		// Testing that the test task object has a valid ID, date, and description
        assertEquals("1", testTask.getTaskID());
        assertEquals("ValidName", testTask.getTaskName());
        assertEquals("ValidDescription", testTask.getTaskDesc());
    }
	
	/**
	 * Testing the boundary values of the taskID
	 */
	
	@DisplayName("Task ID Boundary test")
	@Test
	void testTaskIDBoundary() {
		
		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
			new Task("", "ValidName", "ValidDescription");
	    });
		
		// Testing the minimum length for the contactID
		new Task("1", "ValidName", "ValidDescription");
		
		// Testing the maximum length for the contactID
		new Task("1234567890", "ValidName", "ValidDescription");
		
		// Testing that an exception is thrown for a contactID over 10 characters
		assertThrows(IllegalArgumentException.class, () -> {
			new Task("01234567890", "ValidName", "ValidDescription");
	    });
	}
	
	/**
	 * Testing the boundary values of taskName
	 */
	
	@DisplayName("Task Name Boundary test")
	@Test
	void testTaskNameBoundary() {
		
		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
			new Task("1", "", "ValidDescription");
	    });
		
		// Testing the minimum length for taskName
		new Task("1", "A", "ValidDescription");
		
		// Testing the maximum length for taskName
		new Task("1", "ABCDEFGHIJKLMNOPQRST", "ValidDescription");
		
		// Testing that an exception is thrown for a contactID over 10 characters
		assertThrows(IllegalArgumentException.class, () -> {
			new Task("1", "ABCDEFGHIJKLMNOPQRSTU", "ValidDescription");
	    });
	}
	
	/**
	 * Testing the boundary values of taskDesc
	 */
	
	@DisplayName("Task Description Boundary test")
	@Test
	void testTaskDescriptionBoundary() {
		
		// Testing that an exception is thrown for an empty string
		assertThrows(IllegalArgumentException.class, () -> {
			new Task("1", "ValidName", "");
	    });
		
		// Testing the minimum length for taskDesc
		new Task("1", "ValidName", "1");
		
		// Testing the maximum length for taskDesc
		new Task("1", "ValidName", "12345678901234567890123456789012345678901234567890");
		
		// Testing that an exception is thrown for a taskDesc over 50 characters
		assertThrows(IllegalArgumentException.class, () -> {
			new Task("1", "ValidName", "123456789012345678901234567890123456789012345678901");
	    });
	}
	
	/**
	 * Testing for a task ID that's null and a ID length of more than 10 characters
	 */
	
	@DisplayName("Task ID invalid test")
	@Test
	void testInvalidTaskID() {	    
	   
		// Testing that an exception is thrown with a null task ID
		assertThrows(IllegalArgumentException.class, () -> {
	        new Task(null, "ValidName", "ValidDescription");
	    });
		
		// Testing that an exception is thrown with an task ID that is longer than 10 characters
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Task("12345678901", "ValidName", "ValidDescription");
	    });
	}
	
	/**
	 * Testing for a task name that's null and a name that's longer than 20 characters
	 */
	
	@DisplayName("Task Name invalid test")
    @Test 
    void testInvalidTaskName() {
    	
    	// Testing that an exception is thrown with a null task name
        assertThrows(IllegalArgumentException.class, () -> {
            new Task("1", null, "ValidDescription");
        });
        
    	// Testing that an exception is thrown with a task name that is longer than 20 characters
        assertThrows(IllegalArgumentException.class, () -> {
            new Task("1", "InvalidNameLongerThan20Characters", "ValidDescription");
        });
    }
	
	/**
	 * Testing for a task description that's null and a description that's longer than 50 characters
	 */
	
	@DisplayName("Task Description invalid test")
    @Test 
    void testInvalidTaskDesc() {
    	
    	// Testing that an exception is thrown with a null task description
        assertThrows(IllegalArgumentException.class, () -> {
            new Task("1", "ValidName", null);
        });
        
    	// Testing that an exception is thrown with a task description that is longer than 50 characters
        assertThrows(IllegalArgumentException.class, () -> {
            new Task("1", "ValidName", "Invalid description because this description is longer than 50 characters");
        });
    }
	
	/**
	 * Testing setting a valid task name by 
	 * using setter method make name not greater than 20 characters
	 */
	
	@DisplayName("Set valid Task Name test")
    @Test
    void testSetTaskNameValid() {
        
		// Creating a testTask object
		Task testTask = new Task("1", "ValidName", "ValidDescription");
        
		// Using the setter method to set the task name not greater than 20 characters, which is valid
		testTask.setTaskName("NewValidName");
        
		// Testing that the setter method worked, by ensuring it equals the set name
		assertEquals("NewValidName", testTask.getTaskName());
    }
	
	/**
	 * Testing setting a invalid task name by 
	 * using setter method to set the name to null and greater than 20 characters
	 */
	
	@DisplayName("Set invalid Task Name test")
    @Test
    void testSetTaskNameInvalid() {
		
		// Creating a testTask object
		Task testTask = new Task("1", "ValidName", "ValidDescription");
        
		// Testing that an exception is thrown by 
		// using the setter method to set the task name as null, which is invalid
		assertThrows(IllegalArgumentException.class, () -> {
            testTask.setTaskName(null);
        });
		
		// Testing that an exception is thrown by 
		// using the setter method to set the task name greater than 20 characters, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testTask.setTaskName("Invalid name because this is longer than 20 characters");
        });
    }
	
	/**
	 * Testing setting a valid task description by 
	 * using setter method make name not greater than 50 characters
	 */
	
	@DisplayName("Set valid Task Description test")
    @Test
    void testSetTaskDescValid() {
        
		// Creating a testTask object
		Task testTask = new Task("1", "ValidName", "ValidDescription");
        
		// Using the setter method to set the task description not greater than 20 characters, which is valid
		testTask.setTaskDesc("This is a valid description");
        
		// Testing that the setter method worked, by ensuring it equals the set description
		assertEquals("This is a valid description", testTask.getTaskDesc());
    }
	
	/**
	 * Testing setting a invalid task description by 
	 * using setter method to set the description to null and greater than 20 characters
	 */
	
	@DisplayName("Set invalid Task Description test")
    @Test
    void testSetTaskDescInvalid() {
		
		// Creating a testTask object
		Task testTask = new Task("1", "ValidName", "ValidDescription");
        
		// Testing that an exception is thrown by 
		// using the setter method to set the task description as null, which is invalid
		assertThrows(IllegalArgumentException.class, () -> {
            testTask.setTaskDesc(null);
        });
		
		// Testing that an exception is thrown by 
		// using the setter method to set the task description greater than 50 characters, which is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            testTask.setTaskDesc("Invalid description because this description is longer than 50 characters");
        });
    }
}