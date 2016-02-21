package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Class to control mechanisms other than drivebase
 * @author Dan Waxman
 * @author Rohan Bapat
 * @since 2016-02-01
 * @version 1.3
 */
public class MechanicalControls {
	private boolean latchB = false;
	CANTalon ballSpinner, ballGrabberPivot, winch, latch;
	DigitalInput button;
	private final int A_BUTTON = 1;
	private final int B_BUTTON = 2;
	private final int X_BUTTON = 3;
	private final int Y_BUTTON = 4;
	private DigitalInput ballGrabberLS = new DigitalInput(3);
	private boolean ballInAlerted = false;
	private boolean ballOutAlerted = true;
	private Joystick gamepad;
	private JoystickRumble JRumble;
	private AnalogInput photoCircuit = new AnalogInput(3);
	private boolean locked = false;
	private boolean lsNotNotified = true;
	
	public MechanicalControls(Joystick stick) {
		gamepad = stick;
		JRumble = new JoystickRumble(gamepad, 2);
		button = new DigitalInput(4);
		ballSpinner = new CANTalon(2);
		ballGrabberPivot = new CANTalon(1);
		ballGrabberPivot.enableBrakeMode(true);
		winch = new CANTalon(4);
		latch = new CANTalon(3);
	}
	
	public void drive() {
		if (gamepad.getRawButton(A_BUTTON)) {
			ballSpinner.set(-0.4);
		} else if (gamepad.getRawButton(B_BUTTON)/* && ballGrabberLS.get()*/) {
			ballSpinner.set(gamepad.getRawButton(7) ? 1.0 : 0.4);
		} else {
			ballSpinner.set(0.0);
		}
		
		if (ballGrabberLS.get() && lsNotNotified) {
			lsNotNotified = false;
			JRumble = new JoystickRumble(gamepad, 1);
		} else if (ballGrabberLS.get()) {
			lsNotNotified = true;
		}
		//System.out.println(ballGrabberLS.get());		
		if (gamepad.getRawButton(X_BUTTON)) {
			ballGrabberPivot.set(locked ? 0.0 : -0.4);
		} else if (gamepad.getRawButton(Y_BUTTON)) {
			ballGrabberPivot.set(1.0);
		} else {
			ballGrabberPivot.set(0.0);
		}
		//System.out.println(button.get());
		if((/*photoCircuit.getVoltage() < 1.6 ||*/ !button.get()) && !ballInAlerted) {
			JRumble = new JoystickRumble(gamepad, 2);
			JRumble.start();
			ballInAlerted = true;
			ballOutAlerted = false;
			locked = true;
			System.out.println("Ball in: " + photoCircuit.getVoltage());
		}
		if ((/*photoCircuit.getVoltage() > 1.6 ||*/ button.get()) && !ballOutAlerted) {
			JRumble = new JoystickRumble(gamepad, 1);
			JRumble.start();
			ballOutAlerted = true;
			ballInAlerted = false;
			locked = false;
			System.out.println("Ball out: " + photoCircuit.getVoltage());
		}
		
		if (gamepad.getRawButton(8)) {
			latchB = true;
		}
		if (latchB) {
			winch.set(gamepad.getRawAxis(1));
			latch.set(gamepad.getRawAxis(5));
		}
	}
	
	public double drive(boolean record) {
		if (record) {
			if (gamepad.getRawButton(A_BUTTON)) {
				ballSpinner.set(-0.4);
			} else if (gamepad.getRawButton(B_BUTTON)) {
				ballSpinner.set(1.0);
			} else {
				ballSpinner.set(0.0);
			}
			
			
			if (gamepad.getRawButton(X_BUTTON) && !ballGrabberLS.get()) {
				ballGrabberPivot.set(0.7);
			}  else if (gamepad.getRawButton(Y_BUTTON)) {
				ballGrabberPivot.set(-0.7);
			} else {
				ballGrabberPivot.set(0.0);
			}
			
			if((/*photoCircuit.getVoltage() < 1.6 ||*/ !button.get()) && !ballInAlerted) {
				JRumble = new JoystickRumble(gamepad, 2);
				JRumble.start();
				ballInAlerted = true;
				ballOutAlerted = false;
			}
			if ((/*photoCircuit.getVoltage() > 1.6 || */button.get()) && !ballOutAlerted) {
				JRumble = new JoystickRumble(gamepad, 1);
				JRumble.start();
				ballOutAlerted = true;
				ballInAlerted = false;
			}
			
			return ballGrabberPivot.get();
		} else {
			drive();
			return 0;
		}
	}
}
