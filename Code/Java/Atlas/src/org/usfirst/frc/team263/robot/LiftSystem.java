package org.usfirst.frc.team263.robot;




import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class LiftSystem {

	private CANTalon toteLiftMotor1;
	private CANTalon toteLiftMotor2;
	private CANTalon canLiftMotor;

	private DigitalInput toteBottomLimitSwitch;
	private DigitalInput toteTopLimitSwitch;
	private DigitalInput canBottomLimitSwitch;
	private DigitalInput canTopLimitSwitch;

	private ElevatorSpeedAlgorithm toteLifterOutput;
	private ElevatorSpeedAlgorithm canLifterOutput;

	public LiftSystem() {
		toteLiftMotor1 = new CANTalon(Utilities.ShortLiftMotor1);
		toteLiftMotor2 = new CANTalon(Utilities.ShortLiftMotor2);
		canLiftMotor = new CANTalon(Utilities.CanLiftMotor);

		toteBottomLimitSwitch = new DigitalInput(Utilities.ShortBottomLimitSwitch);
		toteTopLimitSwitch = new DigitalInput(Utilities.ShortTopLimitSwitch);
		canBottomLimitSwitch = new DigitalInput(Utilities.CanBottomLimitSwitch);
		canTopLimitSwitch = new DigitalInput(Utilities.CanTopLimitSwitch);

		toteLifterOutput = new ElevatorSpeedAlgorithm();
		canLifterOutput = new ElevatorSpeedAlgorithm(0.1, 0.01, 25, 0.7, 0.5, 0.005, 5, 5);

		toteLiftMotor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		toteLiftMotor1.reverseSensor(true);
		canLiftMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		canLiftMotor.reverseSensor(true);

	}

	public void RunLifter(Joystick gamePad, Joystick drivePad) {
		boolean toteManualEnabled = true;
		boolean canManualEnabled = true;
		int ShortLevel = 0;
		int CanLevel = 0;
		int targetCount = 0;
		double canMotorOutput = 0;
		double shortMotorOutput = 0;
		boolean lifterToggle = false;
		double LockedInValue = 0;

		//Utilities.LimitSwitchRumble(drivePad, toteBottomLimitSwitch,0.1f, false);

		if (Utilities.GetJoystickButton(10, gamePad)) {
			lifterToggle = !lifterToggle;
			System.out.println("Toggle Boost");
		}

		if (toteBottomLimitSwitch.get() == true) {
			toteLiftMotor1.setPosition(0);
			System.out.println("Reset Tote at Bottom");
		}
		if (canBottomLimitSwitch.get() == true) {
			toteLiftMotor1.setPosition(0);
			System.out.println("Reset Can At Bottom");
		}
		if (toteTopLimitSwitch.get() == true) {

			toteLiftMotor1.setPosition(4500);
			System.out.println("Reset Tote At Top");
		}

		if (canBottomLimitSwitch.get() == true && gamePad.getRawButton(6)) {
			canLiftMotor.set(0);
			canLiftMotor.setPosition(0);
			canManualEnabled = true;
			System.out.println("Button 6 Manual Can Speed Zero, Reset Zero Level");
		}

		else if (canTopLimitSwitch.get() == true && gamePad.getRawButton(7)) {
			canLiftMotor.set(0);
			canManualEnabled = true;
			System.out.println("Button 7 Manual Can Speed Zero");
		}

		else if ((canTopLimitSwitch.get() == false || canTopLimitSwitch.get() == true) && gamePad.getRawButton(6)) {
			canLiftMotor.set(0.7);
			canManualEnabled = true;
			System.out.println("Button 6 Manual Can 70% (Downward)");
		}

		else if ((canBottomLimitSwitch.get() == true || canBottomLimitSwitch.get() == false)
				&& gamePad.getRawButton(7)) {
			canLiftMotor.set(-1);
			canManualEnabled = true;
			System.out.println("Button 7 Manual Can -100% (Upward)");
		} else {
			if (canManualEnabled) {
				canLiftMotor.set(0);
			}
		}

		if (toteBottomLimitSwitch.get() == true && gamePad.getRawButton(8)) {
			toteLiftMotor1.set(0);
			toteLiftMotor2.set(0);
			toteLiftMotor1.setPosition(0);
			LockedInValue = 0;
			toteManualEnabled = true;
			System.out.println("Bottom Limit Tote Speed 0");
		}

		else if (toteTopLimitSwitch.get() == true && gamePad.getRawButton(9)) {
			toteLiftMotor1.set(0);
			toteLiftMotor2.set(0);
			LockedInValue = 0;
			toteManualEnabled = true;
			System.out.println("Top Limit Tote Speed 0");
		}

		else if ((toteTopLimitSwitch.get() == false || toteTopLimitSwitch.get() == true) && gamePad.getRawButton(8)) {
			if (lifterToggle == true) {
				toteLiftMotor1.set(0.3);
				toteLiftMotor2.set(0.3);
				System.out.println("Button 8 Manual Tote 30% (Downward) BOOST");
			}

			else if (lifterToggle == false) {
				toteLiftMotor1.set(0.4);
				toteLiftMotor2.set(0.4);
				System.out.println("Button 8 Manual Tote 40% (Downward) BOOST");
			}
			LockedInValue = 0;
			toteManualEnabled = true;
		} else if ((toteBottomLimitSwitch.get() == false || toteBottomLimitSwitch.get() == true)
				&& gamePad.getRawButton(9)) {
			if (lifterToggle == true) {
				toteLiftMotor1.set(-0.65);
				toteLiftMotor2.set(-0.65);
				System.out.println("Button 9 Manual Tote -65% (Upward) BOOST");
			} else if (lifterToggle == false) {
				toteLiftMotor1.set(-1);
				toteLiftMotor2.set(-1);
				System.out.println("Button 9 Manual Tote -100% (Upward) BOOST ");
			}
			LockedInValue = 0;
			toteManualEnabled = true;
		} else if ((gamePad.getRawButton(8) == false && gamePad.getRawButton(9) == false)
				&& toteManualEnabled == true) {
			if (LockedInValue == 0) {
				LockedInValue =  toteLiftMotor1.getPosition();
			} else if (LockedInValue > 0) {
				SetToteSpeed(toteLifterOutput.ComputeNextMotorSpeedCommand((int) toteLiftMotor1.getPosition(),
						(int) LockedInValue));
			} else {
				toteLiftMotor1.set(0);
				toteLiftMotor2.set(0);
			}

		} else {
		}

		if (gamePad.getRawButton(11) == true) {
			ShortLevel = 0;
			toteManualEnabled = false;
			LockedInValue = 0;
			targetCount = Utilities.ElevatorShortLevels[ShortLevel];
			System.out.println("Button 11 Auto Tote Level 0");
		} else if (gamePad.getRawButton(12) == true) {
			ShortLevel = 1;
			toteManualEnabled = false;
			LockedInValue = 0;
			targetCount = Utilities.ElevatorShortLevels[ShortLevel];
			System.out.println("Button 12 Auto Tote Level 1");
		} else if (gamePad.getRawButton(13) == true) {
			ShortLevel = 2;
			toteManualEnabled = false;
			LockedInValue = 0;
			targetCount = Utilities.ElevatorShortLevels[ShortLevel];
			System.out.println("Button 13 Auto Tote Level 2");
		} else if (gamePad.getRawButton(14) == true) {
			ShortLevel = 3;
			toteManualEnabled = false;
			LockedInValue = 0;
			targetCount = Utilities.ElevatorShortLevels[ShortLevel];
			System.out.println("Button 14 Auto Tote LEvel 3");
		} else if (gamePad.getRawButton(15) == true) {
			ShortLevel = 4;
			toteManualEnabled = false;
			LockedInValue = 0;
			targetCount = Utilities.ElevatorShortLevels[ShortLevel];
			System.out.println("Button 15 Auto Tote Level 4");
		}

		if (gamePad.getRawButton(1) == true) {
			CanLevel = 0;
			canManualEnabled = false;
			System.out.println("Button 1 Auto Can Level 0");
		} else if (gamePad.getRawButton(2) == true) {
			CanLevel = 1;
			canManualEnabled = false;
			System.out.println("Button 2 Auto Can Level 1");
		} else if (gamePad.getRawButton(3) == true) {
			CanLevel = 2;
			canManualEnabled = false;
			System.out.println("Button 3 Auto Can Level 2");
		}

		else if (gamePad.getRawButton(4) == true) {
			CanLevel = 3;
			canManualEnabled = false;
			System.out.println("Button 4 Auto Can Level 3");
		}
		if (toteManualEnabled == false) {
			if (gamePad.getRawButton(17)) {
				targetCount = targetCount + 30;
				System.out.println("Button 17 Nudge Tote a little higher");
			} else if (gamePad.getRawButton(18)) {
				targetCount = targetCount - 30;
				System.out.println("Button 18 Nudge Tote a little lower");
			}
			targetCount = (int) Utilities.MaxValue(targetCount, 0, 4500);

			shortMotorOutput = toteLifterOutput.ComputeNextMotorSpeedCommand((int) toteLiftMotor1.getPosition(),
					targetCount);

			if (toteTopLimitSwitch.get() == true && shortMotorOutput < 0) {
				shortMotorOutput = 0;
			}
			SetToteSpeed(shortMotorOutput);
		}
		if (canManualEnabled == false) {
			canMotorOutput = canLifterOutput.ComputeNextMotorSpeedCommand((int) canLiftMotor.getPosition(),
					Utilities.ElevatorCanLevels[CanLevel]);

			if (canBottomLimitSwitch.get() == true && canMotorOutput < 0) {
				canMotorOutput = 0;
			} else if (canTopLimitSwitch.get() == true && canMotorOutput > 0) {
				canMotorOutput = 0;
			}
			SetCanSpeed(canMotorOutput);
		}
	}

	public void ResetLifter() {
		if (toteBottomLimitSwitch.get() == false) {
			toteLiftMotor1.set(1);
		}

		else if (toteBottomLimitSwitch.get() == true) {
			toteLiftMotor1.set(0);
		}

		if (canBottomLimitSwitch.get() == false) {
			canLiftMotor.set(1);
		}

		else if (canBottomLimitSwitch.get() == true) {
			canLiftMotor.set(0);
		}
	}

	public void SetCanSpeed(double Speed) {
		canLiftMotor.set(Speed * -1);
	}

	public void SetToteSpeed(double Speed) {
		toteLiftMotor1.set(Speed * -1);
		toteLiftMotor2.set(Speed * -1);
	}

	public void SetZero() {
		canLiftMotor.set(0);
		toteLiftMotor1.set(0);
		toteLiftMotor2.set(0);
	}

}
