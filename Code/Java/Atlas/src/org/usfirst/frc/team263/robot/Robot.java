package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.*;
/*
 * 
 * 
 * IF ROBOT DOES NOT WORK, CHANGE VARIABLES BACK TO STATIC
 * CHANGE IF(BOOL = TRUE) TO IF(BOOL)
 * DO MECANUM DRIVE AND AUTON
 * 
 * 
 */



public class Robot extends SampleRobot {
	Joystick drivePad;
	Joystick gamePad;
	
	MecanumDrive drive;
	LiftSystem lifter;
	PivotPiston pivotPiston;
	AutonomousSystem auton;
	
	DigitalInput auto2tote1Can;
	DigitalInput auto1Tote1Can;
	DigitalInput auto3ToteStack;
	DigitalInput autoDriveFwd;
	DigitalInput autoFastCan;
	Timer timer;
	
	public Robot() {

		drivePad = new Joystick(0);
		gamePad = new Joystick(1);
		
		drive = new MecanumDrive();
		lifter = new LiftSystem();
		pivotPiston = new PivotPiston();
		auton = new AutonomousSystem();
		
		autoFastCan = new DigitalInput(5);
		autoDriveFwd = new DigitalInput(6);
		auto3ToteStack = new DigitalInput(7);
		auto1Tote1Can = new DigitalInput(8);
		auto2tote1Can = new DigitalInput(9);
		timer = new Timer();
		
		
	}
	public void autonomous() {
		
	}
	public void operatorControl() {

		while (isOperatorControl() && isEnabled()) {
			drive.CalibrateNavX();
			drive.Drive(drivePad);
			lifter.RunLifter(gamePad, drivePad);
			//pivotPiston.CommandPivotPistonPosition(drivePad);
		}
	}

	public void test() {
	}
}
