package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 * Holds static methods for traversing pre-recorded obstacles
 * @author Dan Waxman
 * @since 2016-02-16
 * @version 1.0
 */
public class Presets {
	private static final double RECORD_LENGTH = 0.1;
	private static final int INTERRUPT_BUTTON = 8;
	
	private static final double[] portcullisL = {};
	private static final double[] chevalL = {};
	private static final double[] moatL = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.4140625, -.53125, -0.625, -0.8671875, -0.9765625, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, -0.16206896551724137, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	private static final double[] rampartsL = {};
	private static final double[] drawbridgeL = {};
	private static final double[] sallyPortL = {};
	private static final double[] rockWallL = {-.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5};
	private static final double[] roughTerrainL = {};
	private static final double[] lowBarL = {};
	private static final double[] portcullisR = {};
	private static final double[] chevalR = {};
	private static final double[] moatR = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.4140625, -.53125, -0.625, -0.8671875, -0.9765625, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -0.3671875, -0.609375, -0.953125, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -0.40625, 0.0, 0.0, 0.0, -0.4296875, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	private static final double[] rampartsR = {};
	private static final double[] drawbridgeR = {};
	private static final double[] sallyPortR = {};
	private static final double[] rockWallR = {-.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5, -.5};
	private static final double[] roughTerrainR = {};
	private static final double[] lowBarR = {};
	private static final double[] portcullisMech = {};
	private static final double[] chevalMech = {};
	private static final double[] drawbridgeMech = {};
	private static final double[] sallyPortMech = {};
	private static final Joystick interruptJS = new Joystick(0);
	
	/**
	 * Traverses portcullis based on pre-defined path
	 * @param leftFront Front left drivebase motor
	 * @param leftBack Back left drivebase motor
	 * @param rightFront Front right drivebase motor
	 * @param rightBack Back right drivebase motor
	 * @param arm Arm pivot
	 */
	public static void portcullis(Talon leftFront, CANTalon leftBack, Talon rightFront, Talon rightBack, Talon arm) {
		for (int i = 0; i < Math.min(Math.min(portcullisL.length, portcullisR.length), portcullisMech.length); i++) {
			if (interruptJS.getRawButton(INTERRUPT_BUTTON)) break;
			leftFront.set(-portcullisL[i]);
			leftBack.set(portcullisL[i]);
			rightFront.set(-portcullisR[i]);
			rightBack.set(portcullisR[i]);
			arm.set(portcullisMech[i]);
			Timer.delay(RECORD_LENGTH);
		}
		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
		arm.set(0);
	}
	
	/**
	 * Traverses Cheval de Frise based on pre-defined path
	 * @param leftFront Front left drivebase motor
	 * @param leftBack Back left drivebase motor
	 * @param rightFront Front right drivebase motor
	 * @param rightBack Back right drivebase motor
	 * @param arm Arm pivot
	 */
	public static void cheval(Talon leftFront, CANTalon leftBack, Talon rightFront, Talon rightBack, Talon arm) {
		for (int i = 0; i < Math.min(Math.min(chevalL.length, chevalR.length), chevalMech.length); i++) {
			if (interruptJS.getRawButton(INTERRUPT_BUTTON)) break;
			leftFront.set(-chevalL[i]);
			leftBack.set(chevalL[i]);
			rightFront.set(-chevalR[i]);
			rightBack.set(chevalR[i]);
			arm.set(chevalMech[i]);
			Timer.delay(RECORD_LENGTH);
		}
		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
		arm.set(0);
	}
	
	/**
	 * Traverses moat based on pre-defined path
	 * @param leftFront Front left drivebase motor
	 * @param rightBack Back right drivebase motor
	 * @param rightFront Front right drivebase motor
	 * @param leftBack Back left drivebase motor
	 */
	public static void moat(Talon leftFront, Talon rightBack, Talon rightFront, CANTalon leftBack) {
		for (int i = 0; i < Math.min(moatL.length, moatR.length); i++) {
			if (interruptJS.getRawButton(INTERRUPT_BUTTON)) break;
			leftFront.set(-moatL[i]);
			leftBack.set(moatL[i]);
			rightFront.set(-moatR[i]);
			rightBack.set(moatR[i]);
			Timer.delay(RECORD_LENGTH);
		}
		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
	}
	
	/**
	 * Traverses ramparts based on pre-defined path
	 * @param leftFront Front left drivebase motor
	 * @param leftBack Back left drivebase motor
	 * @param rightFront Front right drivebase motor
	 * @param rightBack Back right drivebase motor
	 */
	public static void ramparts(Talon leftFront, CANTalon leftBack, Talon rightFront, Talon rightBack) {
		for (int i = 0; i < Math.min(rampartsL.length, rampartsR.length); i++) {
			if (interruptJS.getRawButton(INTERRUPT_BUTTON)) break;
			leftFront.set(-rampartsL[i]);
			leftBack.set(rampartsL[i]);
			rightFront.set(-rampartsR[i]);
			rightBack.set(rampartsR[i]);
			Timer.delay(RECORD_LENGTH);
		}
		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
	}
	
