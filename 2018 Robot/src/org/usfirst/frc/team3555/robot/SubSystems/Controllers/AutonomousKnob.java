package org.usfirst.frc.team3555.robot.SubSystems.Controllers;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * This represents the spikey knob on the front of the robot that allows us to change the position of the robot before the match starts
 * 
 * @author Sam
 */
public class AutonomousKnob {
	private AnalogInput knob;
	
	public AutonomousKnob() {
		knob = new AnalogInput(0);
	}
	
	public double getRawValue() {
		return knob.getVoltage();
	}
	
	/**
	 * This will return a value between 0 and 3 that represent what position the robot is in
	 * Right(0), RightOfExchange(1), LeftOfExchange(2), Left(3)
	 */
	public int getValue() {
		int value = (int) knob.getVoltage();
		
		if(value > 2)//The physical switch jumps a volt, subtract 1 to bring it back down
			value --;
		
		return value;
	}
}
