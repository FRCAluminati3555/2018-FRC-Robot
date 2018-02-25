package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CANTalon;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends SubSystem {
	public static enum Positions {//TODO Get These Values
		Top(20), Bottom(0);
		
		private double value;
		private Positions(double value) {
			this.value = value;
		}
		public double getValue() { return value; }
	}
	
	private CurvedJoystick joyOp;
	private CANTalon climber;
	
	/**
	 * ? Bottom out limit switch 
	 * Encoder on the motor -> CTRE Mag Encoder
	 */
	public Climber(Handler handler) {
		super(handler);
		joyOp = handler.getJoyOp();
		
		climber = new CANTalon(46);
		
//		climber.setFeedbackDevice(FeedbackDevice.QuadEncoder);
//		climber.setSensorUnitsPerRotation(500);//TODO Get this value from the encoder
//		climber.setDistancePerRotation(0.05);//INCHES
//		climber.setSensorPosition(0);
//		climber.setControlMode(ControlMode.Position);
		
//		climber.setPID(0.0, 0.0, 0.0);//TODO Get these values
		
		climber.enable();
	}

	@Override
	public void teleopUpdate() {
		if(joyOp.getRawButton(3)) { //If a button is held, then move the climber by the input -> Prevents accidental movements
//			if(climber.getControlMode() != ControlMode.PercentOutput)
//				climber.setControlMode(ControlMode.PercentOutput);
			double speed = joyOp.capDeadzone(joyOp.getY());
			climber.set(speed);
			SmartDashboard.putNumber("Climber Percent Output: ", speed);
		} 
		
//		else if(joyOp.getRawButtonReleased(11)) //we're going into position mode and input from operator is required
//			setTarget(Positions.Bottom);
//		else if(joyOp.getRawButtonReleased(9)) 
//			setTarget(Positions.Top);
		
		else { //if(climber.getControlMode() == ControlMode.PercentOutput) { //We're in percent mode, but there is no input so set to 0
			climber.set(0);//Don't move
			SmartDashboard.putNumber("Climber Percent Output: ", 0.0);
		}
		
//		SmartDashboard.putNumber("Inches Of Travel, Lift: ", climber.getPositionLinearDistance());
	}
	
//	private void setTarget(Positions position) {
//		if(climber.getControlMode() != ControlMode.Position) 
//			climber.setControlMode(ControlMode.Position);
//		
//		climber.setPositionDistance(position.getValue());
//	}
	
//	/**
//	 * Generate an initialization action object to find the origin. 
//	 * This would be used in auto to make sure the lift is calibrated correctly
//	 * 
//	 * @return -> The action that will instruct the lift to find its origin
//	 */
//	public Action initOrigin() {
//		return new Action(() -> {
//			//Drive it down to the bottom
//			climber.setControlMode(ControlMode.PercentOutput);
//			climber.set(-.2);
//		}, (startTime) -> {
//			if(!climber.getSensorCollection().isRevLimitSwitchClosed()) {//Check whether or not we are at the bottom
//				climber.set(0);//Stop The motor
//				return true;
//			}
//			return false;
//		}, () -> {
//			//Set this position to be the origin
//			climber.setSensorPosition(0);
//			climber.setControlMode(ControlMode.Position);//Back to position mode
//		});
//	}
}
