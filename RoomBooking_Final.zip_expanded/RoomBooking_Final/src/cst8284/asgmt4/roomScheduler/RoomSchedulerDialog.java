/**
 * Class Name: RoomSchedulerDialog
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import cst8284.asgmt4.roomScheduler.RoomBooking;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class RoomSchedulerDialog {

	private static Panel cp = new Panel();
	private static final Font defaultFont = new Font("SansSerif", Font.PLAIN, 20);
	private static final GridLayout btnCol = new GridLayout(10, 1);
	private static final JPanel btnPanel = new JPanel(btnCol);
	private static final JTextArea textArea = new JTextArea();
	private static final JFrame f = new JFrame("");

	private RoomScheduler roomScheduler;

	RoomSchedulerDialog(RoomScheduler roomScheduler) {
		this.roomScheduler = roomScheduler;
	}

	public void showSchedulerDialog() {

		// Load the buttons
		setBtnRow("Add Room Booking", 'a', new AddBookingBtn());
		setBtnRow("Display Existing Booking", 'e', new DisplayBookingBtn());
		setBtnRow("Backup Bookings to File", 'b', new BakupBookingBtn());
		setBtnRow("Load Bookings from File", 'l', new LoadBookingBtn());
		setBtnRow("Exit", 'x', new ExitBtn());

		btnPanel.setPreferredSize(new Dimension(250, 400));

		cp.add(btnPanel);
		f.add(cp, BorderLayout.WEST);
		f.add(setMainWindow(setRow()), BorderLayout.CENTER);
		f.pack();

		// Close dialog
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		});

		setJFrameTitle("");
		f.setVisible(true);
	}

	public RoomScheduler getRoomScheduler() {
		return roomScheduler;
	}

	public void setJTextArea(String string) {
		textArea.setText(string);
	}

	public void setJFrameTitle(String date) {
		f.setTitle("Room Bookings for " + roomScheduler.getRoom().getRoomNumber() + (!date.equals("") ? " for " : "")
				+ date);
	}

	private JTextArea setRow() {
		textArea.setFont(defaultFont);
		return textArea;
	}

	private JButton setBtnRow(String label, char keyboardShortcut, ActionListener l) {
		JButton btn = new JButton(label);
		btn.setFont(defaultFont);
		btn.setMnemonic(keyboardShortcut);
		btn.addActionListener(l);
		btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		btn.setPreferredSize(new Dimension(250, 30));
		btnPanel.add(btn);
		return btn;
	}

	public JScrollPane setMainWindow(JTextArea textArea) {
		JScrollPane JP = new JScrollPane(textArea);
		JP.setPreferredSize(new Dimension(500, 800));
		return JP;
	}

	private class AddBookingBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			RoomBookingDialog.closeWindow();
			RoomBookingDialog.showAppointmentDialog(RoomSchedulerDialog.this, getClass().getSimpleName());
		}

	}

	private class DisplayBookingBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			RoomBookingDialog.closeWindow();
			RoomBookingDialog.showAppointmentDialog(RoomSchedulerDialog.this, getClass().getSimpleName());

		}

	}

	private class BakupBookingBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (roomScheduler != null
					&& RoomScheduler.confirmDialog("Save", "Are you sure you want to save this booking?") == 0) {
				roomScheduler.saveBookingsToFile();
				RoomScheduler.messageDialog("File is ready!", "Success");
			}
		}

	}

	private class LoadBookingBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (roomScheduler != null && new File("CurrentRoomBookings.book").exists()
					&& RoomScheduler.confirmDialog("Load", "Are you sure you want to load this booking?") == 0) {

				roomScheduler.loadBookingsFromFile();
				RoomScheduler.messageDialog("File is ready!", "Success");
			}
		}

	}

	private static class ExitBtn implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
		}

	}

}
