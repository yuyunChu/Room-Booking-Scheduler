/**
 * Class Name: ContactInfo
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

import java.awt.Color;
import java.io.Serializable;

public class ContactInfo implements Serializable {

	/**
	 * Variable for Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Private fields store data about the person or organization booking the room
	 * 
	 */
	private String firstName, lastName, phoneNumber, organization;

	/**
	 * Parameterized constructor, takes 3 string parameters
	 * <p>
	 * Doesn't take organization but sets a default value of "Algonquin College"<br>
	 * Chains to this(String firstName, String lastName, String phoneNumber, String
	 * organization) ContactInfo constructor below
	 * 
	 * @param fullName    for a first name of contact info (reference type: String),
	 *                    for a last name of contact info (reference type: String)
	 * @param phoneNumber for a phone number of contact info (reference type:
	 *                    String)
	 */
	public ContactInfo(String fullName, String phoneNumber) {
		this(fullName, phoneNumber, "Algonquin College");
	}

	/**
	 * Parameterized constructor, takes 4 parameters
	 * <p>
	 * Sets all private fields using respective setters Chained to from 3 parameter
	 * constructor
	 * 
	 * @param fullName     for a first name of contact info (reference type:
	 *                     String), for a last name of contact info (reference type:
	 *                     String)
	 * 
	 * @param phoneNumber  for a phone number of contact info (reference type:
	 *                     String)
	 * @param organization for an organization of contact info (reference type:
	 *                     String)
	 */
	public ContactInfo(String fullName, String phoneNumber, String organization) {

		if (!RoomScheduler.errorOnNull(fullName, RoomBookingDialog.getField(7))
				&& !RoomScheduler.errorOnBlank(fullName, RoomBookingDialog.getField(7))) {
			String[] fullNameArray = fullName.split(" ");
			if (fullNameArray.length != 2) {
				throw new BadRoomBookingException("Invalid name", "Please enter in the format: Firstname Lastname",
						RoomBookingDialog.getField(7));
			}
			setFirstName(fullNameArray[0]);
			setLastName(fullNameArray[1]);
			setPhoneNumber(phoneNumber);
			setOrganization(organization);
		}

	}

	/**
	 * Sets firstName
	 * <p>
	 * Only sets the first name if the firstName argument value is of an acceptable
	 * format<br>
	 * Name format is verified by verifyName(String) method
	 * 
	 * @param firstName (reference type: String)
	 * 
	 */
	public void setFirstName(String firstName) {
		if (verifyName(firstName))
			this.firstName = firstName;
	}

	/**
	 * Get the stored value of the firstName private field
	 * 
	 * @return firstName (reference type: String)
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets lastName
	 * <p>
	 * Only sets the last name if the lastName argument value is of an acceptable
	 * format<br>
	 * Name format is verified by verifyName(String) method
	 * 
	 * @param lastName (reference type: String)
	 * 
	 */
	public void setLastName(String lastName) {
		if (verifyName(lastName))
			this.lastName = lastName;
	}

	/**
	 * Get the stored value of the lastName private field
	 * 
	 * @return lastName (reference type: String)
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets phoneNumber
	 * <p>
	 * Only sets the phone number if the phoneNumber argument value is of an
	 * acceptable format<br>
	 * Phone number format must be like 123-123-1234<br>
	 * 3 blocks of numbers, separated by "-", 10 digits total (String of length 12).
	 * 
	 * @param phoneNumber (reference type: String)
	 * @throws BadRoomBookingException for invalid string length or missing "-"
	 * @throws BadRoomBookingException for invalid number if digit blocks (not 3)
	 * @throws BadRoomBookingException if any of the digit blocks do not contain
	 *                                 digits (catches a NumberFormatException)
	 */
	public void setPhoneNumber(String phoneNumber) {

		if (!RoomScheduler.errorOnNull(phoneNumber, RoomBookingDialog.getField(9))
				&& !RoomScheduler.errorOnBlank(phoneNumber, RoomBookingDialog.getField(9))) {

			if (!phoneNumber.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$")) {
				throw new BadRoomBookingException("Bad telephone number",
						"The telephone number must be a 10-digit number, separated by '-' in the form, e.g. 613-555-1212.",
						RoomBookingDialog.getField(9));
			}

			this.phoneNumber = phoneNumber;
		}

	}

	/**
	 * Get the stored value of the phoneNumber private field
	 * 
	 * @return phoneNumber (reference type: String)
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets organization
	 * <p>
	 * Incoming data cannot be null, but it can be blank
	 * 
	 * @param organization (reference type: Calendar)
	 */
	public void setOrganization(String organization) {
		if (!RoomScheduler.errorOnNull(organization, RoomBookingDialog.getField(11)))
			this.organization = organization;
	}

	/**
	 * Get the stored value of the organization private field
	 * 
	 * @return organization (reference type: String)
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * Verifies the format of a name (used to set firstName and lastName)
	 * <p>
	 * A name:
	 * <ul>
	 * <li>cannot be null or blank
	 * <li>must have only alphabetic characters, or "-", "'", and "."
	 * <li>must be 30 characters or less
	 * </ul>
	 * 
	 * @param name (reference type: String)
	 * @return true for not having any errors (primitive type: boolean)
	 * @throws BadRoomBookingException for any non-alphabetic charters (other than
	 *                                 "-", "'", and ".")
	 * @throws BadRoomBookingException for string length greater than 30
	 */
	public boolean verifyName(String name) {

		if (!RoomScheduler.errorOnNull(name, RoomBookingDialog.getField(7))
				&& !RoomScheduler.errorOnBlank(name, RoomBookingDialog.getField(7))) {

			if (!name.matches("^[A-Za-z\\-'\\.]+$")) {
				throw new BadRoomBookingException("Name contains illegal characters",
						"A name cannot include characters other than alphabetic characters, the dash (-), the period (.), and the apostrophe (').",
						RoomBookingDialog.getField(7));
			} else if (name.length() > 30) {
				throw new BadRoomBookingException("Name length exceeded",
						"The first or last name exceeds the 30 character maximum allowed.",
						RoomBookingDialog.getField(7));
			}

		}

		return true;
	}

	/**
	 * Returns a compiled string of all contact info with formatting, omitting
	 * organization if that field is blank
	 * 
	 * @return formatted contact info data (reference type: String)
	 */
	@Override
	public String toString() {
		return "Contact Information: " + ((getFirstName() != "") ? (getFirstName() + " " + getLastName()) : "") + "\n"
				+ "Phone: " + getPhoneNumber()
				+ ((getOrganization().equals("")) ? "" : ("\n" + getOrganization() + "\n"));
	}
}