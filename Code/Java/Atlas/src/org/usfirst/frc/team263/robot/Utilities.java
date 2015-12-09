package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Utilities {

	public Utilities() {
	}

	// CAN Motors
	public static final int FrontLeftMotor = 10;
	public static final int FrontRightMotor = 9;
	public static final int BackLeftMotor = 2;
	public static final int BackRightMotor = 5;

	public static final int CanLiftMotor = 8;
	public static final int ShortLiftMotor1 = 3;
	public static final int ShortLiftMotor2 = 6;

	// DIO
	public static final int ShortBottomLimitSwitch = 4;
	public static final int ShortTopLimitSwitch = 3;
	public static final int CanBottomLimitSwitch = 1;
	public static final int CanTopLimitSwitch = 0;
	public static final int LongToteSensor = 6;
	public static final int ShortToteSensor = 8;

	// Analog Input
	public static final int Gyro = 0;

	// Pnuematic Control Module(PCM)
	public static final int PivotPistonForwardChannel = 0;
	public static final int PivotPistonReverseChannel = 1;

	// Arrays
	public static final int[] ElevatorShortLevels = new int[] { 0, 856, 1560, 2700, 4400, 1660 };
	public static final int[] ElevatorCanLevels = new int[] { 0, 1000, 1973, 3500, 1950, 2400 };
	public static final int[] AutonDrive1 = new int[] { 0, 480, 4300, 6340, 6470, 8300, 7640, 9000 };
	public static final int[] AutonDrive2 = new int[] { 0, 450, 200, 300, 400 };

	public static float fabs(float val) {
		if (val < 0) {
			val = val * -1;
		}
		return val;
	}

	public static double fabs(double val) {
		if (val < 0) {
			val = val * -1;
		}
		return val;
	}

	public static int fabs(int val) {
		if (val < 0) {
			val = val * -1;
		}
		return val;
	}
	
	public static double DeadBand(double input, double deadband) {
		if (fabs(input) <= deadband) {
			return 0.0f;
		}

		return input;
	}

	public static double MaxValue(double input, double min, double max) {
		if (input > max) {
			input = max;
		} else if (input < min) {
			input = min;
		}
		return input;
	}

	public static boolean GetJoystickButton(int RawButton, Joystick HID) {
		final int maxButtons = 25;
		boolean[] lastButtonValue = new boolean[maxButtons];
		boolean currentButtonValue = false;
		boolean currentPressedState = false;

		boolean firstTimeCalled = true;
		if (firstTimeCalled) {
			for (int i = 0; i < maxButtons; i++) {
				lastButtonValue[i] = false;
			}
			firstTimeCalled = false;
		}

		currentButtonValue = HID.getRawButton(RawButton);


		if ((lastButtonValue[RawButton] == false) && (currentButtonValue == true)) {
			currentPressedState = true;
		}

		else {
			currentPressedState = false;
		}
		lastButtonValue[RawButton] = currentButtonValue;
		return currentPressedState;

	}
	

	public static void LimitSwitchRumble(Joystick HID, DigitalInput LS, double RumbleLength, boolean HardRumble) {
		final double rumbleTimeOut = RumbleLength;
		boolean lastLSValue = false;
		boolean currentLSValue = false;
		Timer rumbleTimer = new Timer();
		rumbleTimer.start();

		currentLSValue = LS.get();
		if (lastLSValue == false && currentLSValue == true) {
			rumbleTimer.reset();
			rumbleTimer.start();

			if (HardRumble == true) {
				HID.setRumble(Joystick.RumbleType.kLeftRumble, 0.3f);
			} else if (HardRumble == false) {
				HID.setRumble(Joystick.RumbleType.kRightRumble,0.3f);
			}
		} else if (lastLSValue == true && currentLSValue == false) {
			if (HardRumble == true) {
				HID.setRumble(Joystick.RumbleType.kLeftRumble, 0);
			} else if (HardRumble == false) {
				HID.setRumble(Joystick.RumbleType.kRightRumble, 0);
			}
		} else if (rumbleTimer.hasPeriodPassed(rumbleTimeOut)) {
			if (HardRumble == true) {
				HID.setRumble(Joystick.RumbleType.kLeftRumble, 0);
			} else if (HardRumble == false) {
				HID.setRumble(Joystick.RumbleType.kRightRumble, 0);
			}
		}
		lastLSValue = currentLSValue;
	}

}