	/**
	 * Traverses drawbridge based on pre-defined path
	 * @param leftFront Front left drivebase motor
	 * @param leftBack Back left drivebase motor
	 * @param rightFront Front right drivebase motor
	 * @param rightBack Back right drivebase motor
	 * @param arm Arm pivot
	 */
	public static void drawbridge(Talon leftFront, CANTalon leftBack, Talon rightFront, Talon rightBack, Talon arm) {
		for (int i = 0; i < Math.min(Math.min(drawbridgeL.length, drawbridgeR.length), drawbridgeMech.length); i++) {
			if (interruptJS.getRawButton(INTERRUPT_BUTTON)) break;
			leftFront.set(-drawbridgeL[i]);
			leftBack.set(drawbridgeL[i]);
			rightFront.set(-drawbridgeR[i]);
			rightBack.set(drawbridgeR[i]);
			arm.set(drawbridgeMech[i]);
			Timer.delay(RECORD_LENGTH);
		}
		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
		arm.set(0);
	}
	
	/**
	 * Traverses sally port based on pre-defined path
	 * @param leftFront Front left drivebase motor
	 * @param leftBack Back left drivebase motor
	 * @param rightFront Front right drivebase motor
	 * @param rightBack Back right drivebase motor
	 * @param arm Arm pivot
	 */
	public static void sallyPort(Talon leftFront, CANTalon leftBack, Talon rightFront, Talon rightBack, Talon arm) {
		for (int i = 0; i < Math.min(Math.min(sallyPortL.length, sallyPortR.length), sallyPortMech.length); i++) {
			if (interruptJS.getRawButton(INTERRUPT_BUTTON)) break;
			leftFront.set(-sallyPortL[i]);
			leftBack.set(sallyPortL[i]);
			rightFront.set(-sallyPortR[i]);
			rightBack.set(sallyPortR[i]);
			arm.set(sallyPortMech[i]);
			Timer.delay(RECORD_LENGTH);
		}
		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
		arm.set(0);
	}
	
	/**
	 * Traverses rock wall based on pre-defined path
	 * @param leftFront Front left drivebase motor
	 * @param leftBack Back left drivebase motor
	 * @param rightFront Front right drivebase motor
	 * @param rightBack Back right drivebase motor
	 */
	public static void rockWall(Talon leftFront, Talon leftBack, Talon rightFront, CANTalon rightBack) {
		for (int i = 0; i < Math.min(rockWallL.length, rockWallR.length); i++) {
			if (interruptJS.getRawButton(INTERRUPT_BUTTON)) break;
			leftFront.set(-rockWallL[i]);
			leftBack.set(rockWallL[i]);
			rightFront.set(-rockWallR[i]);
			rightBack.set(rockWallR[i]);
			Timer.delay(RECORD_LENGTH);
		}
		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
	}
	
	/**
	 * Traverses rough terrain based on pre-defined path
	 * @param leftFront Front left drivebase motor
	 * @param leftBack Back left drivebase motor
	 * @param rightFront Front right drivebase motor
	 * @param rightBack Back right drivebase motor
	 */
	public static void roughTerrain(Talon leftFront, CANTalon leftBack, Talon rightFront, Talon rightBack) {
		for (int i = 0; i < Math.min(roughTerrainL.length, roughTerrainR.length); i++) {
			if (interruptJS.getRawButton(INTERRUPT_BUTTON)) break;
			leftFront.set(-roughTerrainL[i]);
			leftBack.set(roughTerrainL[i]);
			rightFront.set(-roughTerrainR[i]);
			rightBack.set(roughTerrainR[i]);
			Timer.delay(RECORD_LENGTH);
		}
		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
	}
	
	/**
	 * Traverses low bar based on pre-defined path
	 * @param leftFront Front left drivebase motor
	 * @param leftBack Back left drivebase motor
	 * @param rightFront Front right drivebase motor
	 * @param rightBack Back right drivebase motor
	 */
	public static void lowBar(Talon leftFront, CANTalon leftBack, Talon rightFront, Talon rightBack) {
		for (int i = 0; i < Math.min(lowBarL.length, lowBarR.length); i++) {
			if (interruptJS.getRawButton(INTERRUPT_BUTTON)) break;
			leftFront.set(-lowBarL[i]);
			leftBack.set(lowBarL[i]);
			rightFront.set(-lowBarR[i]);
			rightBack.set(lowBarR[i]);
			Timer.delay(RECORD_LENGTH);
		}
		leftFront.set(0);
		leftBack.set(0);
		rightFront.set(0);
		rightBack.set(0);
	}
}
