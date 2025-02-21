/* Joey Grippi
 * 
 * CS-320
 * 
 * Project One
 * 
 * April 13, 2024
 */

package contact;

import java.util.ArrayList;


/**
 * Public class ContactService
 */

public class ContactService {
	
	// Creating the Array list to hold the contactss
	public static ArrayList<Contact>contacts = new ArrayList<>();
	
	/**
	 * Checking for the existence of unique contact ID, adding if it doesn't exist
	 * @param contact
	 * @return
	 */
	
	public static boolean addContact(Contact contact) {
		
		// Initially setting the boolean to false
		boolean contactExists = false;
		
		// For loop to check for the existence of contact ID
		for (Contact contactList : contacts) {
		
			// If the contacts ID exists, set boolean to true
			if (contactList.getContactID().equals(contact.getContactID())) {
				contactExists = true;
			}	
		}
		
		// If the contact ID doesn't exist, add contact to array
		if (!contactExists) {
			contacts.add(contact);
		}
		
		// Return the value of the boolean
		return contactExists;
	}

	/**
	 * Delete contact per contact ID
	 * @param contactID
	 */
	
	public static void deleteContact(String contactID) {
		
		// For loop to find the contact ID in the array
		for(Contact contactList : contacts) {
			
			// If the contact ID is found in the array, delete the contact from the array
			if(contactList.getContactID() == contactID) {
				contacts.remove(contactList);
				return;
			}
		}
	}
	
	/**
	 * Update first name per contact ID
	 * @param contactID
	 * @param firstName
	 */
	
	public static void updateFirstName(String contactID, String firstName) {
		
		// For loop to find the contact ID in the array
		for(Contact contactList : contacts) {
			
			// If the contact ID is found in the array, update the first name
			if(contactList.getContactID() == contactID) {
				contactList.setFirstName(firstName);	
				return;
			}			
		}	
	}
	
	/**
	 * Helper method for the updateFirstName() function to return firstName per ID
	 * @param contactID
	 * @return
	 */
	
    public static String getFirstNameFromID(String contactID) {
        
    	// For loop to find the contact ID in the array
    	for (Contact contactList : contacts) {
        	
    		// If the contact ID is found in the array, return the first name
    		if(contactList.getContactID() == contactID) {
                return contactList.getFirstName();
            }
        }
    	
    	// Returns a null string if a contact ID isn't matched
        return null;
    }
    
	/**
	 * Update last name per contact ID
	 * @param contactID
	 * @param lastName
	 */
	
	public static void updateLastName(String contactID, String lastName) {
		
		// For loop to find the contact ID in the array
		for(Contact contactList : contacts) {
			
			// If the contact ID is found in the array, update the last name
			if(contactList.getContactID() == contactID) {
				contactList.setLastName(lastName);	
				return;
			}			
		}	
	}
	
	/**
	 * Helper method for the updateLastName() function to return lastName per ID
	 * @param contactID
	 * @return
	 */
	
    public static String getLastNameFromID(String contactID) {
        
    	// For loop to find the contact ID in the array
    	for (Contact contactList : contacts) {
        	
    		// If the contact ID is found in the array, return the last name
    		if(contactList.getContactID() == contactID) {
                return contactList.getLastName();
            }
        }
    	
    	// Returns a null string if a contact ID isn't matched
        return null;
    }
    
	/**
	 * Update phone number per contact ID
	 * @param contactID
	 * @param phone
	 */
	
	public static void updatePhone(String contactID, String phone) {
		
		// For loop to find the contact ID in the array
		for(Contact contactList : contacts) {
			
			// If the contact ID is found in the array, update the phone number
			if(contactList.getContactID() == contactID) {
				contactList.setPhone(phone);	
				return;
			}			
		}	
	}
	
	/**
	 * Helper method for the updatePhone() function to return phone per ID
	 * @param contactID
	 * @return
	 */
	
    public static String getPhoneFromID(String contactID) {
        
    	// For loop to find the contact ID in the array
    	for (Contact contactList : contacts) {
        	
    		// If the contact ID is found in the array, return the phone number
    		if(contactList.getContactID() == contactID) {
                return contactList.getPhone();
            }
        }
    	
    	// Returns a null string if a contact ID isn't matched
        return null;
    }
    
	/**
	 * Update address per contact ID
	 * @param contactID
	 * @param address
	 */
	
	public static void updateAddress(String contactID, String address) {
		
		// For loop to find the contact ID in the array
		for(Contact contactList : contacts) {
			
			// If the contact ID is found in the array, update the address
			if(contactList.getContactID() == contactID) {
				contactList.setAddress(address);	
				return;
			}			
		}	
	}
	
	/**
	 * Helper method for the updateAddress() function to return address per ID
	 * @param contactID
	 * @return
	 */
	
    public static String getAddressFromID(String contactID) {
        
    	// For loop to find the contact ID in the array
    	for (Contact contactList : contacts) {
        	
    		// If the contact ID is found in the array, return the address
    		if(contactList.getContactID() == contactID) {
                return contactList.getAddress();
            }
        }
    	
    	// Returns a null string if a contact ID isn't matched
        return null;
    }
}