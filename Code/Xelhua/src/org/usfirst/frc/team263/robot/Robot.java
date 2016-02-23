
package org.usfirst.frc.team263.robot;

import org.usfirst.frc.team263.robot.LedStrip.Colors;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends SampleRobot {
	CameraServer cameraS;
	Joystick drivePad;
	Joystick gamePad;
	DriveControls drive;
	MechanicalControls mech;
	ServoCam camera;
	Recorder recorder;
	Autonomous autonomous;
	DriverStation ds;

	public Robot() {
		drivePad = new Joystick(0);
		gamePad = new Joystick(1);
		drive = new DriveControls();
		mech = new MechanicalControls(gamePad);
		recorder = new Recorder();
		autonomous = new Autonomous();
		camera = new ServoCam();
		ds = DriverStation.getInstance();
	}

	public void robotInit() {
		if (ds.getAlliance().equals(Alliance.Blue)) {
			LedStrip.setColor(Colors.eBlue);
		} else {
			LedStrip.setColor(Colors.eRed);
		}
		cameraS = CameraServer.getInstance();
		cameraS.startAutomaticCapture("cam1");
		cameraS.setQuality(50);
	}
	
	public void disabled() {
		LedStrip.setColor(Colors.eOrange);
	}

	public void autonomous() {
		if (isAutonomous() && isEnabled()) {
			LedStrip.setColor(Colors.eGreen);
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
		while (isTest() && isEnabled()) {
			drive.drive(drivePad);
			recorder.addDrive(drive.getMotors());
			mech.drive();
			recorder.addMech(mech.getMotor());
			Timer.delay(0.1);
		}
		recorder.retrieveArrays();
	}
}
