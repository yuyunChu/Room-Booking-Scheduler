/**
 * Class Name: RoomSchedulerLauncher
 * @author Chu, Yu-Yun, based on code supplied by Prof. Dave Houtman
 * @version 1.2
 * CLR: Assignment 4_1.01
 * Course Name: CST8284_310
 * Date: April 9th, 2020
 */
package cst8284.asgmt4.roomScheduler;

import cst8284.asgmt4.room.*;

public class RoomSchedulerLauncher {
	/*
	 * Starting point of execution for the program. Instantiating a new instance of
	 * ComputerLab then instantiates RoomScheduler, using ComputerLab as the room,
	 * and launches it.
	 */

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new RoomScheduler(new ComputerLab("B119")).launch();
			}
		});
	}

} // END RoomSchedulerLauncher class
