package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class Winch {
	CANTalon latchMotor, winch;
	
	public Winch() {
		latchMotor = new CANTalon(3);
		winch = new CANTalon(4);
	}
	
	public void runMotor() {
		
	}
}
