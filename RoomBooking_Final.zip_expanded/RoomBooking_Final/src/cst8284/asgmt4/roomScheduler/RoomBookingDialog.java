/**
 * Class Name: RoomBookingDialog
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/* Adapted, with considerable modification, from 
 * http://www.java2s.com/Code/Java/Swing-JFC/TextAcceleratorExample.htm,
 * which is sloppy code and should not be emulated.
 */

public class RoomBookingDialog {

	private static final GridBagConstraints labelConstraints = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1,
			1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 20), 0, 0);
	private static final GridBagConstraints textConstraints = new GridBagConstraints(1, GridBagConstraints.RELATIVE, 1,
			1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 10), 20, 20); // anchor,
																												// fill,
																												// insets,
																												// ipadx,
																												// ipady

	private static final GridBagConstraints btnConstraints = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 2,
			1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 20), 0, 0);

	private static Panel cp = new Panel();
	private static final int labelWidth = 24;
	private static final Font defaultFont = new Font("SansSerif", Font.PLAIN, 20);
	private static final FlowLayout btnRow = new FlowLayout();
	private static final JPanel btnPanel = new JPanel();

	private static RoomBooking modifyRB = null;
	private static JFrame f = new JFrame("");

	public static void showAppointmentDialog(RoomSchedulerDialog RSD, String action) {

		cp.setLayout(new GridBagLayout());

		// Set the first seven rows with Label/TextField
		JTextField bookDate = setRow("Booking Date (YYYYMMDD):", 'e');
		JTextField startBookTime = setRow("Booking Time (2 p.m. or 14:00):", 't');
		JTextField endBookTime = setRow("End Time (4 p.m. or 16:00):", 'y');
		JTextField clientName = setRow("Client Name (FirstName LastName):", 'n');
		JTextField phoneNumber = setRow("Phone Number (e.g. 613-555-1212):", 'p');
		JTextField org = setRow("Organization (optional):", 'o');
		JTextField eventCat = setRow("Event Category:", 'c');
		JTextField desc = setRow("Description:", 'd');

		// Load the buttons across the bottom
		btnPanel.setLayout(btnRow);
		JButton search = setBtnRow("   Search   ", 'r');
		JButton save = setBtnRow("    Save    ", 's');
		JButton delete = setBtnRow("   Delete   ", 'l'/* , new DeleteBtn() */);
		JButton exit = setBtnRow("    Exit    ", 'x');

		if (action.equals("DisplayBookingBtn")) {
			save.setEnabled(false);
			delete.setEnabled(false);
		} else if (action.equals("AddBookingBtn")) {
			search.setEnabled(false);
			delete.setEnabled(false);
		}

		search.addActionListener(event -> {

			modifyRB = null;
			clearBackgrounds();

			save.setEnabled(false);
			delete.setEnabled(false);

			if (action.equals("DisplayBookingBtn")) {

				try {

					Calendar cal = RoomScheduler.makeCalendarFromUserInput(bookDate.getText(), startBookTime.getText());

					if (startBookTime.getText().equals("")) {

						RSD.setJTextArea(RSD.getRoomScheduler().displayDayBookings(cal));

						SimpleDateFormat sdf = new SimpleDateFormat("MMM. d, yyyy");

						RSD.setJFrameTitle(sdf.format(cal.getTime()));

						closeWindow();

					} else {

						RoomBooking rb = RSD.getRoomScheduler().findBooking(cal);
						if (rb == null) {
							throw new BadRoomBookingException("No booking found",
									"Please try a different date and time.");
						} else {

							startBookTime.setText(rb.getTimeBlock().getStartTime().get(Calendar.HOUR_OF_DAY) + ":00");
							endBookTime.setText(rb.getTimeBlock().getEndTime().get(Calendar.HOUR_OF_DAY) + ":00");

							clientName.setText(
									rb.getContactInfo().getFirstName() + " " + rb.getContactInfo().getLastName());
							phoneNumber.setText(rb.getContactInfo().getPhoneNumber());
							org.setText(rb.getContactInfo().getOrganization());
							eventCat.setText(rb.getActivity().getCategory());
							desc.setText(rb.getActivity().getDescription());

							modifyRB = rb;

							save.setEnabled(true);
							delete.setEnabled(true);

						}

					}

				} catch (BadRoomBookingException ex) {
					RoomScheduler.errorDialog(ex);
				}

			}

		});

		save.addActionListener(event -> {

			clearBackgrounds();

			try {

				Calendar startTime = RoomScheduler.makeCalendarFromUserInput(bookDate.getText(), startBookTime);
				Calendar endTime = RoomScheduler.makeCalendarFromUserInput(bookDate.getText(), endBookTime);

				RoomBooking newRB = RoomScheduler.makeBookingFromUserInput(clientName.getText(), phoneNumber.getText(),
						org.getText(), eventCat.getText(), desc.getText(), startTime, endTime);

				if (action.equals("AddBookingBtn")) {
					RSD.getRoomScheduler().saveRoomBooking(newRB);
					closeWindow();
				} else if (modifyRB != null && RoomScheduler.confirmDialog("Modify booking",
						"Are you sure you want to modify this booking?") == 0) {
					RSD.getRoomScheduler().changeBooking(modifyRB, newRB);
				}

			} catch (BadRoomBookingException ex) {
				RoomScheduler.errorDialog(ex);
			}

		});

		delete.addActionListener(event -> {

			if (modifyRB != null && RoomScheduler.confirmDialog("Delete booking",
					"Are you sure you want to delete this booking?") == 0) {

				save.setEnabled(false);
				delete.setEnabled(false);

				RSD.getRoomScheduler().deleteBooking(modifyRB);

				modifyRB = null;

				bookDate.setText("");
				startBookTime.setText("");
				endBookTime.setText("");
				clientName.setText("");
				phoneNumber.setText("");
				org.setText("");
				eventCat.setText("");
				desc.setText("");

			}

		});

		exit.addActionListener(event -> {
			f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
		});

		cp.add(btnPanel, btnConstraints);
		f.add(cp);
		f.pack();

		// Close dialog
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				btnPanel.removeAll();
				cp.removeAll();
				f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		});

		f.setTitle(RSD.getRoomScheduler().getRoom().toString());
		f.setVisible(true);
	}

	private static JTextField setRow(String label, char keyboardShortcut) {
		JLabel l;
		JTextField t;
		cp.add(l = new JLabel(label, SwingConstants.RIGHT), labelConstraints);
		l.setFont(defaultFont);
		l.setDisplayedMnemonic(keyboardShortcut);
		cp.add(t = new JTextField(labelWidth), textConstraints);
		t.setFont(defaultFont);
		t.setFocusAccelerator(keyboardShortcut);
		return t;
	}

	private static JButton setBtnRow(String label, char keyboardShortcut) {
		JButton btn = new JButton(label);
		btn.setFont(defaultFont);
		btn.setMnemonic(keyboardShortcut);
		btnPanel.add(btn);
		return btn;
	}

	public static JTextField getField(int n) {
		return (JTextField) cp.getComponent(n);
	}

	public static void closeWindow() {
		f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
	}

	private static void clearBackgrounds() {
		cp.getComponent(1).setBackground(new Color(255, 255, 255));
		cp.getComponent(3).setBackground(new Color(255, 255, 255));
		cp.getComponent(5).setBackground(new Color(255, 255, 255));
		cp.getComponent(7).setBackground(new Color(255, 255, 255));
		cp.getComponent(9).setBackground(new Color(255, 255, 255));
		cp.getComponent(11).setBackground(new Color(255, 255, 255));
		cp.getComponent(13).setBackground(new Color(255, 255, 255));
		cp.getComponent(15).setBackground(new Color(255, 255, 255));
	}

}
