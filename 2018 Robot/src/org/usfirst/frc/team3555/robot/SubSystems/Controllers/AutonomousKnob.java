package org.usfirst.frc.team3555.robot.SubSystems.Controllers;

import edu.wpi.first.wpilibj.AnalogInput;

public class AutonomousKnob {
	private AnalogInput knob;
	
	public AutonomousKnob() {
		knob = new AnalogInput(0);//TODO get knob analog channel
	}
	
	public int getValue() {
		double value = knob.getVoltage();
		
		if(value < 1)
			return 0;
		if(value < 2)
			return 1;
		if(value < 3)
			return 2;
		if(value < 5)//Jumps in voltage, which is why this class was made to handle this condition in the back end
			return 3;
		return 0;
	}
}
