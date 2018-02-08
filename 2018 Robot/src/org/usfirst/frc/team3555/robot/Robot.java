/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3555.robot;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.SubSystems.DriveTrain;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	private DriveTrain drive;
//	private Lift lift;

	private ActionQueue actions;
	
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		
		drive = new DriveTrain();
		actions = new ActionQueue();
	}

	@Override
	public void autonomousInit() {
		//Add actions to the queue here
	}

	@Override
	public void autonomousPeriodic() {
		actions.update();
	}

	@Override
	public void teleopInit() {
		drive.teleopUpdate();
//		lift.teleopUpdate();
	}
	
	@Override
	public void teleopPeriodic() {
		drive.teleopUpdate();
//		lift.teleopUpdate();
	}
}
