/**
 * Class Name: Classroom
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.room;

public final class Classroom extends Room {

	/**
	 * Default constant for number of seats in the room
	 */
	private static final int DEFAULT_SEATS = 120;

	/**
	 * stores the actual number of seats
	 */
	private int seats;

	/**
	 * Default no-arg constructor
	 * <p>
	 * Call super() constructor to define local room number, and set the number of
	 * seats from the default constructor
	 */
	public Classroom() {
		this(Room.DEFAULT_ROOM);
	}
	
	public Classroom(String roomNumber) {
		super(roomNumber);
		seats = DEFAULT_SEATS;
	}

	/**
	 * Concrete method:
	 * Gets the number of seats in the room
	 * <p> 
	 * Concrete method overridden from superclass room
	 * Seats are stored as a private method
	 * 
	 * @return seats (primitive type: int)
	 */
	protected int getSeats() {
		return seats;
	}

	/**
	 * Concrete method:
	 * Gets the type of room
	 * <p> 
	 * Concrete method overridden from superclass room
	 * The type is hardcoded into the method
	 * 
	 * @return seats (reference type: String)
	 */
	protected String getRoomType() {
		return "classroom";
	}

	/**
	 * Concrete method:
	 * Gets the details of the room
	 * <p> 
	 * Concrete method overridden from superclass room
	 * Details are hardcoded into the method
	 * 
	 * @return seats (reference type: String)
	 */
	protected String getDetails() {
		return "contains overhead projector";
	}
} // END Classroom class
