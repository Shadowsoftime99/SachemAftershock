package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

public class MechanicalControls {
	CANTalon ballSpinner, ballGrabberPivot;
	PDBallMech ballGrabber;
	private final int A_BUTTON = 1;
	private final int B_BUTTON = 2;
	private final int X_BUTTON = 3;
	private final int Y_BUTTON = 4;
	private Encoder ballGrabberEnc = new Encoder(0, 1);
	private DigitalInput ballGrabberLS = new DigitalInput(2);
	
	public MechanicalControls() {
		ballSpinner = new CANTalon(1);
		ballGrabberPivot = new CANTalon(0);
		ballGrabber = new PDBallMech(0.1, 0.1, ballGrabberPivot, ballGrabberEnc, ballGrabberLS);
	}
	
	public void drive(Joystick gamepad) {
	//	ballGrabberPivot.enableBrakeMode(false);
		ballGrabberPivot.set(gamepad.getRawAxis(1));
		ballSpinner.set(gamepad.getRawAxis(5) + .0625);
	}
}
