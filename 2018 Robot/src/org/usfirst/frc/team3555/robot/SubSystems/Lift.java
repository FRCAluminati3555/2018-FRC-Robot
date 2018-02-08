package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.Autonomous.Action;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CANTalon;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;

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
		
		lift = new CANTalon(0);//TODO Get Id #
		
		lift.setControlMode(ControlMode.Position);
		lift.setFeedbackDevice(FeedbackDevice.CTRE_MagEncoder_Relative);
		lift.setSensorUnitsPerRotation(1024);// 1024 CPR Quadrature Encoder
		lift.setPIDF(0, 0, 0, 0);//TODO Get PIDF values
		
		//Set up Limit Switches
		lift.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, CANTalon.kTimeoutMs);
		lift.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, CANTalon.kTimeoutMs);
		
		lift.enableLimitSwitch(true); 
	}
	
	@Override
	public void teleopUpdate() {
		if(joyOp.getRawButton(3)) //If a button is held, then move the lift by the input -> Prevents accidental movements
			lift.set(joyOp.capDeadzone(joyOp.getY() / 2.0));
		else 
			lift.set(0);//Don't move
	}
	
	/**
	 * Generate an initialization action object to find the origin. 
	 * This would be used in auto to make sure the lift is calibrated correctly
	 * 
	 * @return -> The action that will instruct the lift to find its origin
	 */
	public Action initOrigin() {
		return new Action(() -> {
			//Drive it down to the bottom
			lift.setControlMode(ControlMode.PercentOutput);
			lift.set(-.2);
		}, (startTime) -> {
			if(lift.getSensorCollection().isRevLimitSwitchClosed()) {//Check whether or not we are at the bottom
				lift.set(0);//Stop The motor
				return true;
			}
			return false;
		}, () -> {
			//Set this position to be the origin
			lift.setSensorPosition(0);
			lift.setControlMode(ControlMode.Position);//Back to position mode
		});
	}
}
