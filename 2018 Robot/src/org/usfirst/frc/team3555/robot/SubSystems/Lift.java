package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.Autonomous.Action;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CANTalon;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift extends SubSystem {
	public static enum Positions {
		Intake(-.5), Switch(-27), Exchange(-4);//, Switch1Box(-26), Switch2Boxes(0);
		
		private double value;
		private Positions(double value) {
			this.value = value;
		}
		public double getValue() { return value; }
	}
	
	private CurvedJoystick joyOp;
	private CANTalon lift;
	
//	private double intakePos, switchPos, switchOneBoxPos, switchTwoBoxPos; 
	
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
	public Lift(Handler handler) {
		super(handler);
		this.joyOp = handler.getJoyOp();
		
		lift = new CANTalon(45);
		
		lift.setControlMode(ControlMode.PercentOutput);
		lift.setFeedbackDevice(FeedbackDevice.CTRE_MagEncoder_Relative);
		lift.setSensorPosition(0);
		lift.setSensorUnitsPerRotation(1024);// 1024 CPR Quadrature Encoder
		lift.setDistancePerRotation(4.5);//INCHES
//		lift.setPIDF(.6, 0, 15, 0);//TODO Get PIDF values
		lift.setPIDF(.05, 0, 5, 0);
		lift.setBrake();
		
		lift.enable();
		//Set up Limit Switches
//		lift.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, CANTalon.kTimeoutMs);
//		lift.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, CANTalon.kTimeoutMs);
		
//		lift.enableLimitSwitch(true); 
	}
	
	public void clear() {
		lift.set(0);
	}
	
	@Override
	public void teleopUpdate() {
		if(joyOp.getRawButton(4)) { //If a button is held, then move the lift by the input -> Prevents accidental movements
			if(lift.getControlMode() != ControlMode.PercentOutput)
				lift.setControlMode(ControlMode.PercentOutput);
			
			SmartDashboard.putNumber("Lift Percent: ", joyOp.getY());
			lift.set(-joyOp.capDeadzone(joyOp.getY(), .1));
			
		} else if(joyOp.getRawButtonReleased(12)) {//we're going into position mode and input from operator is required
			setTarget(Positions.Intake);
		} else if(joyOp.getRawButtonReleased(8)) {
			setTarget(Positions.Switch);
		} else if(joyOp.getRawButtonReleased(10)) {
			setTarget(Positions.Exchange);
		} 
//		} else if(joyOp.getRawButtonReleased(12)) {
//			setTarget(Positions.Switch1Box);
//		} else if(joyOp.getRawButtonReleased(11)) {
//			setTarget(Positions.Switch2Boxes);
//		}
		
		else if(lift.getControlMode() == ControlMode.PercentOutput)//We're in percent mode, but there is no input so set to 0
			lift.set(0);//Don't move
		
		SmartDashboard.putNumber("Inches Of Travel, Lift: ", lift.getPositionLinearDistance());
		SmartDashboard.putBoolean("Lift Percent: ", lift.getControlMode() == ControlMode.PercentOutput);
	}
	
	private void setTarget(Positions position ) {
		if(lift.getControlMode() != ControlMode.Position) 
			lift.setControlMode(ControlMode.Position);
		
		lift.setPositionDistance(position.getValue());
	}
	
//	/**
//	 * Generate an initialization action object to find the origin. 
//	 * This would be used in auto to make sure the lift is calibrated correctly
//	 * 
//	 * @return -> The action that will instruct the lift to find its origin
//	 */
//	public Action initOrigin() {
//		return new Action(() -> {
//			//Drive it down to the bottom
//			lift.setControlMode(ControlMode.PercentOutput);
//			lift.set(-.2);
//		}, (startTime) -> {
//			if(!lift.getSensorCollection().isRevLimitSwitchClosed()) {//Check whether or not we are at the bottom
//				lift.set(0);//Stop The motor
//				return true;
//			}
//			return false;
//		}, () -> {
//			//Set this position to be the origin
//			lift.setSensorPosition(0);
//			lift.setControlMode(ControlMode.Position);//Back to position mode
//		});
//	}
	
	public Action genPositionAction(Positions position) {
		return new Action(() -> {
			//Drive it down to the bottom
			setTarget(position);
		}, (startTime) -> {
			if(System.currentTimeMillis() > startTime + 2000)
				return true;
			return false;
		}, () -> {
			
		}); 
	}
}
