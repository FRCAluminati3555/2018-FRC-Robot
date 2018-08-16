package org.usfirst.frc.team3555.robot.SubSystems.Controllers;

import edu.wpi.first.wpilibj.Joystick;

public class CurvedJoystick extends Joystick {
	public static final double defaultExponent = 2;
	public static final double defaultDeadzone = .05;
	
	private double deadzone; 
	
	/**
	 * Joystick class that will put a min cap with the deadzone. 
	 * Also it can curve the input with an exponent.
	 * 
	 * @param port -> USB Port for the joystick 
	 * @param deadzone -> The small zone around joystick that prevents small values like .0047 from being applied to the motor 
	 */
	public CurvedJoystick(int port, double deadzone) {
		super(port);
		this.deadzone = deadzone;
	}

	/**
	 * Joystick class that will put a min cap with the deadzone. 
	 * Also it can curve the input with an exponent.
	 * 
	 * NOTE: This will use the default deadzone as specified by the static member of this class: defaultDeadzone
	 * 
	 * @param port -> USB Port for the joystick 
	 */
	public CurvedJoystick(int port) {
		this(port, defaultDeadzone);
	}
	
	/**
	 * @return -> The input of the x-axis raised to the default exponent
	 */
	public double getCurvedX() { return getCurvedX(defaultExponent); }
	
	/**
	 * 
	 * @param exponent -> The exponent to raise the x-axis value by
	 * @return -> The input of the x-axis raised to the exponent
	 */
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
