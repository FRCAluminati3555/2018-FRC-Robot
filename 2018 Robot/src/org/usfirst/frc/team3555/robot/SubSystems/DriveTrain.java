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
		controller = new CurvedXboxController(1);
		
		joyLeft = new CurvedJoystick(2);
		joyRight = new CurvedJoystick(3);
		
		mainGroup = new MotorGroup(43, 44);
		slaves = new MotorGroup(41, 42);

//		leftRear = new CANTalon(43);
//		leftFront = new CANTalon(41); 
//		
//		rightRear = new CANTalon(44);
//		rightFront = new CANTalon(42);
		
		mainGroup.setControlMode(ControlMode.PercentOutput);
		mainGroup.setFeedBackDevice(FeedbackDevice.QuadEncoder);

		mainGroup.setLeftPIDF(.85, 0.01, .2, 0);
		mainGroup.setRightPIDF(.85, 0.01, .2, 0);
		
		mainGroup.negateRightSetPoint(true);
		mainGroup.setScaleFactorMinimum(.3);
		
		slaves.setControlMode(ControlMode.Follower);
		slaves.set(mainGroup.getLeftController().getDeviceID(), mainGroup.getRightController().getDeviceID());// Follow the main controllers
		
		mainGroup.setEnabled(true);
		slaves.setEnabled(true);
		
		wheelRadius = 0.0762;
		wheelCircumference = 2 * Math.PI * wheelRadius;
		distanceBetweenWheels = .51;
	}
	
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
		double forward = 0;
		double backward = 0;
		
		forward = controller.getCurvedTrigger(Hand.kRight);
		backward = controller.getCurvedTrigger(Hand.kLeft);
		
//		forward = curveData(controller.getTriggerAxis(Hand.kRight));//Get the position of the triggers and curve it accordingly
//		backward = curveData(controller.getTriggerAxis(Hand.kLeft));
		
		forward -= backward;

		double rightForward = forward;
		double leftForward = forward;
		
		double rightTurn = 0;
		double leftTurn = 0;
		
		double stickValue = controller.capDeadzone((controller.getRawX(Hand.kLeft) / 2.0));//curveData(controller.getX(Hand.kRight));
		if(stickValue > 0) {
			rightTurn = stickValue;
			rightForward *= 1 - rightTurn;
		} else if(stickValue < 0) {
			leftTurn = stickValue * -1;
			leftForward *= 1 - leftTurn;
		}
		
		if(forward < 0) {
			rightTurn *= -1;
			leftTurn *=-1;
		}
			
		System.out.println(forward);
		
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
	
	private void swapFront() {
		mainGroup.swap();
		mainGroup.invert();
	}
}
