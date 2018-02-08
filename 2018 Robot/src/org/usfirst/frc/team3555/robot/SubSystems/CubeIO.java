package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.Autonomous.Action;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

public class CubeIO extends SubSystem {
	private CurvedJoystick joystick;
	private Servo releaseServo;
	
	private Talon talonLeft, talonRight;
	
	/**
	 * The intake spins two sets of wheels to pull in the power cube
	 * Servo used to release the intake at the start of the match
	 * 
	 * @param joystick - Operator joystick to use the intake
	 */
	public CubeIO(CurvedJoystick joystick) {
		this.joystick = joystick;
		
		releaseServo = new Servo(0);//TODO Get channel #
		talonLeft = new Talon(0);
		talonRight = new Talon(1);
	}

	@Override
	public void teleopUpdate() {
		if(joystick.getRawButtonReleased(0))//TODO Decide a button for this
			release();//Does this need to be user input?
		
		//Spin the motors with the knob on the bottom of the joystick
		double speed = joystick.capDeadzone(joystick.getZ() / 2.0, .75);
		talonLeft.set(speed);//TODO Which is negative?
		talonRight.set(-speed);
	}
	
	/**
	 * Generate an action to release the cube wheels with the servo
	 * @return -> An action that will release the cube wheels
	 */
	public Action releaseAction() {
		return new Action(() -> {
			release();//Move to the angle that releases the wheels
		}, (startTime) -> {
			if(System.currentTimeMillis() > startTime + 2000)//Wait until they are down
				return true;
			return false;
		}, () -> {
			
		});
	}
	
	private void release() {
		releaseServo.set(1);//TODO get the required position
	}
}
