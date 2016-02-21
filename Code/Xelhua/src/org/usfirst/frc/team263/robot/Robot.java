
package org.usfirst.frc.team263.robot;

import org.usfirst.frc.team263.robot.LedStrip.Colors;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends SampleRobot {
	Joystick drivePad;
	Joystick gamePad;
	DriveControls drive;
	MechanicalControls mech;
	ServoCam camera;
	Recorder recorder;
	Autonomous autonomous;


	public Robot() {
		drivePad = new Joystick(0);
		gamePad = new Joystick(1);
		drive = new DriveControls();
		mech = new MechanicalControls(gamePad);
		recorder = new Recorder();
		autonomous = new Autonomous();
		camera = new ServoCam();
	}

	public void robotInit() {
	}

	public void autonomous() {
		if (isAutonomous() && isEnabled()) {
			autonomous.detectPhase();
			autonomous.autoDrive();
		}
	}

	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			drive.drive(drivePad); 
			mech.drive();
			camera.RunServos(drivePad, gamePad);
		}
	}

	public void test() {
		/*
		while (isTest() && isEnabled()) {
			recorder.addDrive(drive.drive(drivePad, true));
			recorder.addMech(mech.drive(true));
			Timer.delay(0.1);
		}
		recorder.retrieveArrays();
		*/
		while (isTest() && isEnabled()) {
			LedStrip.sendColor(0, 255, 255);
			Timer.delay(1);
			LedStrip.sendColor(255, 0, 0);
			Timer.delay(1);
			LedStrip.sendColor(100, 100, 100);
		}
	}
}
