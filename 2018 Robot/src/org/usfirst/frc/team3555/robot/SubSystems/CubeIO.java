package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.Autonomous.Action;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CubeIO extends SubSystem {
	private CurvedJoystick joystick;
	private Talon talonLeft, talonRight;
	
	/**
	 * The intake spins two sets of wheels to pull in the power cube
	 * Servo used to release the intake at the start of the match
	 * 
	 * 14 amp draw
	 * 
	 * @param joystick - Operator joystick to use the intake
	 */
	public CubeIO(Handler handler) {
		super(handler);
		this.joystick = handler.getJoyOp();
		
		talonLeft = new Talon(0);
		talonRight = new Talon(1);
	}

	@Override
	public void teleopUpdate() {
		//Spin the motors with the knob on the bottom of the joystick
		double speed = joystick.capDeadzone(joystick.getThrottle() / 2.0, .4);
		talonLeft.set(-speed);//TODO Which is negative?
		talonRight.set(speed);
		
		SmartDashboard.putNumber("IO Speed: ", speed);
	}
	
	public void clear() {
		talonLeft.set(0);
		talonRight.set(0);
	}
	
	public Action pushOut(double seconds) {
		return new Action(() -> {
			talonLeft.set(.5);
			talonRight.set(-.5);
		}, (startTime) -> {
			if(System.currentTimeMillis() > startTime + (seconds * 1000))
				return true;
			return false;
		}, () -> {
			talonLeft.set(0);
			talonRight.set(0);
		});
	}
}
