/**
 * Class Name: RoomBooking
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

import java.io.Serializable;

public class RoomBooking implements Serializable {

	/**
	 * Variable for Serializable
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * private fields to hold relevant RoomBooking class' data
	 * <p>
	 * The contact information the person or group organizing the event<br>
	 * The specific event activity<br>
	 * The specific date and time range the event takes place<br>
	 */
	private ContactInfo contactInfo;
	private Activity activity;
	private TimeBlock timeBlock;

	/**
	 * Parameterized constructor sets initial values for all RoomBooking data.
	 * <p>
	 * Sets values for contactInfo, activity, and timeBlock by calling their setters
	 * 
	 * @param contactInfo (reference type: ContactInfo)
	 * @param activity    (reference type: Activity)
	 * @param timeBlock   (reference type: TimeBlock)
	 */
	public RoomBooking(ContactInfo contactInfo, Activity activity, TimeBlock timeBlock) {
		setContactInfo(contactInfo);
		setActivity(activity);
		setTimeBlock(timeBlock);
	}

	/**
	 * Default constructor
	 * <p>
	 * Leaves all class variables uninitialized.
	 */
	public RoomBooking() {
	}

	/**
	 * Sets contactInfo
	 * <p>
	 * Incoming data cannot be null
	 * 
	 * @param contactInfo (reference type: ContactInfo)
	 * 
	 */
	public void setContactInfo(ContactInfo contactInfo) {
		if (!RoomScheduler.errorOnNull(contactInfo, RoomBookingDialog.getField(7)))
			this.contactInfo = contactInfo;
	}

	/**
	 * Get the stored value of the contactInfo private field
	 * 
	 * @return contactInfo (reference type: ContactInfo)
	 */
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	/**
	 * Sets activity
	 * <p>
	 * Incoming data cannot be null
	 * 
	 * @param activity (reference type: Activity)
	 */
	public void setActivity(Activity activity) {
		if (!RoomScheduler.errorOnNull(activity, RoomBookingDialog.getField(13)))
			this.activity = activity;
	}

	/**
	 * Get the stored value of the activity private field
	 * 
	 * @return activity (reference type: Activity)
	 *  
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Sets timeBlock
	 * <p>
	 * Incoming data cannot be null
	 * 
	 * @param timeBlock (reference type: TimeBlock)
	 */
	public void setTimeBlock(TimeBlock timeBlock) {
		if (!RoomScheduler.errorOnNull(timeBlock, RoomBookingDialog.getField(3)))
			this.timeBlock = timeBlock;
	}

	/**
	 * Get the stored value of the timeBlock private field
	 * 
	 * @return timeBlock (reference type: TimeBlock)
	 */
	public TimeBlock getTimeBlock() {
		return timeBlock;
	}

	/**
	 * Compiles all room booking data into a human readable string
	 * <p>
	 * Builds the string out of the toString() methods of each of the TimeBlock,
	 * Activity, and ContactInfo classes
	 * 
	 * @return the compiled RoomBooking string data (reference type: String)
	 */
	@Override
	public String toString() {
		return (getTimeBlock().toString() + getActivity().toString() + getContactInfo().toString());
	}
	

} // END RoomBooking class
