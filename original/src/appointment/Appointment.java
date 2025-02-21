/* Joey Grippi
 *
 * CS-320
 *
 * Project One
 *
 * April 13, 2024
 */

package appointment;

import java.util.Date;

/**
 * Public class Appointment
 */

public class Appointment {

	// Initializing private string and date variables for the Appointment class
	private String apptID;
	private Date apptDate;
	private String apptDesc;

	/**
	 * Appointment class constructor with conditional checks,
	 * throws exceptions if data is incorrect
	 */

	public Appointment (String apptID,
			Date apptDate,
			String apptDesc) {

		/**
		 * Checking to see if apptID is null, empty, and is no longer than 10 characters,
		 * throws illegal argument exception if incorrect
		 */

		if (apptID == null || apptID.isEmpty() || apptID.length() > 10) {
			throw new IllegalArgumentException("Invalid ID");
		}

		/**
		 * Checking to see if apptDate is null and is not in the past,
		 * throws illegal argument exception if incorrect
		 */

		if (apptDate == null || apptDate.before(new Date())) {
			throw new IllegalArgumentException("Invalid appointment date");
		}

		/**
		 * Checking to see if apptDesc is null, empty, and is no longer than 50 characters,
		 * throws illegal argument exception if incorrect
		 */

		if (apptDesc == null || apptDesc.isEmpty() || apptDesc.length() > 50) {
			throw new IllegalArgumentException("Invalid appointment description");
		}

		/**
		 * If the conditions pass, the objects are set accordingly
		 */

		this.apptID = apptID;
		this.apptDate = apptDate;
		this.apptDesc = apptDesc;
	}

	/**
	 * Getter methods
	 */

	public String getApptID() {
		return apptID;
	}

	public Date getApptDate() {
		return apptDate;
	}

	public String getApptDesc() {
		return apptDesc;
	}

	/**
	 * Setter methods
	 *
	 * No setter method for apptID because it shouldn't be updated
	 */

	/**
	 * Sets the appointment date if the data is validated
	 * @param apptDate
	 */

	public void setApptDate(Date apptDate) {
		if (apptDate == null || apptDate.before(new Date())) {
			throw new IllegalArgumentException("Invalid appointment date");
		}
		this.apptDate = apptDate;
	}

	/**
	 * Sets the appointment description if the data is validated
	 * @param apptDesc
	 */

	public void setApptDesc(String apptDesc) {
		if (apptDesc == null || apptDesc.isEmpty() || apptDesc.length() > 50) {
			throw new IllegalArgumentException("Invalid appointment description");
		}
		this.apptDesc = apptDesc;
	}
}