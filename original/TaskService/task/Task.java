/* Joey Grippi
 * 
 * CS-320
 * 
 * Project One
 * 
 * April 13, 2024
 */

package task;

/**
 * Public class Task
 */

public class Task {
	
	// Initializing private string variables for the Task class
	private String taskID;
	private String taskName;
	private String taskDesc;
	
	/**
	 * Task class constructor with conditional checks,
	 * throws exceptions if data is incorrect
	 */
	
	public Task (String taskID, 
			String taskName,  
			String taskDesc) {
		
		/**
		 * Checking to see if taskID is null, empty, and is no longer than 10 characters,
		 * throws illegal argument exception if incorrect
		 */
		
		if (taskID == null || taskID.isEmpty() || taskID.length() > 10) {
			throw new IllegalArgumentException("Invalid ID");
		}
		
		/**
		 * Checking to see if taskName is null, empty, and is no longer than 20 characters,
		 * throws illegal argument exception if incorrect
		 */
		
		if (taskName == null || taskName.isEmpty() || taskName.length() > 20) {
			throw new IllegalArgumentException("Invalid task name");
		}
		
		/**
		 * Checking to see if taskDesc is null, empty, and is no longer than 50 characters,
		 * throws illegal argument exception if incorrect
		 */
		
		if (taskDesc == null || taskDesc.isEmpty() || taskDesc.length() > 50) {
			throw new IllegalArgumentException("Invalid task description");
		}
		
		/**
		 * If the conditions pass, the objects are set accordingly
		 */
		
		this.taskID = taskID;
		this.taskName = taskName;
		this.taskDesc = taskDesc;
	}
	
	/**
	 * Getter methods
	 */
	
	public String getTaskID() {
		return taskID;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public String getTaskDesc() {
		return taskDesc;
	}
	
	/**
	 * Setter methods
	 * 
	 * No setter method for taskID because it shouldn't be updated
	 */
	
	/**
	 * Sets the task name if the data is validated
	 * @param taskName
	 */
	
	public void setTaskName(String taskName) {
		if (taskName == null || taskName.isEmpty() || taskName.length() > 20) {
			throw new IllegalArgumentException("Invalid task name");
		}
		this.taskName = taskName;
	}
	
	/**
	 * Sets the task description if the data is validated
	 * @param taskDesc
	 */
	
	public void setTaskDesc(String taskDesc) {
		if (taskDesc == null || taskDesc.isEmpty() || taskDesc.length() > 50) {
			throw new IllegalArgumentException("Invalid task description");
		}
		this.taskDesc = taskDesc;
	}	
}