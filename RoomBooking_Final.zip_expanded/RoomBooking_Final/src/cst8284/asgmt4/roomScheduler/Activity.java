/**
 * Class Name: Activity
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

public class Activity implements java.io.Serializable {

	/**
	 * Variable for Serializable
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * private fields to hold relevant Activity class' data
	 */
	private String category, description;

    /**
     * Parameterized constructor
     * @param category (reference type: String)
     * @param description (reference type: String)
     */
	public Activity(String category, String description) {
		setCategory(category);
		setDescription(description);
	}

    /**
     * Getter
	 * @return description (reference type: String)
     */
	public String getDescription() {
		return description;
	}

	 /**
     * Setter
     * @param description (reference type: String)
     */
	public void setDescription(String description) {
		if(!RoomScheduler.errorOnNull(description, RoomBookingDialog.getField(15)) && !RoomScheduler.errorOnBlank(description, RoomBookingDialog.getField(15)))
			this.description = description;
	}
	
	 /**
     * Getter
     * @return category (reference type: String)
     */
	public String getCategory() {
		return category;
	}

	 /**
     * Setter
     * @param category (reference type: String)
     */
	public void setCategory(String category) {
		if(!RoomScheduler.errorOnNull(category, RoomBookingDialog.getField(13)) && !RoomScheduler.errorOnBlank(category, RoomBookingDialog.getField(13)))
			this.category = category;
	}

	 /**
     * String method
     * Returns a compiled string of all Activity info with formatting display
     * @return formatted Activity info data (reference type: String)
     */
	@Override
	public String toString() {
		return "Event: " + getCategory() + "\n" + ((getDescription() != "") ? "Description: " + getDescription() : "")
				+ "\n";
	}
} // END Activity class
