package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends SampleRobot {
    Joystick DrivePad;
    SixWD Drive;
    
    public Robot() {
        DrivePad = new Joystick(0);
        Drive = new SixWD();
    }

 
    public void autonomous() {
  
    }
    public void operatorControl() {
        while (isOperatorControl() && isEnabled()) {
        	Drive.Drive(DrivePad);
        }
    }

    public void test() {
    }
}
