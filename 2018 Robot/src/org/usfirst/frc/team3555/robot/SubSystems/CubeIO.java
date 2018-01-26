package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.MotorGroup;

public class CubeIO extends SubSystem {
	private CurvedJoystick joystick;
	private MotorGroup motors;
	
	/**
	 * The intake spins two sets of wheels to pull in the power cube
	 * 
	 * @param joystick - Operator joystick to use the intake
	 */
	public CubeIO(CurvedJoystick joystick) {
		this.joystick = joystick;
		
		motors = new MotorGroup(0, 0);
	}

	@Override
	public void teleopUpdate() {
		motors.set(joystick.capDeadzone(joystick.getZ() / 2.0, .75));
	}
}
