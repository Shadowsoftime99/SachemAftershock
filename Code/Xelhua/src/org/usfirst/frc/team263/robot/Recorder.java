package org.usfirst.frc.team263.robot;

import java.util.ArrayList;

/**
 * Utility for recording user movement
 * @author Dan Waxman
 * @since 2016-02-16
 * @version 1.0
 */
public class Recorder {
	private ArrayList<Double> leftSide;
	private ArrayList<Double> rightSide;
	private ArrayList<Double> mech;
	
	public Recorder() {
		leftSide = new ArrayList<Double>();
		rightSide = new ArrayList<Double>();
		mech = new ArrayList<Double>();
	}
	
	/**
	 * Add drive controls to array
	 * @param controls {leftSpeed, rightSpeed}
	 */
	public void addDrive(double[] controls) {
		if (controls.length != 2) {
			throw new IllegalArgumentException("Recorder.addDrive(double[] controls) can only receive an array of length 2");
		}
		leftSide.add(controls[0]);
		rightSide.add(controls[1]);
	}
	
	/**
	 * Add mech control to array
	 * @param control Control speed for arm
	 */
	public void addMech(double control) {
		mech.add(control);
	}
	
	/**
	 * Prints out arrays to screen
	 */
	public void retrieveArrays() {
		System.out.println("Left side controls: " + leftSide.toString());
		System.out.println("Right side controls: " + rightSide.toString());
		System.out.println("Mechanical controls: " + mech.toString());
	}
}
