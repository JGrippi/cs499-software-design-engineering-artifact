package task;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread-safe service class for managing tasks.
 * Implements CRUD operations with performance optimization and proper synchronization.
 */
public final class TaskService {
    // Thread-safe storage using ConcurrentHashMap for O(1) lookups
    private static final ConcurrentHashMap<String, Task> taskMap = new ConcurrentHashMap<>();
    
    // Lock for synchronizing ArrayList operations
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    
    // Internal list for maintaining order
    private static final ArrayList<Task> taskList = new ArrayList<>();

    // Private constructor
    private TaskService() {}

    /**
     * Adds a new task to the service.
     * @param task The task to add
     * @return true if task already exists, false if added successfully
     * @throws IllegalArgumentException if task is null
     */
    public static boolean addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        if (taskMap.putIfAbsent(task.getTaskID(), task) != null) {
            return true; // Task already exists
        }

        lock.writeLock().lock();
        try {
            taskList.add(task);
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Deletes a task by ID.
     * @param taskID The ID of the task to delete
     * @return true if task was deleted, false if not found
     */
    public static boolean deleteTask(String taskID) {
        Task removed = taskMap.remove(taskID);
        if (removed != null) {
            lock.writeLock().lock();
            try {
                taskList.remove(removed);
                return true;
            } finally {
                lock.writeLock().unlock();
            }
        }
        return false;
    }

    /**
     * Updates a task field using a provided update function.
     * @param taskID The ID of the task to update
     * @param updater Function to apply the update
     * @return true if task was updated, false if not found
     */
    private static boolean updateTaskField(String taskID, java.util.function.Consumer<Task> updater) {
        Task task = taskMap.get(taskID);
        if (task != null) {
            updater.accept(task);
            return true;
        }
        return false;
    }

    /**
     * Updates the name of a task.
     * @param taskID The ID of the task to update
     * @param taskName The new task name
     * @return true if updated successfully, false if task not found
     */
    public static boolean updateTaskName(String taskID, String taskName) {
        return updateTaskField(taskID, task -> task.setTaskName(taskName));
    }

    /**
     * Updates the description of a task.
     * @param taskID The ID of the task to update
     * @param taskDesc The new task description
     * @return true if updated successfully, false if task not found
     */
    public static boolean updateTaskDesc(String taskID, String taskDesc) {
        return updateTaskField(taskID, task -> task.setTaskDesc(taskDesc));
    }

    /**
     * Gets a value from a task using a provided getter function.
     * @param taskID The ID of the task
     * @param getter Function to get the desired value
     * @return The requested value or null if task not found
     */
    private static <T> T getTaskValue(String taskID, java.util.function.Function<Task, T> getter) {
        return Optional.ofNullable(taskMap.get(taskID))
                      .map(getter)
                      .orElse(null);
    }

    // Getter methods using the generic getTaskValue method
    public static String getTaskNameFromID(String taskID) {
        return getTaskValue(taskID, Task::getTaskName);
    }

    public static String getTaskDescFromID(String taskID) {
        return getTaskValue(taskID, Task::getTaskDesc);
    }

    /**
     * Gets a list of all tasks.
     * @return A new List containing all tasks
     */
    public static List<Task> getAllTasks() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(taskList);
        } finally {
            lock.readLock().unlock();
        }
    }
}