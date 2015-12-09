package org.usfirst.frc.team263.robot;


import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SerialPort;

public class MecanumDrive {

	private CANTalon FLMotor;
	private CANTalon FRMotor;
	private CANTalon BLMotor;
	private CANTalon BRMotor;

	private AHRS Navx;

	private double FLSpeed;
	private double FRSpeed;
	private double BLSpeed;
	private double BRSpeed;
	private boolean firstRun;

	public MecanumDrive() {
		FRMotor = new CANTalon(Utilities.FrontRightMotor);
		FLMotor = new CANTalon(Utilities.FrontLeftMotor);
		BRMotor = new CANTalon(Utilities.BackRightMotor);
		BLMotor = new CANTalon(Utilities.BackLeftMotor);
		FLMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		FRMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		BLMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		BRMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

		Navx = new AHRS(SerialPort.Port.kMXP);
		firstRun = true;


		FRMotor.set(0);
		BRMotor.set(0);
		BLMotor.set(0);
		FLMotor.set(0);
		FRMotor.setPosition(0);
		FLMotor.setPosition(0);
		BLMotor.setPosition(0);
		BRMotor.setPosition(0);

		FRMotor.reverseSensor(true);
		BRMotor.reverseSensor(true);

		FLSpeed = 0;
		FRSpeed = 0;
		BLSpeed = 0;
		BRSpeed = 0;
	}

	public void CalibrateNavX() {
		if (firstRun) {
			boolean is_calibrating = Navx.isCalibrating();
			if (!is_calibrating) {
				Timer.delay(0.3);
				Navx.zeroYaw();
				firstRun = false;
			}
		}
	}

	public void Drive(Joystick drivePad) {
		double yDrive = 0;
		double xDrive = 0;
		double Rotate = 0;
		
		boolean throttleEnabled = true;
		if (Utilities.GetJoystickButton(1, drivePad)) {
			throttleEnabled = !throttleEnabled;
		}

		if (throttleEnabled == true) {
			yDrive = drivePad.getY() / 1.7;
			xDrive = (drivePad.getX() * -1) / 1.7;
			Rotate = (-drivePad.getThrottle() + drivePad.getTwist()) / 1.7;
		}

		else if (throttleEnabled == false) {
			yDrive = drivePad.getY();
			xDrive = (drivePad.getX() * -1) / 1.7;
			Rotate = (-drivePad.getThrottle() + drivePad.getTwist()) / 1.7;
		}

		yDrive = Utilities.DeadBand(yDrive, 0.1);
		xDrive = Utilities.DeadBand(xDrive, 0.1);
		Rotate = Utilities.DeadBand(Rotate, 0.1);

		FLSpeed = xDrive + yDrive + Rotate;
		FRSpeed = -xDrive + yDrive - Rotate;
		BLSpeed = -xDrive + yDrive + Rotate;
		BRSpeed = xDrive + yDrive - Rotate;

		double max = 0;

		if (Utilities.fabs(FLSpeed) > max) {
			max = Utilities.fabs(FLSpeed);
		}
		if (Utilities.fabs(FRSpeed) > max) {
			max = Utilities.fabs(FRSpeed);
		}
		if (Utilities.fabs(BLSpeed) > max) {
			max = Utilities.fabs(BLSpeed);
		}
		if (Utilities.fabs(BRSpeed) > max) {
			max = Utilities.fabs(BRSpeed);
		}

		if (max > 1) {
			FLSpeed = FLSpeed / max;
			FRSpeed = FRSpeed / max;
			BLSpeed = BLSpeed / max;
			BRSpeed = BRSpeed / max;
		}

		if (drivePad.getRawButton(5)) {
			FLMotor.set(0);
			FRMotor.set(0);
			BLMotor.set(-0.8);
			BRMotor.set(-0.8);
		} else if (drivePad.getRawButton(6)) {
			FLMotor.set(0);
			FRMotor.set(0);
			BLMotor.set(0.8);
			BRMotor.set(0.8);
		}

		else if (drivePad.getRawButton(2)) {
			AutonDriveStraight(false, -0.4, true);
		} else if (drivePad.getRawButton(3)) {
			AutonDriveStraight(false, 0.4, true);
		} else {
			FLMotor.set(-FLSpeed);
			FRMotor.set(FRSpeed);
			BLMotor.set(-BLSpeed);
			BRMotor.set(BRSpeed);
		}
	}

