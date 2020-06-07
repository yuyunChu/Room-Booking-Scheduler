/**
 * Class Name: TimeBlock
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

import java.util.Calendar;
import java.io.Serializable;

public class TimeBlock implements Serializable {

	/**
	 * Variable for Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The start and end time of the TimeBlock<br>
	 * Used by a RoomBooking event, both Calendar objects will have the same date
	 * but different times in RoomBooking application<br>
	 * endTime must be after startTime
	 */
	private Calendar startTime, endTime;

	/**
	 * Default no-arg constructor
	 * <p>
	 * Assigns default values of startTime to "today" at 8am, and endTime to "today"
	 * at 11pm.<br>
	 * Chain default values to this(Calendar, Calendar)
	 */
	public TimeBlock() {
		this(new Calendar.Builder().set(Calendar.HOUR_OF_DAY, 8).build(),
				new Calendar.Builder().set(Calendar.HOUR_OF_DAY, 23).build());
	}

	/**
	 * Parameterized constructor sets TimeBlock's start time and end time using
	 * respective setters
	 * 
	 * @param start (reference type: Calendar); the start time using Calendar class
	 * @param end   (reference type: Calendar); the end time using Calendar class
	 */
	public TimeBlock(Calendar start, Calendar end) {
		setStartTime(start);
		setEndTime(end);
	}

	@Override
	/**
	 * Returns the hours of the start and end times of the TimeBlock in 24 hour
	 * time. It does not return the date.
	 * 
	 * @return the TimeBlock string data (reference type: String)
	 */
	public String toString() {
		return getStartTime().get(Calendar.HOUR_OF_DAY) + ":00 - " + getEndTime().get(Calendar.HOUR_OF_DAY) + ":00\n";
	}

	/**
	 * Sets startTime
	 * <p>
	 * Incoming data cannot be null
	 * 
	 * @param startTime (reference type: Calendar)
	 */
	public void setStartTime(Calendar startTime) {
		if (!RoomScheduler.errorOnNull(startTime, RoomBookingDialog.getField(3)))
			this.startTime = startTime;
	}

	/**
	 * Get the stored value of the startTime private field
	 * 
	 * @return startTime (reference type: Calendar)
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * Sets endTime
	 * <p>
	 * Incoming data cannot be null, and the endTime must be after the startTime
	 * 
	 * @param endTime (reference type: Calendar)
	 * @throws BadRoomBookingException if the endTime is before the startTime
	 * @throws BadRoomBookingException if the endTime and startTime are equal
	 */
	public void setEndTime(Calendar endTime) {

		if (!RoomScheduler.errorOnNull(endTime, RoomBookingDialog.getField(5))) {
			if (this.startTime.compareTo(endTime) > 0) {
				throw new BadRoomBookingException("End time precedes start time",
						"The room booking start time must occur before the end time of the room booking.");
			} else if (this.startTime.equals(endTime)) {
				throw new BadRoomBookingException("Zero time room booking",
						"Start and end time of the room booking are the same. The minimum time for a room booking is one hour.");
			} else
				this.endTime = endTime;
		}
	}

	/**
	 * Get the stored value of the endTime private field
	 * 
	 * @return endTime (reference type: Calendar)
	 */
	public Calendar getEndTime() {
		return endTime;
	}

	/**
	 * Get the calculated duration of the TimeBlock as an int
	 * <p>
	 * Calculate the difference (in hours) between the start and end times
	 * 
	 * @return the duration in hours, as an int, will always be 24 hours or less in
	 *         RoomBooking application (primitive type: int)
	 */
	public int duration() {
		return (getEndTime().get(Calendar.HOUR_OF_DAY) - getStartTime().get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * Returns whether 2 time blocks overlap.
	 * 
	 * @param tb (reference type: TimeBlock)
	 * @return true if they overlap, false if they do not (primitive type: boolean)
	 */
	public boolean overlaps(TimeBlock tb) {
		if ((tb.getStartTime().get(Calendar.DAY_OF_YEAR) != this.getStartTime().get(Calendar.DAY_OF_YEAR))
				|| (tb.getStartTime().get(Calendar.YEAR) != this.getStartTime().get(Calendar.YEAR)))
			return false; // can't overlap; not on same date
		return ((tb.getStartTime().get(Calendar.HOUR_OF_DAY) < getEndTime().get(Calendar.HOUR_OF_DAY))
				&& (tb.getEndTime().get(Calendar.HOUR_OF_DAY) > getStartTime().get(Calendar.HOUR_OF_DAY)));
		// same date, but do the two timeblocks overlap?
	}

} // END TimeBlock class
