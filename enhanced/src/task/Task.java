package task;

/**
 * Task class representing a task entity with validation rules.
 * Thread-safe implementation with immutable ID and volatile fields.
 */
public final class Task {
    // Constants for validation rules
    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_DESC_LENGTH = 50;
    
    // Private fields with proper encapsulation
    private final String taskID;    // Immutable
    private volatile String taskName;  // Thread-safe
    private volatile String taskDesc;

    /**
     * Constructs a new Task with validation.
     * @param taskID Unique identifier (max 10 chars)
     * @param taskName Task name (max 20 chars)
     * @param taskDesc Task description (max 50 chars)
     * @throws IllegalArgumentException if any parameter fails validation
     */
    public Task(String taskID, String taskName, String taskDesc) {
        this.taskID = validateField("Task ID", taskID, MAX_ID_LENGTH, false);
        this.taskName = validateField("Task name", taskName, MAX_NAME_LENGTH, false);
        this.taskDesc = validateField("Task description", taskDesc, MAX_DESC_LENGTH, false);
    }

    // Getter methods
    public String getTaskID() { return taskID; }
    public String getTaskName() { return taskName; }
    public String getTaskDesc() { return taskDesc; }

    // Setter methods with validation
    public synchronized void setTaskName(String taskName) {
        this.taskName = validateField("Task name", taskName, MAX_NAME_LENGTH, false);
    }

    public synchronized void setTaskDesc(String taskDesc) {
        this.taskDesc = validateField("Task description", taskDesc, MAX_DESC_LENGTH, false);
    }

    // Validation methods
    private String validateField(String fieldName, String value, int maxLength, boolean exactLength) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        if (exactLength && value.length() != maxLength) {
            throw new IllegalArgumentException(fieldName + " must be exactly " + maxLength + " characters. Provided: '" + value + "'");
        }
        if (!exactLength && value.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " cannot exceed " + maxLength + " characters. Provided: '" + value + "'");
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return taskID.equals(task.taskID);
    }

    @Override
    public int hashCode() {
        return taskID.hashCode();
    }

    @Override
    public synchronized String toString() {
        return String.format("Task[ID=%s, name=%s, description=%s]",
                           taskID, taskName, taskDesc);
    }
}