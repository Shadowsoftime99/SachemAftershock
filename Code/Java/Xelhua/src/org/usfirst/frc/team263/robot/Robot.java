
package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends SampleRobot {
	Joystick DrivePad;
	Joystick GamePad;
	DriveControls Drive;
	MechanicalControls Mech;
	Servo ServoElevation;
	Servo ServoAzimuth;
	I2C theI2Cbus;

	private static final int i2cAddress_Arduino = 10;

	public Robot() {
		DrivePad = new Joystick(0);
		GamePad = new Joystick(1);
		Drive = new DriveControls();
		Mech = new MechanicalControls();
		ServoElevation = new Servo(4);
		ServoAzimuth = new Servo(5);

		theI2Cbus = new I2C(I2C.Port.kOnboard, i2cAddress_Arduino);
		System.out.println("Arduino found on i2c address " + i2cAddress_Arduino + " : " + theI2Cbus.addressOnly());
	}

	public void robotInit() {
		sendColor('r');

	}

	public void autonomous() {
		sendColor('g');
	}

	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			Drive.drive(DrivePad); // Drive Wheels and run ball grabber
			//Mech.drive(GamePad);
		}
	}

	public void test() {
		char max = 255;
		char mid = 127;
		char min = 0;
		
	}

	public void sendColor(char theColor) {
		byte byteArray[] = { (byte) theColor };
		if (theI2Cbus.writeBulk(byteArray)) {
			System.out.println("Sent Command: " + theColor + " : Did not work!\n");
		} else {
			System.out.println("Sent Command: " + theColor);
		}
		Timer.delay(0.15);
	}
}
