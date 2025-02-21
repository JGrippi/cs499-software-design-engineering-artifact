package appointment;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Appointment class representing an appointment entity with validation rules.
 * Thread-safe implementation with immutable ID and volatile fields.
 */
public final class Appointment {
    // Constants for validation rules
    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_DESC_LENGTH = 50;
    
    // Private fields with proper encapsulation
    private final String apptID;      // Immutable
    private volatile Date apptDate;    // Thread-safe
    private volatile String apptDesc;

    /**
     * Constructs a new Appointment with validation.
     * @param apptID Unique identifier (max 10 chars)
     * @param apptDate Appointment date (must be in future)
     * @param apptDesc Appointment description (max 50 chars)
     * @throws IllegalArgumentException if any parameter fails validation
     */
    public Appointment(String apptID, Date apptDate, String apptDesc) {
        this.apptID = validateField("Appointment ID", apptID, MAX_ID_LENGTH, false);
        this.apptDate = validateDate(apptDate);
        this.apptDesc = validateField("Appointment description", apptDesc, MAX_DESC_LENGTH, false);
    }

    // Getter methods
    public String getApptID() { return apptID; }
    public Date getApptDate() { return new Date(apptDate.getTime()); } // Return copy to maintain immutability
    public String getApptDesc() { return apptDesc; }

    // Setter methods with validation
    public synchronized void setApptDate(Date apptDate) {
        this.apptDate = validateDate(apptDate);
    }

    public synchronized void setApptDesc(String apptDesc) {
        this.apptDesc = validateField("Appointment description", apptDesc, MAX_DESC_LENGTH, false);
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

    private Date validateDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Appointment date cannot be null");
        }

        // Create current date truncated to nearest second for more reliable comparison
        Date now = new Date();
        now = new Date(TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(now.getTime())));
        
        // Truncate input date to nearest second for comparison
        Date truncatedDate = new Date(TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(date.getTime())));
        
        if (truncatedDate.before(now) || truncatedDate.equals(now)) {
            throw new IllegalArgumentException("Appointment date must be in the future. Provided: " + date);
        }
        
        return new Date(date.getTime()); // Return copy to maintain immutability
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return apptID.equals(that.apptID);
    }

    @Override
    public int hashCode() {
        return apptID.hashCode();
    }

    @Override
    public synchronized String toString() {
        return String.format("Appointment[ID=%s, date=%s, description=%s]",
                           apptID, apptDate, apptDesc);
    }
}