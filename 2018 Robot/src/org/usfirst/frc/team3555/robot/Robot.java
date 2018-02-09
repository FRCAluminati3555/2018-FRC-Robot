/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3555.robot;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.Autonomous.Conditions.AutoHandler;
import org.usfirst.frc.team3555.robot.SubSystems.Handler;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	private AutoHandler autoHandler;
	private Handler handler;
	private ActionQueue actions;
	
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		
		handler = new Handler();
		actions = new ActionQueue();
		
		autoHandler = new AutoHandler(handler, actions);
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
		actions.update();
	}

	@Override
	public void teleopPeriodic() {
		handler.teleopUpdate();
	}
}
