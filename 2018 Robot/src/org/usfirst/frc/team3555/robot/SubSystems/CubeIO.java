package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.Autonomous.Action;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class represents the front intake of the robot. This contains 2 talons that control a set of wheels. 
 * This supports the use of the slider on the bottom of the logitech attack 3d joystick. 
 * So far, the autonomous code for this subsystem consists of a timed pull-in and push-out for a duration of time. 
 * There are no additional sensors.
 * 
 * The left talon is port 0.
 * The right talon is port 1.
 * 
 * @author Sam S.
 */
public class CubeIO extends SubSystem {
	private CurvedJoystick joystick;// Joystick that operates the subsystem
	private Talon talonLeft, talonRight;
	
	/**
	 * Initializes the two talons to  port 0 and 1. 
	 * This will use the curved joystick object in the handler class. 
	 * 
	 * @param handler - The handler object that holds references to the rest of the robots subsystems
	 */
	public CubeIO(Handler handler) {
		super(handler);
		this.joystick = handler.getJoyOp();
		
		talonLeft = new Talon(0);
		talonRight = new Talon(1);
	}

	/**
	 * Here, the state of the slider is queried and directly changes the power supply to the 2 motors.
	 * However, there is a deadzone on this input in order to prevent accidental usage of the intake mechanism.
	 * 
	 * The diagram here represents the input of the slider, where 0 is no voltage 
	 * 
	 * 
	 *  -   1   -
	 *  -  .85  -
	 *      0
	 *  -   0   -
	 *      0
	 *  - -.85  -
	 *  -  -1   -
	 */
	@Override
	public void teleopUpdate() {
		//Spin the motors with the knob on the bottom of the joystick
		double speed = joystick.capDeadzone(joystick.getThrottle(), .85);
		talonLeft.set(-speed);//Negate this side to get the same direction of output
		talonRight.set(speed);
		
		SmartDashboard.putNumber("IO Speed: ", speed);// Print status to the dashboard
	}
	
	/**
	 * This will shut off both motors. 
	 * This is typically called at the start of auto to make sure everything is off
	 */
	public void clear() {
		talonLeft.set(0);
		talonRight.set(0);
	}
	
	/**
	 * This will generate an autonomous action that will run the motors forward for a duration of time. 
	 * This will result in any cube being pushed out from the robot. 
	 * 
	 * @param seconds - Amount of seconds to run the motors. 
	 * @return - The autonomous action object that will push out a cube for a duration of seconds. 
	 */
	public Action pushOut(double seconds) {
		return new Action(() -> {
			talonLeft.set(.5);// Start the motors at half power in the forward direction
			talonRight.set(-.5);
		}, startTime ->
			System.currentTimeMillis() > startTime + (seconds * 1000) // Check how long it has been
		, () -> {
			talonLeft.set(0);// Stop the motors
			talonRight.set(0);
		});
	}
	
	/**
	 * This will generate an autonomous action that will run the motors in reverse for a duration of time. 
	 * This will result in any cube being pulled into the robot. 
	 * 
	 * @param seconds - Amount of seconds to run the motors. 
	 * @return - The autonomous action object that will pull in a cube for a duration of seconds. 
	 */
	public Action pullIn(double seconds) {
		return new Action(() -> {
			talonLeft.set(-.5);// Start the motors at half power in reverse
			talonRight.set(.5);
		}, startTime ->
			System.currentTimeMillis() > startTime + (seconds * 1000) // Check how long it has been
		, () -> {
			talonLeft.set(0);// Stop the motors
			talonRight.set(0);
		});
	}
}
