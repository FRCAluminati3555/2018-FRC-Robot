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
	
	public int getValue() {
		int value = (int) knob.getVoltage();
		
		if(value > 2)
			value --;
		
		return value;
	}
}
