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
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import task.TaskService;
import task.Task;

class TaskServiceTest {
	
    // Clear the array before each test
    @BeforeEach
    void setUp() throws Exception {
        TaskService.tasks.clear(); 
    }
	
	/**
	 * Testing the default constructor
	 */
	
	@DisplayName("Default constructor test")
    @Test
    public void testDefaultConstructor() {
        new TaskService();
	}
	
    /**
     * Testing if the tasks list is instantiated properly
     */
	
	@DisplayName("Tasks list test")
    @Test
    void testTasksList() {
        assertNotNull(TaskService.tasks);
    }
	
    /**
	 * Testing the addTask() function
	 * by first creating a new task object
	 * then adding it to the array,
	 * then testing to validate the object is in the array
	 */
	
	@DisplayName("Add task test")
	@Test
	void testAddTask() {
		
		// Creating a testTask object
		Task testTask = new Task("1", "ValidName", "ValidDescription");
		
		// Adding the object, then testing to validate it was added
		TaskService.addTask(testTask);
		assertTrue(TaskService.tasks.contains(testTask));
	}
	
	/**
	 * Testing the addTask() function
	 * to validate that it cannot add
	 * a duplicate taskID into the array
	 */
	
	@DisplayName("Unique task ID test")
	@Test
	void testUniqueTaskID() {
		
		// Creating a testTask object
		Task testTask = new Task("1", "ValidName", "ValidDescription");
		
		// Adding the object, then testing to validate it was added
		TaskService.addTask(testTask);
		assertTrue(TaskService.tasks.contains(testTask));
		
		// Creating a second testTask object with a duplicate ID
		Task testTask2 = new Task("1", "ValidName", "ValidDescription");
		
		// Adding the object, then testing to validate it wasn't added due to a duplicate ID
		TaskService.addTask(testTask2);
		assertEquals(false, TaskService.tasks.contains(testTask2));
	}
	
	   /**
	    * Testing deleteTask by adding 2 tasks,
	    * then deleting one and validating deletion
	    * by checking the size of the array
	    */
	    
	    @DisplayName("Delete task test")              
	    @Test 
	    void testDeleteTask() {      
	        
	        // First, validating that the array is empty
	        assertTrue(TaskService.tasks.isEmpty());
	        
			// Creating a testTask object
			Task testTask = new Task("1", "ValidName", "ValidDescription");
	        
	        // Adding the object, then testing to validate it was added
			TaskService.addTask(testTask);
	        assertTrue(TaskService.addTask(testTask));
	        assertTrue(TaskService.tasks.contains(testTask));
	        
	        // Creating a second testTask object
	        Task testTask2 = new Task("2", "ValidName", "ValidDescription");
	        
	        // Adding the second object, then testing to validate it was added
	        TaskService.addTask(testTask2);
	        assertTrue(TaskService.addTask(testTask2));
	        assertTrue(TaskService.tasks.contains(testTask2));
	        
	        // Validating the size of the array, then deleting and re-testing to validate deletion
	        assertEquals(2, TaskService.tasks.size());
	        TaskService.deleteTask("2");
	        assertEquals(1, TaskService.tasks.size());
	        assertFalse(TaskService.tasks.contains(testTask2));
	    }
	    
	    /**
	     * Testing deleteTask by trying to delete an task that doesn't exist
	     * and validating that the size of the tasks array remains the same
	     */
	     
	     @DisplayName("Delete non-existent task test")              
	     @Test 
	     void testDeleteNonExistentTask() {      
	         
	         // First, validating that the array is empty
	    	 assertTrue(TaskService.tasks.isEmpty());
	         
			// Creating a testTask object
			Task testTask = new Task("1", "ValidName", "ValidDescription");
	         
			// Adding the object, then testing to validate it was added
			TaskService.addTask(testTask);
	        assertTrue(TaskService.addTask(testTask));
	        assertTrue(TaskService.tasks.contains(testTask));
	         
	         // Validating the size of the array before trying to delete a non-existent task
	         assertEquals(1, TaskService.tasks.size());
	         
	         // Deleting a non-existent task
	         TaskService.deleteTask("2");
	         
	         // Validating that the size of the array remains the same
	         assertEquals(1, TaskService.tasks.size());
	     }
    
	 	/**
	 	 * Testing updateTaskName by adding a task and validating,
	 	 * then updating the task name and re-validating. 
	 	 * Also try to update task name of a non-existent task.
	 	 */
	 	
