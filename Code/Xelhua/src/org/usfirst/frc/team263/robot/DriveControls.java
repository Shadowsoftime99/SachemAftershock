package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * Class to control drive mechanisms
 * @author Dan Waxman
 * @author Rohan Bapat
 * @since 2016-01-15
 * @version 1.8
 */
public class DriveControls {
	/*
	 * USE XBOX CONTROLLER ONLY OTHERS WILL NOT WORK CORRECTLY
	 */
	private static Talon leftBack, leftFront, rightFront;
	private static CANTalon rightBack;
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
	private static Joystick dp;

	public DriveControls() {
		// Assign Talons correct ports
		leftFront = new Talon(0);
		leftBack = new Talon(1);
		rightFront = new Talon(2);
		rightBack = new CANTalon(5);
		rightBack.enableBrakeMode(true);

		leftFront.set(.8 * 0);
		leftBack.set(.8 * 0);
		rightFront.set(.8 * 0);
		rightBack.set(.8 * 0);
	}
	
	/**
	 * Method to drive robot
	 * @param drivepad Joystick to control driving with
	 */
	public void drive(Joystick drivepad) {
		if (drivepad.getRawButton(LEFT_BUMPER) && drivepad.getRawButton(RIGHT_BUMPER)) {
			tankDrive(drivepad);
		} else {
			arcadeDrive(drivepad);
		}
	}
	
	public double[] getMotors() {
		return new double[] {leftFront.get(), leftBack.get(), rightFront.get(), rightBack.get()};
	}
	
	private void tankDrive(Joystick drivepad) {
		dp = drivepad;
		leftSpeed = drivepad.getRawAxis(LEFT_ANALOG_TANK);
		rightSpeed = drivepad.getRawAxis(RIGHT_ANALOG_TANK);

		if (Math.abs(leftSpeed) < 0.1)
			leftSpeed = 0;
		if (Math.abs(rightSpeed) < 0.1)
			rightSpeed = 0;

		if (leftSpeed == 0 && rightSpeed == 0) {
			if (drivepad.getRawAxis(LEFT_TRIGGER) > 0.1) { 
				leftSpeed = drivepad.getRawAxis(LEFT_TRIGGER);
				rightSpeed = -drivepad.getRawAxis(LEFT_TRIGGER);
				leftFront.set(.8 * -leftSpeed);
				rightFront.set(.8 * -rightSpeed);
				leftBack.set(.8 * 0);
				rightBack.set(.8 * 0);
				return;
			} else if (drivepad.getRawAxis(RIGHT_TRIGGER) > 0.1) { 
				leftSpeed = -drivepad.getRawAxis(RIGHT_TRIGGER);
				rightSpeed = drivepad.getRawAxis(RIGHT_TRIGGER);
				leftFront.set(.8 * -leftSpeed);
				rightFront.set(.8 * -rightSpeed);
				leftBack.set(.8 * 0);
				rightBack.set(.8 * 0);
				return; 
			}
		}
		leftFront.set(.8 * -leftSpeed);
		leftBack.set(.8 * leftSpeed);
		rightFront.set(.8 * -rightSpeed);
		rightBack.set(.8 * rightSpeed);
	}	
	
	private void arcadeDrive(Joystick drivepad) {
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
			if (drivepad.getRawButton(LEFT_BUMPER)) {
				leftSpeed = -.5;
			} else if (drivepad.getRawButton(RIGHT_BUMPER)) {
				rightSpeed = -.5;
			} else if (drivepad.getRawAxis(LEFT_TRIGGER) > 0.1) {
				leftSpeed = drivepad.getRawAxis(LEFT_TRIGGER);
				rightSpeed = -drivepad.getRawAxis(LEFT_TRIGGER);
				leftFront.set(.8 * -leftSpeed);
				rightFront.set(.8 * -rightSpeed);
				leftBack.set(.8 * 0);
				rightBack.set(.8 * 0);
				return;
			} else if (drivepad.getRawAxis(RIGHT_TRIGGER) > 0.1) {
				leftSpeed = -drivepad.getRawAxis(RIGHT_TRIGGER);
				rightSpeed = drivepad.getRawAxis(RIGHT_TRIGGER);
				leftFront.set(.8 * -leftSpeed);
				rightFront.set(.8 * -rightSpeed);
				leftBack.set(.8 * 0);
				rightBack.set(.8 * 0);
				return;
			} else { // Set motors to zero
				leftSpeed = 0;
				rightSpeed = 0;
			}
		}
		leftFront.set(.8 * -leftSpeed);
		leftBack.set(.8 * leftSpeed);
		rightFront.set(.8 * -rightSpeed);
		rightBack.set(.8 * rightSpeed);
	}
	
	public static Talon getLeftFront() {
		return leftFront;
	}
	
	public static Talon getLeftBack() {
		return leftBack;
	}
	
	public static Talon getRightFront() {
		return rightFront;
	}
	
	public static CANTalon getRightBack() {
		return rightBack;
	}
	
	public static Joystick getStick() {
		return dp;
	}
}