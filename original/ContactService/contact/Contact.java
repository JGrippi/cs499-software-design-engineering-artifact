/* Joey Grippi
 * 
 * CS-320
 * 
 * Project One
 * 
 * April 13, 2024
 */


package contact;

/**
 * Public class Contact
 */

public class Contact {
	
	// Initializing private string variables for the Contact class
	private String contactID;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	
	/**
	 * Contact class constructor with conditional checks,
	 * throws exceptions if data is incorrect
	 */
	
	public Contact(String contactID, 
			String firstName, 
			String lastName, 
			String phone, 
			String address) {
		
		/**
		 * Checking to see if contactID is null, empty, and is no longer than 10 characters,
		 * throws illegal argument exception if incorrect
		 */
		
		if (contactID == null || contactID.isEmpty() || contactID.length() > 10) {
			throw new IllegalArgumentException("Invalid ID");
		}
		
		/**
		 * Checking to see if firstName is null, empty, and is no longer than 10 characters,
		 * throws illegal argument exception if incorrect
		 */
		
		if (firstName == null || firstName.isEmpty() || firstName.length() > 10) {
			throw new IllegalArgumentException("Invalid first name");
		}
		
		/**
		 * Checking to see if lastName is null, empty, and is no longer than 10 characters,
		 * throws illegal argument exception if incorrect
		 */
		
		if (lastName == null || lastName.isEmpty() || lastName.length() > 10) {
			throw new IllegalArgumentException("Invalid last name");
		}
		
		/**
		 * Checking to see if phone is null, empty, and is exactly 10 characters,
		 * throws illegal argument exception if incorrect
		 */
		
		if (phone == null || phone.isEmpty() || phone.length() != 10) {
			throw new IllegalArgumentException("Invalid phone number");
		}
		
		/**
		 * Checking to see if address is null, empty, and is no longer than 30 characters,
		 * throws illegal argument exception if incorrect
		 */
		
		if (address == null || address.isEmpty() || address.length() > 30) {
			throw new IllegalArgumentException("Invalid address");
		}
		
		/**
		 * If the conditions pass, the objects are set accordingly
		 */
		
		this.contactID = contactID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
	}
	
	/**
	 * Getter methods
	 */
	
	public String getContactID() {
		return contactID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	/**
	 * Setter methods
	 * 
	 * No setter method for contactID because it shouldn't be updated
	 */
	
	/**
	 * Sets the first name if the data is validated
	 * @param firstName
	 */
	
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.isEmpty() || firstName.length() > 10) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}
	
	/**
	 * Sets the last name if the data is validated
	 * @param lastName
	 */
	
	public void setLastName(String lastName) {
		if (lastName == null || lastName.isEmpty() || lastName.length() > 10) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}
	
	/**
	 * Sets the phone if the data is validated
	 * @param phone
	 */
	
	public void setPhone(String phone) {
		if (phone == null || phone.isEmpty() || phone.length() != 10) {
			throw new IllegalArgumentException("Invalid phone number");
		}
		this.phone = phone;
	}
	
	/**
	 * Sets the address if the data is validated
	 * @param address
	 */
	
	public void setAddress(String address) {
		if (address == null || address.isEmpty() || address.length() > 30) {
			throw new IllegalArgumentException("Invalid address");
		}
		this.address = address;
	}
}