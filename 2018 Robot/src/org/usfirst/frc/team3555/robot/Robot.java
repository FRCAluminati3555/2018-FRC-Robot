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
import org.usfirst.frc.team3555.robot.SubSystems.Lift.Positions;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	private AutoHandler autoHandler;
	private Handler handler;
	private ActionQueue actions;
	private ActionQueue initActions;
	
	/** TODO
	 * Establish buttons for pretty much everything
	 * 
	 * PID Values on the lift -> High Priority
	 * PID Values on the climber -> Low Priority
	 * PID Values on the drive train -> REALLY High Priority
	 */
	@Override
	public void robotInit() {
		
		CameraServer.getInstance().startAutomaticCapture();
		
		handler = new Handler();
		actions = new ActionQueue();
		initActions = new ActionQueue();
		autoHandler = new AutoHandler(handler, actions);
	}
	
	@Override
	public void autonomousInit() {
		actions.clear();
		initActions.clear();
		
		handler.getCubeIO().clear();
		handler.getLift().clear();
		handler.getDriveTrain().clear();
		
//		actions.add(handler.getDriveTrain().turnLeftOnDimeDegrees(90, 3));
		
		//Distribute actions according to the game data
//		actions.add(handler.getDriveTrain().setBrake());
		autoHandler.handle();
		initActions.add(handler.getLift().genPositionAction(Positions.Switch));

//		actions.add(handler.getDriveTrain().setCoast());
	}
	
	@Override
	public void autonomousPeriodic() {
		actions.update();
		initActions.update();
	}

	@Override
	public void teleopPeriodic() {
		handler.teleopUpdate();//Get controller input
	}
}
