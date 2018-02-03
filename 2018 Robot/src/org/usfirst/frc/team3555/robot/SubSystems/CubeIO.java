package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.MotorGroup;

import edu.wpi.first.wpilibj.Servo;

public class CubeIO extends SubSystem {
	private CurvedJoystick joystick;
	private MotorGroup motors;
	private Servo releaseServo;
	
	/**
	 * The intake spins two sets of wheels to pull in the power cube
	 * Servo used to release the intake at the start of the match
	 * 
	 * @param joystick - Operator joystick to use the intake
	 */
	public CubeIO(CurvedJoystick joystick) {
		this.joystick = joystick;
		
		motors = new MotorGroup(0, 0);//TODO Get ID #s
		releaseServo = new Servo(0);//TODO Get channel #
	}

	@Override
	public void teleopUpdate() {
		if(joystick.getRawButtonReleased(0))//TODO Decide a button for this
			release();
		
		//Spin the motors with the knob on the bottom of the joystick
		motors.set(joystick.capDeadzone(joystick.getZ() / 2.0, .75));
	}
	
	private void release() {
		releaseServo.set(0);//TODO get the required position
	}
}
