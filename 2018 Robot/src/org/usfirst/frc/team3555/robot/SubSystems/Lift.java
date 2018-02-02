package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CANTalon;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

public class Lift extends SubSystem {
	private CurvedJoystick joyOp;
	private CANTalon lift;
	
	/**
	 * One limit switch on the top
	 * One limit switch at the bottom
	 * Encoder on the motor
	 * 
	 * Init in auto to fin origin at the bottom sensor
	 * PID Position to go to positions -> relative to origin determined at in autonomous 
	 * 
	 * User input -> Tread carefully, the lift is incredibly fast
	 */
	public Lift(CurvedJoystick joyOp) {
		this.joyOp = joyOp;
		
		lift = new CANTalon(0);
	}
	
	@Override
	public void teleopUpdate() {
		if(joyOp.getRawButton(3)) 
			lift.set(joyOp.capDeadzone(joyOp.getY() / 2.0));
		else 
			lift.set(0);
	}
}
