/**
 * Class Name: Room
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.room;

import cst8284.asgmt4.roomScheduler.RoomScheduler;

public abstract class Room {

	// Superclass: Room
	// Cannot instantiate an object from the abstract class itself.

	/**
	 * Variable for Serializable
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Constant for default room number is "unknown room number"
	 */
	protected static final String DEFAULT_ROOM = "unknown room number";

	/**
	 * Stores the real room number
	 */
	private String roomNumber;

	/**
	 * No-arg/the 1st constructor
	 * <p>
	 * Chain to the 2nd constructor with the DEFUALT_ROOM constant
	 */
	protected Room() {
		this(DEFAULT_ROOM);
	}

	/**
	 * 1-param/the 2nd constructor
	 * <p>
	 * Sets the room number of the room by calling its setter
	 * 
	 * @param roomNum (reference type: String)
	 */
	protected Room(String roomNum) {
		setRoomNumber(roomNum);
	}

	/**
	 * Sets roomNumber
	 * <p>
	 * Incoming data cannot be null or blank
	 * 
	 * @param roomNum (reference type: String)
	 */
	public void setRoomNumber(String roomNum) {
		if (!RoomScheduler.errorOnNull(roomNum, null) && !RoomScheduler.errorOnBlank(roomNum, null))
			roomNumber = roomNum;
	}

	/**
	 * Get the stored value of the roomNumber private field
	 * 
	 * @return roomNumber (reference type: String)
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Abstract method declaration to be extended by subclasses <br>
	 * It will return the number of seats (primitive type: int) in a room, defined
	 * in the subclass
	 */
	protected abstract int getSeats();

	/**
	 * Abstract method declaration to be extended by subclasses <br>
	 * It will return type of the room (reference type: String), defined in the
	 * subclass
	 */
	protected abstract String getRoomType();

	/**
	 * Abstract method declaration to be extended by subclasses <br>
	 * It will return details about the room (reference type: String), defined in
	 * the subclass
	 */
	protected abstract String getDetails();

	/**
	 * Returns a compiled string of all room data with formatting
	 * 
	 * @return formatted room data (reference type: String)
	 */
	public String toString() {
		return getRoomNumber() + " (" + getRoomType() + " / " + getSeats() + " seats / " + getDetails() + ")";
	}
} // END Room class
