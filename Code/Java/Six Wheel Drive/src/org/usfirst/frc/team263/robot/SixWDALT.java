package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class SixWDALT {
	private Talon leftFront, leftBack, rightFront, rightBack;
	private int RIGHT_ANALOG = 4;
	private int LEFT_ANALOG = 1;
	private int LEFT_TRIGGER = 2;
	private int RIGHT_TRIGGER = 3;
	private boolean linear = false;
	private double leftSpeed = 0, rightSpeed = 0;

	public SixWDALT() {
		leftFront = new Talon(1);
		leftBack = new Talon(3);
		rightFront = new Talon(2);
		rightBack = new Talon(0);

		leftFront.set(0);
		rightFront.set(0);
	}

	public void Drive(Joystick drivepad) {
		if (Math.abs(drivepad.getRawAxis(LEFT_ANALOG)) > 0.1) {
			double value = drivepad.getRawAxis(LEFT_ANALOG);
			leftSpeed = -value;
			rightSpeed = value;
			linear = true;
		} else {
			linear = false;
		}

		if (linear && Math.abs(drivepad.getRawAxis(RIGHT_ANALOG)) > 0.1) {
			if (drivepad.getRawAxis(RIGHT_ANALOG) > 0) {
				leftSpeed -= Math.min(0.9, Math.abs(drivepad.getRawAxis(RIGHT_ANALOG)));
			} else if (drivepad.getRawAxis(RIGHT_ANALOG) < 0) {
				rightSpeed -= Math.min(0.9, Math.abs(drivepad.getRawAxis(RIGHT_ANALOG)));
			}
		} else if (Math.abs(drivepad.getRawAxis(RIGHT_ANALOG)) > 0.1) {
			if (drivepad.getRawAxis(RIGHT_ANALOG) > 0) {
				leftSpeed = Math.abs(drivepad.getRawAxis(RIGHT_ANALOG));
			} else {
				rightSpeed = Math.abs(drivepad.getRawAxis(RIGHT_ANALOG));
			}
		}

		if (!linear && Math.abs(drivepad.getRawAxis(RIGHT_ANALOG)) <= 0.1) {
			if (drivepad.getRawAxis(LEFT_TRIGGER) > 0.1) {
				leftSpeed = -drivepad.getRawAxis(LEFT_TRIGGER);
				rightSpeed = -drivepad.getRawAxis(LEFT_TRIGGER);
			} else if (drivepad.getRawAxis(RIGHT_TRIGGER) > 0.1) {
				leftSpeed = drivepad.getRawAxis(RIGHT_TRIGGER);
				rightSpeed = drivepad.getRawAxis(RIGHT_TRIGGER);
			} else {
				leftSpeed = 0;
				rightSpeed = 0;
			}
		}
		leftFront.set(leftSpeed);
		leftBack.set(leftSpeed);
		rightFront.set(rightSpeed);
		rightBack.set(rightSpeed);
	}
}
