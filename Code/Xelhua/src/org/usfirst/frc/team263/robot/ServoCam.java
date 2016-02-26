package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

/**
 * Controls camera
 * @author Greg Strakhov
 * @author Dan Waxman
 * @author Rohan Bapat
 */
public class ServoCam {
	private Servo serElevation;
	private Servo serAzimuth;
	private Timer t = new Timer();
	private boolean first = true;

	public ServoCam() {
		serElevation = new Servo(4);
		serAzimuth = new Servo(5);
		t.start();
		park();
	}
	
	/**
	 * Sets camera in "safe" spot
	 */
	public void park() {
		serElevation.setAngle(0);
		serAzimuth.setAngle(180);
	}
	
	/**
	 * Sets angle given
	 * @param elevation Desired elevation angle
	 * @param azimuth Desired azimuth angle
	 */
	public void setAngle(int elevation, int azimuth) {
		serElevation.setAngle(elevation);
		serAzimuth.setAngle(azimuth);
	}

	/**
	 * "Drives" servos using two joystick dpads
	 * @param joy1 Joystick one to control servos
	 * @param joy2 Joystick two to control servos
	 */
	public void RunServos(Joystick joy1, Joystick joy2) {
		if (first) {
			serElevation.setAngle(90);
			serAzimuth.setAngle(180);
			first = false;
		}
		if (t.get() >= 0.25) { 
			double serE = serElevation.getAngle();
			double serA = serAzimuth.getAngle();
			
			if (joy1.getPOV() == 0 || joy2.getPOV() == 0) {
				serElevation.setAngle(serE + 10);
				t.reset();
				t.start();
			}
			if (joy1.getPOV() == 180 || joy2.getPOV() == 180) {
				serElevation.setAngle(serE - 10);
				t.reset();
				t.start();
			}
	
			if (joy1.getPOV() == 90 || joy2.getPOV() == 90) {
				serAzimuth.setAngle(serA + 10);
				t.reset();
				t.start();
			}
			if (joy1.getPOV() == 270 || joy2.getPOV() == 270) {
				serAzimuth.setAngle(serA - 10);
				t.reset();
				t.start();
			}
			if (joy1.getRawButton(5) || joy2.getRawButton(5)) {
				serElevation.setAngle(90);
				t.reset();
				t.start();
			}
			if (joy1.getRawButton(6) || joy2.getRawButton(6)) {
				serAzimuth.setAngle(90);
				t.reset();
				t.start();
			}
		}
	}
}
