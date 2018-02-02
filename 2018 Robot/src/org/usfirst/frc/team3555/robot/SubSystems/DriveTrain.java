package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedXboxController;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.MotorGroup;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class DriveTrain extends SubSystem {
	private MotorGroup mainGroup;//Back
	private MotorGroup slaves;//Front 
	
	private CurvedXboxController controller;
	private CurvedJoystick joyLeft, joyRight;
	
	//Data
	private double wheelCircumference; // <- meters
	private double wheelRadius; // <- meters
	private double distanceBetweenWheels; // <- meters
	
	public DriveTrain() {
		//Input init
		controller = new CurvedXboxController(1);
//		joyLeft = new CurvedJoystick(2);
//		joyRight = new CurvedJoystick(3);
		
		//Init groups to hold the talons
		mainGroup = new MotorGroup(43, 44);
		slaves = new MotorGroup(41, 42);
		
//		leftRear = new CANTalon(43);
//		leftFront = new CANTalon(41); 
//		
//		rightRear = new CANTalon(44);
//		rightFront = new CANTalon(42);
		
		mainGroup.setControlMode(ControlMode.PercentOutput);
		mainGroup.setFeedBackDevice(FeedbackDevice.QuadEncoder);
		
		mainGroup.setSensorUnitsPerRotation(360);

		//Set PID constants
		mainGroup.setLeftPIDF(.85, 0.01, .2, 0);
		mainGroup.setRightPIDF(.85, 0.01, .2, 0);
		
		//Make sure that the right side has the same positive direction as the left side
		mainGroup.negateRightSetPoint(true);
		mainGroup.setScaleFactorMinimum(.3);//Scale factor to slow down the speed when more precise movemnt is desired
		
		//Follow the main group
		slaves.setControlMode(ControlMode.Follower);
		slaves.set(mainGroup.getLeftController().getDeviceID(), mainGroup.getRightController().getDeviceID());// Follow the main controllers
		
		//Enable the drives
		mainGroup.setEnabled(true);
		slaves.setEnabled(true);
		
		//Math Data for auto
		wheelRadius = 0.0762;
		wheelCircumference = 2 * Math.PI * wheelRadius;
		distanceBetweenWheels = .51;
	}
	
	/**
	 * Interpret the driver input, and make sure the slave drives are up to date with their master controllers
	 */
	@Override
	public void teleopUpdate() {
		interpretController();
		
//		mainGroup.update();
		slaves.update();
	}
	
	/**
	 * Interpret the controller input to how it would be in a video game
	 */
	private void interpretController() {
		if(controller.getBButtonReleased())
			swapFront();
		
		double forward = 0;
		double backward = 0;
		
//		forward = controller.getCurvedTrigger(Hand.kRight);
//		backward = controller.getCurvedTrigger(Hand.kLeft);
		
		forward = controller.getRawTrigger(Hand.kRight);
		backward = controller.getRawTrigger(Hand.kLeft);
		
		forward -= backward;//Total Forward Power

		//Separate into the two sides
		double rightForward = forward;
		double leftForward = forward;
		
		//Turn Power
		double rightTurn = 0;
		double leftTurn = 0;
		
		double stickValue = controller.getRawX(Hand.kLeft) / 2.0;
		if(stickValue > 0) {
			rightTurn = stickValue;
			rightForward *= 1 - rightTurn;
//			rightForward *= 1 - (rightTurn * 1.5);
		} else if(stickValue < 0) {
			leftTurn = stickValue * -1;
			leftForward *= 1 - leftTurn;
//			leftForward *= 1 - (leftTurn * 1.5);
		}
		
		//Reverse 
		if(forward < 0) {
			rightTurn *= -1;
			leftTurn *=-1;
		}
			
//		System.out.println(forward);
		
		//Apply power
		mainGroup.set(leftForward + rightTurn, rightForward + leftTurn);
	}
	
	/**
	 * Control Robot with a single joystick in an arcade fashion
	 */
	private void interpretArcadeDrive() {
		double leftSpeed = 0;
    	double rightSpeed = 0;
    	
    	leftSpeed = joyRight.getCurvedY() + joyRight.getCurvedY();
    	rightSpeed = -joyRight.getCurvedY() + joyRight.getCurvedX();

    	mainGroup.set(leftSpeed, rightSpeed);
	}
	
	/**
	 * Control Robot with both joysticks in a tank fashion
	 */
	private void interpretTankDrive() {
		double leftSpeed = 0;
    	double rightSpeed = 0;
    	
    	leftSpeed = joyLeft.getCurvedY();
		rightSpeed = joyRight.getCurvedY();
    	
		mainGroup.set(leftSpeed, rightSpeed);
	}
	
	/**
	 * Swap which direction is forward
	 * This is used for when the robot is turned around, so that the driver can change the forward direction and not think about how to drive backwards
	 */
	private void swapFront() {
		mainGroup.swap();
		mainGroup.invert();
		slaves.swap();
		slaves.invert();
	}
}
