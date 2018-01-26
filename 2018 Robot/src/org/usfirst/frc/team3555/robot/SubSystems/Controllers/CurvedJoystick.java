package org.usfirst.frc.team3555.robot.SubSystems.Controllers;

import edu.wpi.first.wpilibj.Joystick;

public class CurvedJoystick extends Joystick {
	public static final double defaultExponent = 2;
	public static final double defaultDeadzone = .05;
	
	private double deadzone; 
	
	public CurvedJoystick(int port, double deadzone) {
		super(port);
		this.deadzone = deadzone;
	}

	public CurvedJoystick(int port) {
		this(port, defaultDeadzone);
	}
	
	public double getCurvedX() { return getCurvedX(defaultExponent); }
	public double getCurvedX(double exponent) {
		return curveData(getX(), exponent);
	}
	
	public double getCurvedY() { return getCurvedY(defaultExponent); }
	public double getCurvedY(double exponent) {
		return curveData(getY(), exponent);
	}
	
	public double getCurvedZ() { return getCurvedZ(defaultExponent); }
	public double getCurvedZ(double exponent) {
		return curveData(getZ(), exponent);
	}
	
	/**
	 * Get the raw -1 - 1 x value of the joystick
	 * 
	 * @return - The value of the x axis with a min of the deadzone
	 */
	public double getRawX() {
		return capDeadzone(getX());
	}
	
	/**
	 * Get the raw -1 - 1 y value of the joystick
	 * 
	 * @return - The value of the y axis with a min of the deadzone
	 */
	public double getRawY() {
		return capDeadzone(getY());
	}
	
	/**
	 * Get the raw -1 - 1 z value of the joystick
	 * 
	 * @return - The value of the z axis with a min of the deadzone
	 */
	public double getRawZ() {
		return capDeadzone(getZ());
	}
	
	private double curveData(double data, double exponent) {
		return capDeadzone(data > 0 ? Math.pow(data, exponent) : -Math.pow(Math.abs(data), exponent));
	}

	public double capDeadzone(double data) { return capDeadzone(data, deadzone); }
	public double capDeadzone(double data, double deadzone) {
		if(Math.abs(data) < deadzone)
			return 0;
		return data;
	}
}
