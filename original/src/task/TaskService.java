/* Joey Grippi
 *
 * CS-320
 *
 * Project One
 *
 * April 13, 2024
 */

package task;

import java.util.ArrayList;

/**
 * Public class TaskService
 */

public class TaskService {

	// Creating the Array list to hold the tasks
	public static ArrayList<Task>tasks = new ArrayList<>();

	/**
	 * Checking for the existence of unique task ID, adding if it doesn't exist
	 * @param task
	 * @return
	 */

	public static boolean addTask(Task task) {

		// Initially setting the boolean to false
		boolean taskExists = false;

		// For loop to check for the existence of task ID
		for (Task taskList : tasks) {

			// If the task ID exists, set boolean to true
			if (taskList.getTaskID().equals(task.getTaskID())) {
				taskExists = true;
			}
		}

		// If the task ID doesn't exist, add task to array
		if (!taskExists) {
			tasks.add(task);
		}

		// Return the value of the boolean
		return taskExists;
	}

	/**
	 * Delete task per task ID
	 * @param taskID
	 */

	public static void deleteTask(String taskID) {

		// For loop to find the task ID in the array
		for(Task taskList : tasks) {

			// If the task ID is found in the array, delete the task from the array
			if(taskList.getTaskID() == taskID) {
				tasks.remove(taskList);
				return;
			}
		}
	}

	/**
	 * Update task name per task ID
	 * @param taskID
	 * @param taskName
	 */

	public static void updateTaskName(String taskID, String taskName) {

		// For loop to find the task ID in the array
		for(Task taskList : tasks) {

			// If the task ID is found in the array, update the task name
			if(taskList.getTaskID() == taskID) {
				taskList.setTaskName(taskName);
				return;
			}
		}
	}

	/**
	 * Helper method for the updateTaskName() function to return taskName per ID
	 * @param taskID
	 * @return
	 */

    public static String getTaskNameFromID(String taskID) {

    	// For loop to find the task ID in the array
    	for (Task taskList : tasks) {

    		// If the task ID is found in the array, return the task name
    		if(taskList.getTaskID() == taskID) {
                return taskList.getTaskName();
            }
        }

    	// Returns a null string if a task ID isn't matched
        return null;
    }

	/**
	 * Update task description per task ID
	 * @param taskID
	 * @param taskDesc
	 */

	public static void updateTaskDesc(String taskID, String taskDesc) {

		// For loop to find the task ID in the array
		for(Task taskList : tasks) {

			// If the task ID is found in the array, update the task description
			if(taskList.getTaskID() == taskID) {
				taskList.setTaskDesc(taskDesc);
				return;
			}
		}
	}

	/**
	 * Helper method for the updateTaskDesc() function to return taskDesc per ID
	 * @param taskID
	 * @return
	 */

    public static String getTaskDescFromID(String taskID) {

    	// For loop to find the task ID in the array
    	for (Task taskList : tasks) {

    		// If the task ID is found in the array, return the task description
    		if(taskList.getTaskID() == taskID) {
                return taskList.getTaskDesc();
            }
        }

        // Returns a null string if a task ID isn't matched
        return null;
    }
}