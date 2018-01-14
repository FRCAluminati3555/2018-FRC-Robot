package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.MotorGroup;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class DriveTrain {
	private MotorGroup mainGroup;//Front
	private MotorGroup slaves;//Back 
	
	public DriveTrain() {
		mainGroup = new MotorGroup(0, 1);
		slaves = new MotorGroup(2, 3);

		mainGroup.setControlMode(ControlMode.PercentOutput);
		mainGroup.setFeedBackDevice(FeedbackDevice.QuadEncoder);
		mainGroup.setLeftPID(0, 0, 0);
		mainGroup.setRightPID(0, 0, 0);
		
		mainGroup.setScaleFactorMinimum(.3);
		
		slaves.setControlMode(ControlMode.Follower);
		slaves.set(0, 1);// Follow the main controllers
		
		mainGroup.setEnabled(true);
		slaves.setEnabled(true);
	}
	
	public void teleopUpdate() {
		mainGroup.update();
		slaves.update();
	}
}
