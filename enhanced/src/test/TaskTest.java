package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import task.Task;

/**
 * Test class for Task
 * Provides 100% coverage for Task.java
 */
class TaskTest {
    private static final String VALID_ID = "12345";
    private static final String VALID_NAME = "ValidTaskName";
    private static final String VALID_DESC = "Valid task description";
    
    private Task testTask;
    
    @BeforeEach
    void setUp() {
        testTask = new Task(VALID_ID, VALID_NAME, VALID_DESC);
    }

    @Test
    @DisplayName("Test valid task creation")
    void testValidConstruction() {
        Task task = new Task(VALID_ID, VALID_NAME, VALID_DESC);
        
        assertEquals(VALID_ID, task.getTaskID());
        assertEquals(VALID_NAME, task.getTaskName());
        assertEquals(VALID_DESC, task.getTaskDesc());
    }

    @Test
    @DisplayName("Test task ID validation")
    void testTaskIDValidation() {
        // Test null ID
        Exception e1 = assertThrows(IllegalArgumentException.class, () ->
            new Task(null, VALID_NAME, VALID_DESC));
        assertTrue(e1.getMessage().contains("Task ID cannot be null"));

        // Test empty ID
        Exception e2 = assertThrows(IllegalArgumentException.class, () ->
            new Task("", VALID_NAME, VALID_DESC));
        assertTrue(e2.getMessage().contains("Task ID cannot be null or empty"));

        // Test ID too long
        Exception e3 = assertThrows(IllegalArgumentException.class, () ->
            new Task("12345678901", VALID_NAME, VALID_DESC));
        assertTrue(e3.getMessage().contains("Task ID cannot exceed 10 characters"));
    }

    @Test
    @DisplayName("Test task name validation")
    void testTaskNameValidation() {
        // Test null name
        assertThrows(IllegalArgumentException.class, () ->
            new Task(VALID_ID, null, VALID_DESC));

        // Test empty name
        assertThrows(IllegalArgumentException.class, () ->
            new Task(VALID_ID, "", VALID_DESC));

        // Test name too long (> 20 chars)
        String longName = "ThisTaskNameIsWayTooLongToBeValid";
        assertThrows(IllegalArgumentException.class, () ->
            new Task(VALID_ID, longName, VALID_DESC));

        // Test name setter validation
        assertThrows(IllegalArgumentException.class, () -> testTask.setTaskName(null));
        assertThrows(IllegalArgumentException.class, () -> testTask.setTaskName(""));
        assertThrows(IllegalArgumentException.class, () -> testTask.setTaskName(longName));

        // Test valid name update
        testTask.setTaskName("New Valid Name");
        assertEquals("New Valid Name", testTask.getTaskName());
    }

    @Test
    @DisplayName("Test task description validation")
    void testTaskDescValidation() {
        // Test null description
        assertThrows(IllegalArgumentException.class, () ->
            new Task(VALID_ID, VALID_NAME, null));

        // Test empty description
        assertThrows(IllegalArgumentException.class, () ->
            new Task(VALID_ID, VALID_NAME, ""));

        // Test description too long (> 50 chars)
        String longDesc = "This task description is way too long to be valid as it exceeds fifty characters";
        assertThrows(IllegalArgumentException.class, () ->
            new Task(VALID_ID, VALID_NAME, longDesc));

        // Test description setter validation
        assertThrows(IllegalArgumentException.class, () -> testTask.setTaskDesc(null));
        assertThrows(IllegalArgumentException.class, () -> testTask.setTaskDesc(""));
        assertThrows(IllegalArgumentException.class, () -> testTask.setTaskDesc(longDesc));

        // Test valid description update
        testTask.setTaskDesc("New valid description");
        assertEquals("New valid description", testTask.getTaskDesc());
    }

    @Test
    @DisplayName("Test field exact length validation")
    void testExactLengthValidation() {
        Task task = new Task(VALID_ID, VALID_NAME, VALID_DESC);
        
        try {
            java.lang.reflect.Method validateField = Task.class.getDeclaredMethod(
                "validateField", String.class, String.class, int.class, boolean.class);
            validateField.setAccessible(true);

            // Test shorter than required length
            Exception exception = assertThrows(Exception.class, () -> 
                validateField.invoke(task, "Test Field", "1234", 5, true));
            assertTrue(exception.getCause() instanceof IllegalArgumentException);
            assertTrue(exception.getCause().getMessage().contains("must be exactly 5 characters"));

            // Test longer than required length
            exception = assertThrows(Exception.class, () -> 
                validateField.invoke(task, "Test Field", "123456", 5, true));
            assertTrue(exception.getCause() instanceof IllegalArgumentException);
            assertTrue(exception.getCause().getMessage().contains("must be exactly 5 characters"));

            // Test exact length
            Object result = validateField.invoke(task, "Test Field", "12345", 5, true);
            assertEquals("12345", result);

        } catch (Exception e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test toString implementation")
    void testToString() {
        String expected = String.format("Task[ID=%s, name=%s, description=%s]",
                                      VALID_ID, VALID_NAME, VALID_DESC);
        assertEquals(expected, testTask.toString());
    }


    @SuppressWarnings("unlikely-arg-type")
	@Test
    @DisplayName("Test equals and hashCode")
    void testEqualsAndHashCode() {
        // Create tasks with same ID but different data
        Task sameIdTask = new Task(VALID_ID, "Different Name", "Different Description");
        Task differentTask = new Task("54321", VALID_NAME, VALID_DESC);

        // Test equals
        assertTrue(testTask.equals(testTask));  // Same object
        assertTrue(testTask.equals(sameIdTask)); // Same ID
        assertFalse(testTask.equals(differentTask)); // Different ID
        assertFalse(testTask.equals(null)); // null
        assertFalse(testTask.equals("not a task")); // Different type

        // Test hashCode
        assertEquals(testTask.hashCode(), sameIdTask.hashCode());
        assertNotEquals(testTask.hashCode(), differentTask.hashCode());
    }
}