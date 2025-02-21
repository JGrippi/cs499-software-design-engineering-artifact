/* Joey Grippi
 *
 * CS-320
 *
 * Project One
 *
 * April 13, 2024
 */

package appointment;

import java.util.ArrayList;

/**
 * Public class AppointmentService
 */

public class AppointmentService {

	// Creating the Array list to hold the appointments
	public static ArrayList<Appointment>appointments = new ArrayList<>();

	/**
	 * Checking for the existence of unique apptID, adding if it doesn't exist
	 * @param appt
	 * @return
	 */

	public static boolean addAppt(Appointment appt) {

		// Initially setting the boolean to false
		boolean apptExists = false;

		// For loop to check for the existence of appointment ID
		for (Appointment apptList : appointments) {

			// If the appointment ID exists, set boolean to true
			if (apptList.getApptID().equals(appt.getApptID())) {
				apptExists = true;
			}
		}

		// If the appointment ID doesn't exist, add appointment to array
		if (!apptExists) {
			appointments.add(appt);
		}

		// Return the value of the boolean
		return apptExists;
	}

	/**
	 * Delete appointment per apptID
	 * @param apptID
	 */

	public static void deleteAppt(String apptID) {

		// For loop to find the apptID in the array
		for(Appointment apptList : appointments) {

			// If the apptID is found in the array, delete the appointment from the array
			if(apptList.getApptID() == apptID) {
				appointments.remove(apptList);
				return;
			}
		}
	}
}