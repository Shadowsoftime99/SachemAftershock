package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

public class Autonomous {
	private DigitalInput ones;
	private DigitalInput twos;
	private DigitalInput fours;
	private int phase;
	
	public Autonomous() {
		ones = new DigitalInput(7);
		twos = new DigitalInput(8);
		fours = new DigitalInput(9);
	}
	
	public void detectPhase() {
		int sum = 0;
		if (!ones.get()) sum += 1;
		if (!twos.get()) sum += 2;
		if (!fours.get()) sum += 4;
		System.out.println(sum);
		phase = sum;
	}
	
	public void autoDrive() {
		if (phase == 6) {
			Presets.rockWall(DriveControls.getLeftFront(), DriveControls.getLeftBack(), DriveControls.getRightFront(), DriveControls.getRightBack());
		}
	}
}
