package contact;

/**
 * Contact class representing a contact entity with validation rules.
 * Thread-safe implementation with immutable ID and volatile fields.
 */
public final class Contact {
    // Constants for validation rules
    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_NAME_LENGTH = 10;
    private static final int PHONE_LENGTH = 10;
    private static final int MAX_ADDRESS_LENGTH = 30;
    private static final String PHONE_REGEX = "\\d+";
    
    // Private fields with proper encapsulation
    private final String contactID;  // Immutable
    private volatile String firstName;  // Thread-safe
    private volatile String lastName;
    private volatile String phone;
    private volatile String address;

    /**
     * Constructs a new Contact with validation.
     * @param contactID Unique identifier (max 10 chars)
     * @param firstName First name (max 10 chars)
     * @param lastName Last name (max 10 chars)
     * @param phone Phone number (exactly 10 digits)
     * @param address Address (max 30 chars)
     * @throws IllegalArgumentException if any parameter fails validation
     */
    public Contact(String contactID, String firstName, String lastName, String phone, String address) {
        this.contactID = validateField("Contact ID", contactID, MAX_ID_LENGTH, false);
        this.firstName = validateField("First name", firstName, MAX_NAME_LENGTH, false);
        this.lastName = validateField("Last name", lastName, MAX_NAME_LENGTH, false);
        this.phone = validatePhone(phone);
        this.address = validateField("Address", address, MAX_ADDRESS_LENGTH, false);
    }

    // Getter methods
    public String getContactID() { return contactID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    // Setter methods with validation
    public synchronized void setFirstName(String firstName) {
        this.firstName = validateField("First name", firstName, MAX_NAME_LENGTH, false);
    }

    public synchronized void setLastName(String lastName) {
        this.lastName = validateField("Last name", lastName, MAX_NAME_LENGTH, false);
    }

    public synchronized void setPhone(String phone) {
        this.phone = validatePhone(phone);
    }

    public synchronized void setAddress(String address) {
        this.address = validateField("Address", address, MAX_ADDRESS_LENGTH, false);
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

    private String validatePhone(String phone) {
        String validatedPhone = validateField("Phone number", phone, PHONE_LENGTH, true);
        if (!validatedPhone.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Phone number must contain only digits. Provided: '" + phone + "'");
        }
        return validatedPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return contactID.equals(contact.contactID);
    }

    @Override
    public int hashCode() {
        return contactID.hashCode();
    }

    @Override
    public synchronized String toString() {
        return String.format("Contact[ID=%s, firstName=%s, lastName=%s, phone=%s, address=%s]",
                           contactID, firstName, lastName, phone, address);
    }
}