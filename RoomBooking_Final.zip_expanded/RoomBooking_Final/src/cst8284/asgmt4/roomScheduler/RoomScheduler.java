/**
 * Class Name: RoomScheduler
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

import cst8284.asgmt4.room.Room;
//import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
import java.text.ParseException;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class RoomScheduler {

	public static final long serialVersionUID = 1L;
	/**
	 * Declare room instance for the room currently being scheduled, initialized by
	 * the RoomScheduler class' default constructor
	 */
	private Room room;

	/**
	 * rooBmookings stores ArrayList of all bookings from RoomBooking class through
	 * user input or load from the CurrentRoomBookings.book file
	 */
	private ArrayList<RoomBooking> roomBookings = new ArrayList<>();

	/**
	 * Named constants, used in the main menu
	 */
	private static final int DISPLAY_ROOM_INFORMATION = 1, ENTER_ROOM_BOOKING = 2, DELETE_BOOKING = 3,
			CHANGE_BOOKING = 4, DISPLAY_BOOKING = 5, DISPLAY_DAY_BOOKINGS = 6, SAVE_BOOKINGS_TO_FILE = 7,
			LOAD_BOOKINGS_FROM_FILE = 8, EXIT = 0;

	/**
	 * Parameterized constructor: Set the room which is current room being scheduled
	 * 
	 * @param room (reference type: room)
	 * 
	 */
	public RoomScheduler(Room room) {
		setRoom(room);
	}

	/**
	 * Setter: Set the room, called from the constructor
	 * 
	 * @param room is the current room being scheduled (reference type: Room)
	 */
	private void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Getter: Get the room, returned from the room in the private field
	 * 
	 * @return the current room being scheduled (reference type: Room)
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * RoomBooking launcher, displays main menu (loops until user selects Exit)
	 * Catches all BadRoomBookingException type exceptions
	 */
	public void launch() {

		RoomSchedulerDialog RSD = new RoomSchedulerDialog(this);

		RSD.showSchedulerDialog();

		try {

		} catch (BadRoomBookingException ex) {
			errorDialog(ex);
		}

	}

	public static RoomBooking makeBookingFromUserInput(String fullName, String phoneNumber, String organization,
			String category, String description, Calendar startCal, Calendar endCal) {

		ContactInfo contactInfo = new ContactInfo(fullName, phoneNumber, organization);
		Activity activity = new Activity(category, description);
		TimeBlock timeBlock = new TimeBlock(startCal, endCal);
		return (new RoomBooking(contactInfo, activity, timeBlock));
	}


	// https://beginnersbook.com/2013/05/java-date-format-validation/
	/**
	 * Gets input from user's date and time to create a Calendar object
	 * <p>
	 * Can set calendar with just the day (initCal = null, requestHour = false)<br>
	 * Or the day and the time (initCal = null, requestHour = true)<br>
	 * Or add a time to an existing calendar object (initCal = Calendar, requestHour
	 * = true)
	 *
	 * @param date    
	 * @param time
	 * @return the Calendar object created from user input (reference type:
	 *         Calendar)
	 * @throws BadRoomBookingException with try-catch block of ParseException, to
	 *                                 check for bad date input (invalid date or
	 *                                 invalid characters)
	 */
	public static Calendar makeCalendarFromUserInput(String date, String time) {

		Calendar cal = Calendar.getInstance();
		cal.clear();

		int hour = 0;

		if (!errorOnNull(date, RoomBookingDialog.getField(1)) && !errorOnBlank(date, RoomBookingDialog.getField(1))) {

			SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyyMMdd");
			sdfrmt.setLenient(false);

			try {
				sdfrmt.parse(date);
			} catch (ParseException e) {
				RoomBookingDialog.getField(1).setBackground(new Color(255, 255, 165));
				throw new BadRoomBookingException("Bad Calendar format",
						"Bad calendar date was entered. The correct format is YYYYMMDD.");
			}
		}

		int day = Integer.parseInt(date.substring(6, 8));
		int month = Integer.parseInt(date.substring(4, 6)) - 1;
		int year = Integer.parseInt(date.substring(0, 4));

		if (!time.equals(""))
			hour = processTimeString(time);

		cal.set(year, month, day, hour, 0);
		return (cal);
	}


	public static Calendar makeCalendarFromUserInput(String date, JTextField time) {

		Calendar cal = Calendar.getInstance();
		cal.clear();

		int hour = 0;

		if (!errorOnNull(date, RoomBookingDialog.getField(1)) && !errorOnBlank(date, RoomBookingDialog.getField(1))) {

			SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyyMMdd");
			sdfrmt.setLenient(false);

			try {
				sdfrmt.parse(date);
			} catch (ParseException e) {
				RoomBookingDialog.getField(1).setBackground(new Color(255, 255, 165));
				throw new BadRoomBookingException("Bad Calendar format",
						"Bad calendar date was entered. The correct format is YYYYMMDD.");
			}
		}

		int day = Integer.parseInt(date.substring(6, 8));
		int month = Integer.parseInt(date.substring(4, 6)) - 1;
		int year = Integer.parseInt(date.substring(0, 4));

		if (!errorOnNull(time.getText(), time) && !errorOnBlank(time.getText(), time)) 
			hour = processTimeString(time);

		cal.set(year, month, day, hour, 0);
		return (cal);
	}

	/**
	 * Converts user input hour from a string to an int, to be added to a Calendar
	 * object.
	 * <p>
	 * Acceptable string hour formats: 1 pm ; 1 p.m. ; 13:00
	 *
	 * @param t (reference type: String) the string based time to be converted
	 * @return hour (primitive type: int) to be added to a calendar object
	 */
	public static int processTimeString(String t) {

		int hour = 0;

		t = t.trim();

		if (!t.matches("^([0-2]?[0-9]:00)|([01]?[0-9] [ap](\\.)?m(\\.?))$")) {
			throw new BadRoomBookingException("Time is invalid", "Enter time format as 13:00 or 1 pm");
		}
		if (!errorOnNull(t, RoomBookingDialog.getField(3)) && !errorOnBlank(t, RoomBookingDialog.getField(3))) {
			if (t.contains("pm") || (t.contains("p.m.")))
				hour = Integer.parseInt(t.split(" ")[0]) + 12;
			else if (t.contains("am") || t.contains("a.m."))
				hour = Integer.parseInt(t.split(" ")[0]);
			else if (t.contains(":"))
				hour = Integer.parseInt(t.split(":")[0]);
		}

		return hour;
	}

	public static int processTimeString(JTextField t1) {

		int hour = 0;

		String t = t1.getText().trim();

		if (!t.matches("^([0-2]?[0-9]:00)|([01]?[0-9] [ap](\\.)?m(\\.?))$")) {
			throw new BadRoomBookingException("Time is invalid", "Enter time format as 13:00 or 1 pm", t1);
		}

		if (t.contains("pm") || (t.contains("p.m.")))
			hour = Integer.parseInt(t.split(" ")[0]) + 12;
		else if (t.contains("am") || t.contains("a.m."))
			hour = Integer.parseInt(t.split(" ")[0]);
		else if (t.contains(":"))
			hour = Integer.parseInt(t.split(":")[0]);
		return hour;
	}

	/**
	 * Searches roomBookings ArrayList for any booking that matches (or overlaps) a
	 * specific day and time
	 * <p>
	 * Uses binarySearch() method to search for the matching date/time of a
	 * RoomBooking's timeBlock object,<br>
	 * and returns its index if found, or null if no matching timeBlock was found
	 * 
	 * 
	 * @param cal (reference type: Calendar) the day and time to search for
	 * @return the roomBooking that matches the search parameter, or null (reference
	 *         type: RoomBooking)
	 */
	public RoomBooking findBooking(Calendar cal) {

		Calendar oneHourLater = (Calendar) cal.clone();
		oneHourLater.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);

		RoomBooking rb = new RoomBooking();
		rb.setTimeBlock(new TimeBlock(cal, oneHourLater));
		int index = Collections.binarySearch(roomBookings, rb, new SortRoomBookingsByCalendar());

		if (index < 0) {
			return null;
		}

		return roomBookings.get(index);
	}

	/**
	 * Saves room booking data to the RoomBookings ArrayList
	 * <p>
	 * Searches through existing RoomBooking TimeBlocks to see if the new<br>
	 * entry's TimeBlock overlaps any, and returns false if an conflict is
	 * found.<br>
	 * If there's no conflict, it adds the new RoomBooking to the ArrayList, sorts
	 * the list, and returns true.
	 * 
	 * @param roomBooking (reference type: RoomBooking) the RoomBooking object to
	 *                    save
	 * @return the result of the save process (primitive type: boolean; true =
	 *         success, false = fail)
	 */
	public boolean saveRoomBooking(RoomBooking roomBooking) {
		TimeBlock tb = roomBooking.getTimeBlock(); // Check this TimeBlock to see if already booked
		Calendar cal = (Calendar) tb.getStartTime().clone(); // use its Calendar
		int hour = cal.get(Calendar.HOUR_OF_DAY);// Get first hour of block
		for (; hour < tb.getEndTime().get(Calendar.HOUR_OF_DAY); hour++) { // Loop through each hour in TimeBlock
			cal.set(Calendar.HOUR_OF_DAY, hour); // set next hour
			if (findBooking(cal) != null) { // TimeBlock already booked at that hour, can't add appointment
				throw new BadRoomBookingException("Could not save", "That time-block is already booked.");
			}
		} // else time slot still available; continue loop to next hour
		getRoomBookings().add(roomBooking);
		messageDialog("Success!", "Booking time and date saved.");
		Collections.sort(roomBookings, new SortRoomBookingsByCalendar());
		return true;
	}

	/**
	 * Deletes a RoomBooking from the RoomBookings list
	 * <p>
	 * Displays the existing booking data for the date & time requested,<br>
	 * and asks for confirmation to delete that entry (if it exists).
	 *
	 * @param rb (reference type: RoomBooking) the day and time for the entry to be
	 *            deleted
	 * @return the result of the deletion (true = success, false = fail)
	 */
	public boolean deleteBooking(RoomBooking rb) {
		if (rb != null) {
			// int index = getRoomBookings().indexOf(rb);

			getRoomBookings().remove(rb);
			return true;
		}
		return false;
	}

	/**
	 * Alters an existing RoomBooking
	 * <p>
	 * Searches for a matching booking at the date & time specified by cal, and
	 * requests a new start & end time for it from the user.<br>
	 * Checks that the new time doesn't overlap any existing bookings.<br>
	 * Sorts the list after the time is updated.
	 * 
	 * @param cal (reference type: Calendar) containing the day and time of the
	 *            booking to altered
	 * @return the result of finding & saving the booking (true = success, false =
	 *         fail)
	 */
	public boolean changeBooking(RoomBooking oldRB, RoomBooking newRB) {

		for (RoomBooking rbook : getRoomBookings()) // check this won't collide with existing TimeBlock
			if (!rbook.equals(oldRB) && // ignore rb already in ArrayList
					(rbook.getTimeBlock().overlaps(newRB.getTimeBlock())))
				throw new BadRoomBookingException("Could not modify", "That time-block is already booked.");

		oldRB.setActivity(newRB.getActivity());
		oldRB.setContactInfo(newRB.getContactInfo());
		oldRB.setTimeBlock(newRB.getTimeBlock());

		Collections.sort(roomBookings, new SortRoomBookingsByCalendar());

		return (findBooking(newRB.getTimeBlock().getStartTime()) != null);
	}

	/**
	 * Displays all the RoomBookings for a selected day
	 * <p>
	 * Takes a calendar inputed by the user and uses just the date to find all
	 * bookings on that day<br>
	 * For each hour it searches for any bookings in that hour using binarySearch()
	 * method<br>
	 * If an entry is found it's toString() method is called to display its data to
	 * the user, and for loop skips to end hour of the found booking<br>
	 * Or it displays that no booking was found for that hour<br>
	 * Each entry is output in chronological order for that date.
	 *
	 * @param cal (reference type: calendar) containing the date to be displayed
	 */
	public String displayDayBookings(Calendar cal) {

		int index = -1;
		Calendar oneHourLater = (Calendar) cal.clone();
		RoomBooking rb = new RoomBooking();

		String output = "";

		for (int hr = 8; hr < 24; hr++) {

			cal.set(Calendar.HOUR_OF_DAY, hr);
			oneHourLater.set(Calendar.HOUR_OF_DAY, hr + 1);
			rb.setTimeBlock(new TimeBlock(cal, oneHourLater));

			index = Collections.binarySearch(roomBookings, rb, new SortRoomBookingsByCalendar());

			if (index < 0) {
				output += "No booking scheduled between " + hr + ":00 and " + (hr + 1) + ":00\n";
			} else {
				output += "---------------\n" + roomBookings.get(index).toString() + "\n---------------\n";
				hr = roomBookings.get(index).getTimeBlock().getEndTime().get(Calendar.HOUR_OF_DAY) - 1;
			}
		}

		return output;
	}

	/**
	 * Saves the roomBookings arrayList into a file.
	 * <p>
	 * ArrayList roomBookings is saved as an Object Stream into
	 * CurrentRoomBookings.book<br>
	 * 
	 * @return the result of save process (true = success, false = fail)
	 * 
	 */
	public boolean saveBookingsToFile() {
		try (FileOutputStream fos = new FileOutputStream("CurrentRoomBookings.book");
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			for (RoomBooking rb : getRoomBookings())
				oos.writeObject(rb);
			
			return true;
		} catch (IOException ex) {
			
			return false;
		}
	}

	/**
	 * Loads the contents of a file into a RoomBooking arrayList and returns it.
	 * <p>
	 * It expects data to be stored as an ObjectStrem CurrentRoomBookings.book<br>
	 * Returns null for any file IO type exceptions.
	 * 
	 * @return the RoomBooking ArrayList loaded from the file, or null
	 * 
	 */
	public ArrayList<RoomBooking> loadBookingsFromFile() {
		ArrayList<RoomBooking> ar = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream("CurrentRoomBookings.book");
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			while (true) {
				ar.add((RoomBooking) (ois.readObject()));
			}
		} catch (EOFException ex) {
			roomBookings = ar;
			return ar;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}

	}

	/**
	 * Getter for roomBookings ArrayList
	 * 
	 * @returns roomBookings
	 */
	public ArrayList<RoomBooking> getRoomBookings() {
		return roomBookings;
	}

	/**
	 * Checks strings to determine if they're blank.
	 * <p>
	 * Throws a BadRoomBookingException for empty strings, returns false
	 * otherwise.<br>
	 * Used to verify all user input (except Organization)
	 *
	 * @param string to be checked for being empty
	 * @return false
	 * @throws BadRoomBookingException if the content of the string is an empty
	 *                                 string
	 */
	public static boolean errorOnBlank(String string, JTextField jf) {
		if (string.trim().equals("")) {
			throw new BadRoomBookingException("Missing value", "Missing an input value.", jf);
		} else
			return false;
	}

	/**
	 * Checks objects to determine if they're null
	 * <p>
	 * If the generic object &lt;T&gt; type is null, it throws a
	 * BadRoomBookingException Otherwise, it returns false.<br>
	 * Used in every setter method and for verifying user text input.
	 * 
	 * @param <T> for generic object type method
	 * @param obj - generic type T, to be checked for null value
	 * @return false, if there is no null, throws an error otherwise
	 * @throws BadRoomBookingException if the passed obj is null
	 */
	public static <T> boolean errorOnNull(T obj, JTextField jf) {
		if (obj == null) {
			throw new BadRoomBookingException("Null value entered",
					"An attempt was made to pass a null value to a variable.", jf);
		} else
			return false;
	}

	public static void errorDialog(BadRoomBookingException ex) {
		JTextField jf = ex.getJF();
		if (jf != null)
			jf.setBackground(new Color(255, 255, 165));

		messageDialog(ex.getMessage(), ex.getHeader(), JOptionPane.ERROR_MESSAGE);
	}

	public static void messageDialog(String message, String title) {
		int type = JOptionPane.INFORMATION_MESSAGE;
		messageDialog(message, title, type);
	}

	public static void messageDialog(String message, String title, int type) {
		JOptionPane.showMessageDialog(new JFrame(), message, title, type);
	}

	public static int confirmDialog(String header, String message) {
		Object[] options = { "Yes", "No" };

		int n = JOptionPane.showOptionDialog(null, message, header, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
				options, // the titles of buttons
				null);

		return n;
	}

} // END RoomScheduler class
