package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class SixWDALT {
	private Talon leftFront, leftBack, rightFront, rightBack;
	private int LEFT_ANALOG = 4; // To the right 0 - 1, to the left -1 - 0
	private int RIGHT_ANALOG = 1; // Bottom to middle 0 - 1 middle to top 0 - -1
	private int LEFT_TRIGGER = 2;
	private int RIGHT_TRIGGER = 3;
	private boolean linear = false;
	private boolean lastTurn = false;
	
	public SixWDALT()
	{
		leftFront = new Talon(1);
		leftBack = new Talon(3);
		rightFront = new Talon(2);
		rightBack = new Talon(0);
		
		leftFront.set(0);
		rightFront.set(0);
	}
	
	public void Drive(Joystick drivepad) {
		/*
		 * I wrote this all kickoff weekend.
		 * At 12:00 AM.
		 * So please don't judge this code.
		 * I'm sorry to whoever has to read this.
		 * 
		 * - Waxman
		 */
		
		// If right analog is up/down far enough for us to use
		if (Math.abs(drivepad.getRawAxis(RIGHT_ANALOG)) > 0.1) {
			// Set all motors to however far the analog stick is up/down.
			// Must set it to negative since controller is a bit messed up
			double value = -drivepad.getRawAxis(RIGHT_ANALOG);
			leftFront.set(value);
			leftBack.set(value);
			rightFront.set(-value);
			rightBack.set(-value);
			// Set linear to true. This is just used to determine if the bot is already moving forward/backwards for turning
			linear = true;
		} else {
			// Set linear to false. This is just used to determine if the bot is already moving forward/backwards for turning
			linear = false;
		}
		// Check to see if the left analog stick is pressed enough for us to count it and if it's moving forwards/backwards
		if (linear && Math.abs(drivepad.getRawAxis(LEFT_ANALOG)) > 0.1) {
			// To the right? If so slow down left motors to turn right.
			if (drivepad.getRawAxis(LEFT_ANALOG) > 0) {
				double value = leftFront.get() - Math.abs(drivepad.getRawAxis(LEFT_ANALOG));
				leftFront.set(value);
				leftBack.set(value);
				System.out.println("Turning right");
			} else { // Slow down right motors to turn left
				double value = rightFront.get() - Math.abs(drivepad.getRawAxis(LEFT_ANALOG));
				rightFront.set(value);
				rightBack.set(value);
				System.out.println("Turning left");
			}
		} else if (Math.abs(drivepad.getRawAxis(LEFT_ANALOG)) > 0.1) { // Just turn left or right if not already moving
			if (drivepad.getRawAxis(LEFT_ANALOG) > 0) {
				double value = drivepad.getRawAxis(LEFT_ANALOG);
				leftFront.set(value);
				leftBack.set(value);
			} else {
				double value = drivepad.getRawAxis(LEFT_ANALOG);
				rightFront.set(value);
				rightBack.set(value);
			}
		}
		// If the robot isn't moving forwards/backwards or left/right, set all motors to zero.
		if (!linear && Math.abs(drivepad.getRawAxis(LEFT_ANALOG)) <= 0.1) {
			
			// Now check if triggers are pressed. If they are, then we turn in place. 
			if (drivepad.getRawAxis(LEFT_TRIGGER) > 0.1) {
				double value = -drivepad.getRawAxis(LEFT_TRIGGER);
				leftFront.set(value);
				leftBack.set(value);
				rightFront.set(value);
				rightBack.set(value);
			} else if (drivepad.getRawAxis(RIGHT_TRIGGER) > 0.1) {
				double value = drivepad.getRawAxis(RIGHT_TRIGGER);
				leftFront.set(value);
				leftBack.set(value);
				rightFront.set(value);
				rightBack.set(value);
			} else {
				leftFront.set(0);
				leftBack.set(0);
				rightFront.set(0);
				rightBack.set(0);
			}
		}
		
	}
	
}
