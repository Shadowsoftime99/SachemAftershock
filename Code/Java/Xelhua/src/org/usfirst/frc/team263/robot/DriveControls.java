package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class DriveControls {
	/*
	 * USE XBOX CONTROLLER ONLY OTHERS WILL NOT WORK CORRECTLY
	 */
	private Talon leftBack, rightBack, rightFront;
	private CANTalon leftFront;
	private CANTalon ballSpinner, ballGrabberPivot;
	private int LEFT_ANALOG_TANK = 1;
	private int RIGHT_ANALOG_TANK = 5;
	private int RIGHT_ANALOG = 4;
	private int LEFT_ANALOG = 1;
	private int LEFT_TRIGGER = 2;
	private int RIGHT_TRIGGER = 3;
	private int LEFT_BUMPER = 5;
	private int RIGHT_BUMPER = 6;
	private boolean linear; // True when going forward -- used for turning
	private double leftSpeed = 0, rightSpeed = 0;
	private PDBallMech ballGrabber;

	public DriveControls() {
		// Assign Talons correct ports
		leftFront = new CANTalon(2);
		leftFront.enableBrakeMode(false);
		leftBack = new Talon(1);
		rightFront = new Talon(3);
		rightBack = new Talon(0);

		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
	}

	public void drive(Joystick drivepad) {
		if (!drivepad.getRawButton(LEFT_BUMPER) && !drivepad.getRawButton(RIGHT_BUMPER)) {
			leftSpeed = drivepad.getRawAxis(LEFT_ANALOG_TANK);
			rightSpeed = drivepad.getRawAxis(RIGHT_ANALOG_TANK);

			if (Math.abs(leftSpeed) < 0.1)
				leftSpeed = 0;
			if (Math.abs(rightSpeed) < 0.1)
				rightSpeed = 0;

			if (leftSpeed == 0 && rightSpeed == 0) {
				if (drivepad.getRawAxis(LEFT_TRIGGER) > 0.1) { // Turn in place
																// left
					leftSpeed = drivepad.getRawAxis(LEFT_TRIGGER);
					rightSpeed = -drivepad.getRawAxis(LEFT_TRIGGER);
				} else if (drivepad.getRawAxis(RIGHT_TRIGGER) > 0.1) { // Turn
																		// in
																		// place
																		// right
					leftSpeed = -drivepad.getRawAxis(RIGHT_TRIGGER);
					rightSpeed = drivepad.getRawAxis(RIGHT_TRIGGER);
				}
			}
			System.out.println(leftSpeed + " + " + rightSpeed);
			leftFront.set(-leftSpeed);
			leftBack.set(leftSpeed);
			rightFront.set(-rightSpeed);
			rightBack.set(rightSpeed);
		} else {
			linear = Math.abs(drivepad.getRawAxis(LEFT_ANALOG)) > 0.1;
			if (linear) {
				double value = drivepad.getRawAxis(LEFT_ANALOG);
				leftSpeed = value;
				rightSpeed = value;
			}

			if (linear && Math.abs(drivepad.getRawAxis(RIGHT_ANALOG)) > 0.1) {
				if (drivepad.getRawAxis(RIGHT_ANALOG) > 0) {
					rightSpeed /= (10 * drivepad.getRawAxis(RIGHT_ANALOG)); 
				} else if (drivepad.getRawAxis(RIGHT_ANALOG) < 0) {
					leftSpeed /= (10 * Math.abs(drivepad.getRawAxis(RIGHT_ANALOG))); 
				}
			} else if (Math.abs(drivepad.getRawAxis(RIGHT_ANALOG)) > 0.1) {
				if (drivepad.getRawAxis(RIGHT_ANALOG) > 0) {
					leftSpeed = -drivepad.getRawAxis(RIGHT_ANALOG);
				} else {
					rightSpeed = drivepad.getRawAxis(RIGHT_ANALOG);
				}
			}

			if (!linear && Math.abs(drivepad.getRawAxis(RIGHT_ANALOG)) <= 0.1) {
				if (drivepad.getRawAxis(LEFT_TRIGGER) > 0.1) {
					leftSpeed = drivepad.getRawAxis(LEFT_TRIGGER);
					rightSpeed = -drivepad.getRawAxis(LEFT_TRIGGER);
				} else if (drivepad.getRawAxis(RIGHT_TRIGGER) > 0.1) {
					leftSpeed = -drivepad.getRawAxis(RIGHT_TRIGGER);
					rightSpeed = drivepad.getRawAxis(RIGHT_TRIGGER);
				} else { // Set motors to zero
					leftSpeed = 0;
					rightSpeed = 0;
				}
			}
			leftFront.set(leftSpeed);
			leftBack.set(-leftSpeed);
			rightFront.set(rightSpeed);
			rightBack.set(rightSpeed);
		}
	}
}