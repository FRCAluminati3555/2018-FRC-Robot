package org.usfirst.frc.team3555.robot.SubSystems.Controllers;

import edu.wpi.first.wpilibj.AnalogInput;

public class AutonomousKnob {
	private AnalogInput knob;
	
	public AutonomousKnob() {
		knob = new AnalogInput(0);
	}
	
	public double getRawValue() {
		return knob.getVoltage();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getValue() {
		int value = (int) knob.getVoltage();
		
		if(value > 2)//The physical switch jumps a volt, subtract 1 to bring it back down
			value --;
		
		return value;
	}
}
