package appointment;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Date;
import java.util.Calendar;

/**
 * Thread-safe service class for managing appointments.
 * Implements CRUD operations with performance optimization and proper synchronization.
 */
public final class AppointmentService {
    // Thread-safe storage using ConcurrentHashMap for O(1) lookups
    private static final ConcurrentHashMap<String, Appointment> appointmentMap = new ConcurrentHashMap<>();
    
    // Lock for synchronizing ArrayList operations
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    
    // Internal list for maintaining order
    private static final ArrayList<Appointment> appointmentList = new ArrayList<>();

    // Private constructor
    private AppointmentService() {}

    /**
     * Adds a new appointment to the service.
     * @param appt The appointment to add
     * @return true if appointment already exists, false if added successfully
     * @throws IllegalArgumentException if appointment is null
     */
    public static boolean addAppt(Appointment appt) {
        if (appt == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }

        if (appointmentMap.putIfAbsent(appt.getApptID(), appt) != null) {
            return true; // Appointment already exists
        }

        lock.writeLock().lock();
        try {
            appointmentList.add(appt);
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Deletes an appointment by ID.
     * @param apptID The ID of the appointment to delete
     * @return true if appointment was deleted, false if not found
     */
    public static boolean deleteAppt(String apptID) {
        Appointment removed = appointmentMap.remove(apptID);
        if (removed != null) {
            lock.writeLock().lock();
            try {
                appointmentList.remove(removed);
                return true;
            } finally {
                lock.writeLock().unlock();
            }
        }
        return false;
    }

    /**
     * Gets an appointment by ID.
     * @param apptID The ID of the appointment
     * @return The appointment or null if not found
     */
    public static Appointment getAppt(String apptID) {
        return Optional.ofNullable(appointmentMap.get(apptID))
                      .map(appt -> new Appointment(appt.getApptID(), 
                                                 appt.getApptDate(), 
                                                 appt.getApptDesc()))
                      .orElse(null);
    }

    /**
     * Gets a list of all appointments.
     * @return A new List containing all appointments
     */
    public static List<Appointment> getAllAppointments() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(appointmentList);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Gets all appointments scheduled for a specific date.
     * @param date The date to check
     * @return List of appointments on that date
     */
    public static List<Appointment> getAppointmentsByDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        lock.readLock().lock();
        try {
            List<Appointment> result = new ArrayList<>();
            for (Appointment appt : appointmentList) {
                if (isSameDay(appt.getApptDate(), date)) {
                    result.add(appt);
                }
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    // Helper method to compare dates ignoring time
    private static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}