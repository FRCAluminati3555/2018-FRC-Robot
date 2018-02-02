package org.usfirst.frc.team3555.robot.SubSystems.Controllers;

import edu.wpi.first.wpilibj.XboxController;

public class CurvedXboxController extends XboxController {
	public static final double defaultExponent = 1.75;
	public static final double defaultDeadzone = .05;
	
	private double deadzone; 
	
	public CurvedXboxController(int port, double deadzone) {
		super(port);
		this.deadzone = deadzone;
	}

	public CurvedXboxController(int port) {
		this(port, defaultDeadzone);
	}
	
	public double getCurvedX(Hand hand) { return getCurvedX(hand, defaultExponent); }
	public double getCurvedX(Hand hand, double exponent) {
		return curveData(getX(hand), exponent);
	}
	
	public double getCurvedY(Hand hand) { return getCurvedY(hand, defaultExponent); }
	public double getCurvedY(Hand hand, double exponent) {
		return curveData(getY(hand), exponent);
	}
	
	public double getCurvedTrigger(Hand hand) { return getCurvedTrigger(hand, defaultExponent); }
	public double getCurvedTrigger(Hand hand, double exponent) { return curveData(getTriggerAxis(hand), exponent); }
	
	/**
	 * Get the raw -1 - 1 x value of the joystick
	 * 
	 * @return - The value of the x axis with a min of the deadzone
	 */
	public double getRawX(Hand hand) {
		return capDeadzone(getX(hand));
	}
	
	/**
	 * Get the raw -1 - 1 y value of the joystick
	 * 
	 * @return - The value of the y axis with a min of the deadzone
	 */
	public double getRawY(Hand hand) {
		return capDeadzone(getY(hand));
	}
	
	public double getRawTrigger(Hand hand) {
		return capDeadzone(getTriggerAxis(hand));
	}
	
	private double curveData(double data, double exponent) {
		return capDeadzone(data > 0 ? Math.pow(data, exponent) : -Math.pow(Math.abs(data), exponent));
	}
	
	public double capDeadzone(double data) {
		if(Math.abs(data) < deadzone)
			return 0;
		return data;
	}
}
