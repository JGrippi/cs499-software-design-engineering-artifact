package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import task.Task;
import task.TaskService;

import java.util.List;

/**
 * Test class for TaskService
 * Provides 100% coverage for TaskService.java
 */
class TaskServiceTest {
    private static final String VALID_ID = "12345";
    private static final String VALID_NAME = "ValidTaskName";
    private static final String VALID_DESC = "Valid task description";
    
    @BeforeEach
    void setUp() {
        // Clear any existing tasks
        List<Task> tasks = TaskService.getAllTasks();
        tasks.forEach(task -> TaskService.deleteTask(task.getTaskID()));
    }

    @Test
    @DisplayName("Test add task")
    void testAddTask() {
        Task task = new Task(VALID_ID, VALID_NAME, VALID_DESC);
        
        // Test successful add
        assertFalse(TaskService.addTask(task), "Adding new task should return false");
        assertEquals(VALID_NAME, TaskService.getTaskNameFromID(VALID_ID));
        
        // Test duplicate add
        Task duplicate = new Task(VALID_ID, "Different Name", "Different Desc");
        assertTrue(TaskService.addTask(duplicate), "Adding duplicate task should return true");
        
        // Verify original task data remains unchanged
        assertEquals(VALID_NAME, TaskService.getTaskNameFromID(VALID_ID));
        
        // Test null task
        assertThrows(IllegalArgumentException.class, () -> TaskService.addTask(null));
    }

    @Test
    @DisplayName("Test delete task")
    void testDeleteTask() {
        Task task = new Task(VALID_ID, VALID_NAME, VALID_DESC);
        TaskService.addTask(task);
        
        // Test successful delete
        assertTrue(TaskService.deleteTask(VALID_ID), "Deleting existing task should return true");
        assertNull(TaskService.getTaskNameFromID(VALID_ID));
        
        // Test delete non-existent task
        assertFalse(TaskService.deleteTask("nonexistent"), 
                   "Deleting non-existent task should return false");
    }

    @Test
    @DisplayName("Test update operations")
    void testUpdates() {
        Task task = new Task(VALID_ID, VALID_NAME, VALID_DESC);
        TaskService.addTask(task);
        
        // Test successful updates
        assertTrue(TaskService.updateTaskName(VALID_ID, "New Name"));
        assertTrue(TaskService.updateTaskDesc(VALID_ID, "New Description"));
        
        // Verify updates
        assertEquals("New Name", TaskService.getTaskNameFromID(VALID_ID));
        assertEquals("New Description", TaskService.getTaskDescFromID(VALID_ID));
        
        // Test updates on non-existent task
        String nonexistentId = "nonexistent";
        assertFalse(TaskService.updateTaskName(nonexistentId, "New Name"));
        assertFalse(TaskService.updateTaskDesc(nonexistentId, "New Description"));
    }

    @Test
    @DisplayName("Test get operations")
    void testGetOperations() {
        Task task = new Task(VALID_ID, VALID_NAME, VALID_DESC);
        TaskService.addTask(task);
        
        // Test successful retrievals
        assertEquals(VALID_NAME, TaskService.getTaskNameFromID(VALID_ID));
        assertEquals(VALID_DESC, TaskService.getTaskDescFromID(VALID_ID));
        
        // Test retrievals for non-existent task
        String nonexistentId = "nonexistent";
        assertNull(TaskService.getTaskNameFromID(nonexistentId));
        assertNull(TaskService.getTaskDescFromID(nonexistentId));
    }

    @Test
    @DisplayName("Test get all tasks")
    void testGetAllTasks() {
        // Test empty list
        assertTrue(TaskService.getAllTasks().isEmpty());
        
        // Add multiple tasks
        Task task1 = new Task(VALID_ID, VALID_NAME, VALID_DESC);
        Task task2 = new Task("54321", "Second Task", "Second Description");
        
        TaskService.addTask(task1);
        TaskService.addTask(task2);
        
        // Test retrieving all tasks
        List<Task> allTasks = TaskService.getAllTasks();
        assertEquals(2, allTasks.size(), "Should return correct number of tasks");
        
        // Verify contents
        boolean foundTask1 = false;
        boolean foundTask2 = false;
        
        for (Task t : allTasks) {
            if (t.getTaskID().equals(VALID_ID)) {
                assertEquals(VALID_NAME, t.getTaskName());
                assertEquals(VALID_DESC, t.getTaskDesc());
                foundTask1 = true;
            } else if (t.getTaskID().equals("54321")) {
                assertEquals("Second Task", t.getTaskName());
                assertEquals("Second Description", t.getTaskDesc());
                foundTask2 = true;
            }
        }
        
        assertTrue(foundTask1, "First task should be in the list");
        assertTrue(foundTask2, "Second task should be in the list");
        
        // Verify list independence
        allTasks.clear();
        assertFalse(TaskService.getAllTasks().isEmpty(), 
                   "Service tasks should remain after clearing returned list");
    }

    @Test
    @DisplayName("Test list independence")
    void testListIndependence() {
        Task task = new Task(VALID_ID, VALID_NAME, VALID_DESC);
        TaskService.addTask(task);
        
        // Get tasks list twice
        List<Task> list1 = TaskService.getAllTasks();
        List<Task> list2 = TaskService.getAllTasks();
        
        // Verify lists are independent
        assertNotSame(list1, list2, "Should return new list each time");
        assertEquals(list1.size(), list2.size(), "Lists should contain same number of elements");
        
        // Modify first list and verify second is unchanged
        list1.clear();
        assertFalse(list2.isEmpty(), "Second list should be unchanged");
        assertFalse(TaskService.getAllTasks().isEmpty(), 
                   "Service tasks should be unchanged");
    }
}