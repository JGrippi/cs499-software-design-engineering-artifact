package contact;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread-safe service class for managing contacts.
 * Implements CRUD operations with performance optimization and proper synchronization.
 */
public final class ContactService {
    // Thread-safe storage using ConcurrentHashMap for O(1) lookups
    private static final ConcurrentHashMap<String, Contact> contactMap = new ConcurrentHashMap<>();
    
    // Lock for synchronizing ArrayList operations
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    
    // Internal list for maintaining order
    private static final ArrayList<Contact> contactList = new ArrayList<>();

    // Private constructor
    private ContactService() {}

    /**
     * Adds a new contact to the service.
     * @param contact The contact to add
     * @return true if contact already exists, false if added successfully
     * @throws IllegalArgumentException if contact is null
     */
    public static boolean addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }

        if (contactMap.putIfAbsent(contact.getContactID(), contact) != null) {
            return true; // Contact already exists
        }

        lock.writeLock().lock();
        try {
            contactList.add(contact);
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Deletes a contact by ID.
     * @param contactID The ID of the contact to delete
     * @return true if contact was deleted, false if not found
     */
    public static boolean deleteContact(String contactID) {
        Contact removed = contactMap.remove(contactID);
        if (removed != null) {
            lock.writeLock().lock();
            try {
                contactList.remove(removed);
                return true;
            } finally {
                lock.writeLock().unlock();
            }
        }
        return false;
    }

    /**
     * Updates a contact field using a provided update function.
     * @param contactID The ID of the contact to update
     * @param updater Function to apply the update
     * @return true if contact was updated, false if not found
     */
    private static boolean updateContactField(String contactID, java.util.function.Consumer<Contact> updater) {
        Contact contact = contactMap.get(contactID);
        if (contact != null) {
            updater.accept(contact);
            return true;
        }
        return false;
    }

    /**
     * Updates the first name of a contact.
     * @param contactID The ID of the contact to update
     * @param firstName The new first name
     * @return true if updated successfully, false if contact not found
     */
    public static boolean updateFirstName(String contactID, String firstName) {
        return updateContactField(contactID, contact -> contact.setFirstName(firstName));
    }

    /**
     * Updates the last name of a contact.
     * @param contactID The ID of the contact to update
     * @param lastName The new last name
     * @return true if updated successfully, false if contact not found
     */
    public static boolean updateLastName(String contactID, String lastName) {
        return updateContactField(contactID, contact -> contact.setLastName(lastName));
    }

    /**
     * Updates the phone number of a contact.
     * @param contactID The ID of contact to update
     * @param phone The new phone number
     * @return true if updated successfully, false if contact not found
     */
    public static boolean updatePhone(String contactID, String phone) {
        return updateContactField(contactID, contact -> contact.setPhone(phone));
    }

    /**
     * Updates the address of a contact.
     * @param contactID The ID of contact to update
     * @param address The new address
     * @return true if updated successfully, false if contact not found
     */
    public static boolean updateAddress(String contactID, String address) {
        return updateContactField(contactID, contact -> contact.setAddress(address));
    }

    /**
     * Gets a value from a contact using a provided getter function.
     * @param contactID The ID of the contact
     * @param getter Function to get the desired value
     * @return The requested value or null if contact not found
     */
    private static <T> T getContactValue(String contactID, java.util.function.Function<Contact, T> getter) {
        return Optional.ofNullable(contactMap.get(contactID))
                      .map(getter)
                      .orElse(null);
    }

    // Getter methods using the generic getContactValue method
    public static String getFirstNameFromID(String contactID) {
        return getContactValue(contactID, Contact::getFirstName);
    }

    public static String getLastNameFromID(String contactID) {
        return getContactValue(contactID, Contact::getLastName);
    }

    public static String getPhoneFromID(String contactID) {
        return getContactValue(contactID, Contact::getPhone);
    }

    public static String getAddressFromID(String contactID) {
        return getContactValue(contactID, Contact::getAddress);
    }

    /**
     * Gets a list of all contacts.
     * @return A new List containing all contacts
     */
    public static List<Contact> getAllContacts() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(contactList);
        } finally {
            lock.readLock().unlock();
        }
    }
}