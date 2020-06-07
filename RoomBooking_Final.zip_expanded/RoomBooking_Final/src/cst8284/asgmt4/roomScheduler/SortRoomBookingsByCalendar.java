/**
 * Class Name: SortRoomBookingsByCalendar
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

import java.util.Comparator;

public class SortRoomBookingsByCalendar implements Comparator<RoomBooking> {

	/**
	 * Implements compare function with roomBooking
	 * <p>
	 * If 2 TimeBlocks overlap, their RoomBookings should be considered equal. <br>
	 * Otherwise, compare by start time.
	 * 
	 * @param o1 (reference type: RoomBooking)
	 * @param o2 (reference type: RoomBooking)
	 * @return 0 means equal, 1 means o1 after o2, and -1 means o2 after o1
	 *         (primitive type: int)
	 */
	@Override
	public int compare(RoomBooking o1, RoomBooking o2) {
		if (o1.getTimeBlock().overlaps(o2.getTimeBlock()))
			return 0;
		else if (o1.getTimeBlock().getStartTime().after(o2.getTimeBlock().getStartTime())) {
			return 1;
		} else {
			return -1;
		}
	}

} // END SortRoomBookingsByCalendar class
