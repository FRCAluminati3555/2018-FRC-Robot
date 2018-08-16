package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CANTalon;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

/**
 * This class represents the climber on the back of the robot. 
 * This will use the operator joystick to apply a percent power to the motor. 
 * 
 * @author Sam S.
 */
public class Climber extends SubSystem {
	private CurvedJoystick joystick;// Joystick that controls the mechanism
	private CANTalon climber;//Talon that governs the motor, operates on port 46
	
	/**
	 * Creates a new Climber object that will use the operator joystick from the handler object.
	 * This will use the joystick to operate in percent mode to drive the motor up or down. 
	 * To use it, hold button 3 on the operator joystick (Logitech attack 3D) and move it along the y-axis
	 * 
	 * @param handler -> Handler object that holds references to all other sub-systems 
	 */
	public Climber(Handler handler) {
		super(handler);
		joystick = handler.getJoyOp();
		
		climber = new CANTalon(46);
		climber.enable();
	}

	/**
	 * This will apply power to the motor with a direct correspondence to the joystick input if button 3 is being held as well. 
	 */
	@Override
	public void teleopUpdate() {
		if(joystick.getRawButton(3)) 
			climber.set(joystick.capDeadzone(joystick.getY()));
		else 
			climber.set(0);//Don't move
	}
}
