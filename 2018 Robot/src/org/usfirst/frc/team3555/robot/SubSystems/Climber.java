package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CANTalon;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;

public class Climber extends SubSystem {
	private CurvedJoystick joyOp;
	private CANTalon climber;
	
	/**
	 * ? Bottom out limit switch 
	 * Encoder on the motor -> CTRE Mag Encoder
	 */
	public Climber(CurvedJoystick joyOp) {
		this.joyOp = joyOp;
		
		climber = new CANTalon(0);
		
		climber.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		climber.setSensorUnitsPerRotation(500);//TODO Get this value from the encoder
		climber.setReverseLimitSwitchNormal(LimitSwitchNormal.NormallyOpen);//Which one?
		
		climber.enable();
	}

	@Override
	public void teleopUpdate() {
		if(joyOp.getRawButton(4)) 
			climber.set(joyOp.capDeadzone(joyOp.getY() / 2.0));
		else 
			climber.set(0);
	}
}
