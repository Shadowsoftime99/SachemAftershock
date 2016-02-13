package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.PWM;

public class LedStrip {
	PWM strip;
	
	public LedStrip(int port) {
		strip = new PWM(port);
	}
	
	public void setGreen() {
		strip.setRaw(0);
	}
	
	public void setBlue() {
		strip.setRaw(100);
	}
	
	public void setRed() {
		strip.setRaw(255);
	}
	
	public void setPurple() {
		strip.setRaw(200);
	}
}