	 	@DisplayName("Update task name test")
	 	@Test
	 	void testUpdateTaskName() {
	 		
	 		// Creating a testTask object
	 		Task testTask = new Task("1", "TestName", "ValidDescription");
	 		
	 		// Adding the object, then testing to validate it was added
	 		TaskService.addTask(testTask);
	 		assertTrue(TaskService.tasks.contains(testTask));
	 		
	 		// Validating the test task name, then updating it and re-testing for validation
	 		assertEquals("TestName", TaskService.getTaskNameFromID("1"));
	 		TaskService.updateTaskName("1", "UpdatedName");
	 		assertEquals("UpdatedName", TaskService.getTaskNameFromID("1"));
	 		
	 		// Trying to update the task name of a non-existent task,
	 		// then testing to ensure the correct name remains the same
	 		TaskService.updateTaskName("2", "Non-Existent Task");
	 		assertEquals("UpdatedName", TaskService.getTaskNameFromID("1"));

	 	}
	 	
	 	/**
	 	 * Testing the helper method getTaskNameFromID()
	 	 * by creating a test task, adding it to the list,
	 	 * returning the ID, then trying to return a non-existent ID.
	 	 */
	 	
	 	@DisplayName("Get task name ID test")
	 	@Test
	 	void testGetTaskNameID() {
	 		
	 		// Creating a testTask object
	 		Task testTask = new Task("1", "TestName", "ValidDescription");
	 		
	 		// Adding the object, then testing to validate it was added
	 		TaskService.addTask(testTask);
	 		assertTrue(TaskService.tasks.contains(testTask));
	 		
	 		// Getting task name by ID
	 		String taskName = TaskService.getTaskNameFromID("1");
	 		
	 		// Testing to ensure correct task name is returned
	 		assertEquals("TestName", taskName);
	 		
	 		// Trying to get the task name of a non-existent task
	 		String taskName2 = TaskService.getTaskNameFromID("2");
	 		
	 		// Testing to ensure null is returned
	 		assertNull(taskName2);
	 	}
	 	
	 	/**
	 	 * Testing updateTaskDesc by adding a task and validating,
	 	 * then updating the task description and re-validating. 
	 	 * Also try to update task description of a non-existent task.
	 	 */
	 	
	 	@DisplayName("Update task description test")
	 	@Test
	 	void testUpdateTaskDesc() {
	 		
	 		// Creating a testTask object
	 		Task testTask = new Task("1", "ValidName", "TestDescription");
	 		
	 		// Adding the object, then testing to validate it was added
	 		TaskService.addTask(testTask);
	 		assertTrue(TaskService.tasks.contains(testTask));
	 		
	 		// Validating the test task description, then updating it and re-testing for validation
	 		assertEquals("TestDescription", TaskService.getTaskDescFromID("1"));
	 		TaskService.updateTaskDesc("1", "UpdatedDescription");
	 		assertEquals("UpdatedDescription", TaskService.getTaskDescFromID("1"));
	 		
	 		// Trying to update the task name of a non-existent task,
	 		// then testing to ensure the correct name remains the same
	 		TaskService.updateTaskDesc("2", "Non-Existent Task");
	 		assertEquals("UpdatedDescription", TaskService.getTaskDescFromID("1"));

	 	}
	 	
	 	/**
	 	 * Testing the helper method getTaskDescFromID()
	 	 * by creating a test task, adding it to the list,
	 	 * returning the ID, then trying to return a non-existent ID.
	 	 */
	 	
	 	@DisplayName("Get task description ID test")
	 	@Test
	 	void testGetTaskDescID() {
	 		
	 		// Creating a testTask object
	 		Task testTask = new Task("1", "ValidName", "TestDescription");
	 		
	 		// Adding the object, then testing to validate it was added
	 		TaskService.addTask(testTask);
	 		assertTrue(TaskService.tasks.contains(testTask));
	 		
	 		// Getting task name by ID
	 		String taskDesc = TaskService.getTaskDescFromID("1");
	 		
	 		// Testing to ensure correct task name is returned
	 		assertEquals("TestDescription", taskDesc);
	 		
	 		// Trying to get the task description of a non-existent task
	 		String taskDesc2 = TaskService.getTaskDescFromID("2");
	 		
	 		// Testing to ensure null is returned
	 		assertNull(taskDesc2);
	 	}
}