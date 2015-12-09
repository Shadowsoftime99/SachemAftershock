package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

public class PivotPiston {
	DoubleSolenoid doubleSolenoid;

	enum PivotPistonPosition {
		Retract, Deploy
	};

	public PivotPiston() {
		doubleSolenoid = new DoubleSolenoid(Utilities.PivotPistonForwardChannel, Utilities.PivotPistonReverseChannel);
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	public void CommandPivotPistonPosition(Joystick drivePad) {
		if (Utilities.GetJoystickButton(2, drivePad)) {
			if (doubleSolenoid.get() == DoubleSolenoid.Value.kReverse) {
				doubleSolenoid.set(DoubleSolenoid.Value.kForward);
			} else {
				doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
			}
		}

	}

	public void CommandPivotPistonPosition(PivotPistonPosition commandedPivotPosition) {
		if (commandedPivotPosition == PivotPistonPosition.Deploy) {
			doubleSolenoid.set(DoubleSolenoid.Value.kForward);
		} else {
			doubleSolenoid.set(DoubleSolenoid.Value.kForward);
		}
	}
}
