package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.Autonomous.Action;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CANTalon;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class represents the cube lift on the front of the robot.
 * Essentially, this subsystem will either take input from the buttons on the side of the operator joystick for different positions, 
 * or manual control in the event that the encoder disconnects during the match.
 * 
 * Autonomous consists of actions that will bring the lift to a certain position
 * 
 * @author Sam S.
 */
public class CubeLift extends SubSystem {
	
	/**
	 * This enumeration represents the different heights of the lift. 
	 * These are expressed in terms of inches from the bottom of the lift. 
	 * 
	 * (These are negative because of the way that the motor is oriented) 
	 * 
	 * @author Sam S.
	 */
	public static enum Positions {
		Intake(-.5), Switch(-28), Exchange(-8);
		
		private double value;
		private Positions(double value) {
			this.value = value;
		}
		public double getValue() { return value; }
	}
	
	private CurvedJoystick joystick;// Joystick that operates the mechanism
	private CANTalon lift;// Motor Controller for the subsystem, operates on ID : 45

	/**
	 * This represents the Cube lift on the front of the elevator. 
	 * Here, there is an assumption that the lift will always be at the bottom at the start of the match. This will be used as the origin for distances. 
	 * 
	 * The input of this class will use the operator joystick from the handler class. 
	 * 
	 * This will also create a new CANTalon object on port 45. The talon is equipped with a CTRE Mag Encoder at 1024 Codes Per Revolution, 
	 * with 4.5 inches of travel for every rotation of the motor. 
	 * 
	 * @param handler - Handler object that hold references to all other subsystems on the robot
	 */
	public CubeLift(Handler handler) {
		super(handler);
		this.joystick = handler.getJoyOp();
		
		lift = new CANTalon(45);
		
		lift.setControlMode(ControlMode.PercentOutput);// Start in percent mode
		lift.setFeedbackDevice(FeedbackDevice.CTRE_MagEncoder_Relative);// This means that the encoder will behave like a quadrature encoder, rather than the 1 rotation absolute mode that is more like a potentionmeter
		lift.setSensorPosition(0);
		lift.setSensorUnitsPerRotation(1024);// 1024 CPR Quadrature Encoder
		lift.setDistancePerRotation(4.5);//INCHES
//		lift.setPIDF(.6, 0, 15, 0);
		lift.setPIDF(.05, 0, 5, 0);
		lift.setBrake();
		
		lift.enable();
	}
	
	/**
	 * This will listen for input from the operator joystick. 
	 * 
	 * From a front end percpective, when one of the buttons (bindings below) is pressed, the talon will use the encoder to drive to that corresponding position.
	 * At any point, the manual mode (binding below) can interupt this autonomy in case communication with the encoder is lost. 
	 * 
	 * The manual mode controls such that all the way forward is full power, and backwards is full power in the reverse direction. 
	 * 
	 * Button 12 :  The Intake Position   : .5 inches from the bottom
	 * Button 10 : The Exchange Position  :  8 inches from the bottom
	 * Button 8  :  The Switch Position   : 28 inches from the bottom
	 * 
	 * Button 4  : Manual Mode 
	 */
	@Override
	public void teleopUpdate() {
		if(joystick.getRawButton(4)) {// If this button is held, then move the lift by the input of the joystick (manual mode) -> Prevents accidental movements
			if(lift.getControlMode() != ControlMode.PercentOutput)
				lift.setControlMode(ControlMode.PercentOutput);
			
			SmartDashboard.putNumber("Lift Percent: ", joystick.getY());
			lift.set(-joystick.capDeadzone(joystick.getY(), .1));
		} else 
		if(joystick.getRawButtonReleased(12)) //Ask if any of the buttons were just released, if so set the set point to that position
			setTarget(Positions.Intake);
		else if(joystick.getRawButtonReleased(8)) 
			setTarget(Positions.Switch);
		else if(joystick.getRawButtonReleased(10)) 
			setTarget(Positions.Exchange);
		
		else if(lift.getControlMode() == ControlMode.PercentOutput)//We're in percent mode, but there is no input so set to 0
			lift.set(0);//Don't move
		
		// Print information to the dashboard 
		SmartDashboard.putNumber("Inches Of Travel, Lift: ", lift.getPositionLinearDistance());
		SmartDashboard.putBoolean("Manual Control: ", lift.getControlMode() == ControlMode.PercentOutput);
	}
	
	/**
	 * This function will set the target position of the lift. 
	 * This is to be used during tele-operation because it will make sure that the talon is in position mode then set the target.
	 * 
	 * @param position -> Position enumeration that represents the target height of the lift 
	 */
	private void setTarget(Positions position) {
		if(lift.getControlMode() != ControlMode.MotionMagic) {
			lift.setControlMode(ControlMode.MotionMagic);
			
			lift.configMotionAcceleration(1000);
			lift.configMotionCruiseVelocity(1);
		}
		
		lift.setPositionDistance(position.getValue());
	}
	
	
	/**
	 * Make sure the the lift is being supplied no power
	 */
	public void clear() {
		lift.set(0);
	}
	
	/**
	 * This will generate an autonomous Action that will drive the lift to the specified height. 
	 * Note: This will assume that in 2 seconds the lift will have made it to it's target. In the future, an absolute completion will be implemented to accurately complete this action
	 * 
	 * @param position -> Position enumeration object that represents the target height for the lift
	 * @return -> Autonomous action object that will drive the lift to the specified height.
	 */
	public Action genPositionAction(Positions position) {
		return new Action(() -> {
			setTarget(position);// Set the target position
		}, startTime -> 
			System.currentTimeMillis() > startTime + 2000
		, () -> {
			//The talon will take care of itself -> when it is at the position it will maintain it
		}); 
	}
}
