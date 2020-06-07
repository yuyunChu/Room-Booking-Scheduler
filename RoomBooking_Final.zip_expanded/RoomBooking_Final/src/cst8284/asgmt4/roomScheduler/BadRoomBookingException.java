/**
 * Class Name: BadRoomBookingException
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

import javax.swing.JTextField;

public class BadRoomBookingException extends java.lang.RuntimeException {

	/**
	 * Variable for Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The text content of the error message for the user
	 * 
	 * header: a brief description of the error message: a detailed description of
	 * the error
	 */
	private String header, message;
	private JTextField jf;

	/**
	 * Default no-arg constructor
	 * <p>
	 * Assigns default values for header and message:<br>
	 * header = "Please try again"<br>
	 * message = "Bad room booking entered"
	 * <p>
	 * Chain to the 2-arg constructor - this(String, String) - with a default error
	 * message.<br>
	 */

	public BadRoomBookingException() {
		this("Please try again", "Bad room booking entered");
	}

	/**
	 * 2-param/the 2nd constructor
	 * <p>
	 * Sets the message of the exception by calling the superclass:
	 * super(String)<br>
	 * Sets the header of the exception message by calling its setter
	 * 
	 * @param header  (reference type: String)
	 * @param message (reference type: String)
	 */
	public BadRoomBookingException(String header, String message) {
		this(header, message, (JTextField) null);
	}

	public BadRoomBookingException(String header, String message, JTextField jf) {
		super(message);
		setHeader(header);
		setJF(jf);
	}

	public JTextField getJF() {
		return jf;
	}

	public void setJF(JTextField jf) {
		this.jf = jf;

	}

	/**
	 * Sets message
	 * <p>
	 * Incoming data cannot be null and cannot be blank
	 * 
	 * @param message (reference type: String)
	 */
	public void setMessage(String message) {
		if (!RoomScheduler.errorOnNull(message, jf) && !RoomScheduler.errorOnBlank(message,jf))
			this.message = message;
	}

	/**
	 * Get the stored value of the header private field
	 * 
	 * @return header (reference type: String)
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * Sets header
	 * <p>
	 * Incoming data cannot be null or blank
	 * 
	 * @param header (reference type: String)
	 */
	public void setHeader(String header) {
		if (!RoomScheduler.errorOnNull(header,jf) && !RoomScheduler.errorOnBlank(header,jf))
			this.header = header;
	}
}