	public void AutonDriveStraight(boolean GyroEnabled, double speed, boolean Strafe) {
		double driveX = 0;
		double driveY = 0;

		if (Strafe) {
			driveX = speed;
			driveY = 0;
		}

		else if (!Strafe) {
			driveY = speed;
			driveX = 0;
		}

		double twist = Navx.getYaw() * 3 / 180;

		float angle = Navx.getYaw() * -1;

		if (angle < 0) {
			angle += 360;
		}

		if (GyroEnabled && !Strafe) {
			double temp = driveY * Math.cos(angle * (Math.PI / 180)) - driveX * Math.sin(angle * (Math.PI / 180));
			driveX = driveY * Math.sin(angle * (Math.PI / 180)) - driveX * Math.cos(angle * (Math.PI / 180));
			driveY = temp;
		}

		else if (GyroEnabled && Strafe) {
			double temp = driveX * Math.cos(angle * (Math.PI / 180)) - driveY * Math.sin(angle * (Math.PI / 180));
			driveY = driveX * Math.sin(angle * (Math.PI / 180)) - driveY * Math.cos(angle * (Math.PI / 180));
			driveX = temp;
		}

		else if (GyroEnabled) {
			twist = 0;
		}
		FLSpeed = driveX + driveY + twist;
		FRSpeed = -driveX + driveY - twist;
		BLSpeed = -driveX + driveY + twist;
		BRSpeed = driveX + driveY - twist;

		double Max = 0;

		if (Utilities.fabs(FLSpeed) > Max) {
			Max = Utilities.fabs(FLSpeed);
		}
		if (Utilities.fabs(FRSpeed) > Max) {
			Max = Utilities.fabs(FRSpeed);
		}
		if (Utilities.fabs(BLSpeed) > Max) {
			Max = Utilities.fabs(BLSpeed);
		}
		if (Utilities.fabs(BRSpeed) > Max) {
			Max = Utilities.fabs(BRSpeed);
		}

		if (Max > 1.0) {
			FLSpeed = FLSpeed / Max;
			FRSpeed = FRSpeed / Max;
			BLSpeed = BLSpeed / Max;
			BRSpeed = BRSpeed / Max;
		}

		FLMotor.set(-FLSpeed);
		FRMotor.set(FRSpeed);
		BLMotor.set(-BLSpeed);
		BRMotor.set(BRSpeed);

		if (Strafe == true) {
			FLMotor.set(-FLSpeed);
			FRMotor.set(FRSpeed);
			BLMotor.set(-BLSpeed);
			BRMotor.set(BRSpeed);
		}

	}

	public void AutonTurn(double Speed) {
		double driveX = 0;
		double driveY = 0;
		double twist = Speed;

		FLSpeed = driveX + driveY + twist;
		FRSpeed = -driveX + driveY - twist;
		BLSpeed = -driveX + driveY + twist;
		BRSpeed = driveX + driveY - twist;

		double Max = 0;

		if (Utilities.fabs(FLSpeed) > Max) {
			Max = Utilities.fabs(FLSpeed);
		}
		if (Utilities.fabs(FRSpeed) > Max) {
			Max = Utilities.fabs(FRSpeed);
		}
		if (Utilities.fabs(BLSpeed) > Max) {
			Max = Utilities.fabs(BLSpeed);
		}
		if (Utilities.fabs(BRSpeed) > Max) {
			Max = Utilities.fabs(BRSpeed);
		}
		if (Max > 1.0) {
			FLSpeed = FLSpeed / Max;
			FRSpeed = FRSpeed / Max;
			BLSpeed = BLSpeed / Max;
			BRSpeed = BRSpeed / Max;
		}

		FLMotor.set(-FLSpeed);
		FRMotor.set(FRSpeed);
		BLMotor.set(-BLSpeed);
		BRMotor.set(BRSpeed);
	}

	public void AutonDiagonalStrafe(boolean NegativeX, double Speed) {
		double driveX = Speed;
		double driveY = Speed;
		double twist = 0;

		if (NegativeX) {
			FLSpeed = 0;
			FRSpeed = -driveX + driveY - twist;
			BLSpeed = -driveX + driveY + twist;
			BRSpeed = 0;
		}

		else if (!NegativeX) {
			FLSpeed = driveX + driveY + twist;
			FRSpeed = 0;
			BLSpeed = 0;
			BRSpeed = driveX + driveY - twist;
		}

		double Max = 0;

		if (Utilities.fabs(FLSpeed) > Max) {
			Max = Utilities.fabs(FLSpeed);
		}
		if (Utilities.fabs(FRSpeed) > Max) {
			Max = Utilities.fabs(FRSpeed);
		}
		if (Utilities.fabs(BLSpeed) > Max) {
			Max = Utilities.fabs(BLSpeed);
		}
		if (Utilities.fabs(BRSpeed) > Max) {
			Max = Utilities.fabs(BRSpeed);
		}

		if (Max > 1.0) {
			FLSpeed = FLSpeed / Max;
			FRSpeed = FRSpeed / Max;
			BLSpeed = BLSpeed / Max;
			BRSpeed = BRSpeed / Max;
		}

		FLMotor.set(-FLSpeed);
		FRMotor.set(FRSpeed);
		BLMotor.set(-BLSpeed);
		BRMotor.set(BRSpeed);
	}

	public void SetZero() {
		FLMotor.set(0);
		FRMotor.set(0);
		BLMotor.set(0);
		BRMotor.set(0);
	}

	public double AverageEncoders() {
		return (FLMotor.getPosition() + FRMotor.getPosition() + BLMotor.getPosition() + BRMotor.getPosition()) / 4;
	}

	public double AverageTurnRightEncoders() {
		return (FLMotor.getPosition() + -FRMotor.getPosition() + BLMotor.getPosition() + -BRMotor.getPosition()) / 4;
	}

	public double AverageTurnLeftEncoders() {
		return (-FLMotor.getPosition() + FRMotor.getPosition() + -BLMotor.getPosition() + BRMotor.getPosition()) / 4;
	}

	public double AverageLeftStrafe() {
		return (-FLMotor.getPosition() + FRMotor.getPosition() + BLMotor.getPosition() + -BRMotor.getPosition()) / 4;
	}

	public void ResetEncoders() {
		FLMotor.setPosition(0);
		FRMotor.setPosition(0);
		BLMotor.setPosition(0);
		BRMotor.setPosition(0);
	}
}
