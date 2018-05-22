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
import org.usfirst.frc.team3555.robot.SubSystems.CubeLift.Positions;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Aluminati 3555
 * FRC 2018
 * 
 * This is the main robot class that will hold all of the methods that the field will call upon throughout the match. 
 * This class extends the IterativeRobot class. In essence this means that the robot will update its components in a periodic manner. 
 * This project organizes the parts of the robot in different subsystems that each interpret joystick input and contain autonomous methods. 
 * Additionally, to simplify this main class, different portions of code are encapsulated in different classes. 
 * For example, the Handler class contains the references to all of the subsystems on the robot. This allows any component to reference another, if need be. 
 * Also, the interpretation of the game data is all handled in the AutoHandler class. 
 * The purpose of all of these different classes is to segregate all of the code into shorter organized portions.    
 * 
 * @author Sam S.
 */
public class Robot extends IterativeRobot {
	private AutoHandler autoHandler;//Class that will handle the auto game data and fill up a queue with actions
	private Handler handler;//Hold references to all subsystems
	private ActionQueue actions;//Actions that will be executed depending on game data and robot position
	private ActionQueue initActions;//Actions that initialize the robot's components
	
	/** TODO
	 * Clean Up
	 * 	- Auto code needs docs and could be more straight forward
	 *  - Clear out the drive train class of all of the extra methods
	 *  - Auto Code should work regardless if the game data is null
	 */
	
	/**
	 * This method will start the camera stream to the driver station, generate all of the subsystems, and create the queues for autonomous.
	 * This will only be called once when the code is first uploaded.
	 */
	@Override
	public void robotInit() {
		//Start the camera
		CameraServer.getInstance().startAutomaticCapture();
		
		handler = new Handler();//Create all of the subsystems
		actions = new ActionQueue();//Construct queues
		initActions = new ActionQueue();
		autoHandler = new AutoHandler(handler, actions);
	}
	
	/**
	 * This method is called at the start of autonomous and initializes the state of the robot. 
	 * By this, it will make sure all of the queues are empty and all of the motors are set to not move. This will ensure that the robot's subsystem start the same way every time.
	 * Once everything is cleared, the autonomous handler will fill up the queue with actions that suffice its starting position and switch ownership.
	 * Also, under any condition, the lift will be brought up to the switch position.
	 * Here, two different queues are used, the actions queue and the initActions. 
	 * InitActions is used for initializing anything that will be done regardless of any game data or switch ownership.
	 * The actions queue is will be filled with the corresponding actions of switch ownership and robot position.
	 */
	@Override
	public void autonomousInit() {
		//Clear out any actions from a previous autonomous run
		actions.clear();
		initActions.clear();
		
		//Make sure everything starts at 0
		handler.getCubeIO().clear(); 
		handler.getCubeLift().clear();
		handler.getDriveTrain().clear();
		
		//Distribute actions according to the game data
//		autoHandler.handle();
		initActions.add(handler.getCubeLift().genPositionAction(Positions.Switch));
	}
	
	/**
	 * This method is called periodically when autonomous is enabled.
	 * It is here that the queues will perform their actions.
	 * As they finish actions, they will be removed from their list until nothing is left or time runs out.
	 */
	@Override
	public void autonomousPeriodic() {
		//The update method will have the robot execute the top action or move to the next when the current action has been completed
		actions.update();
		initActions.update();
	}

	/**
	 * This method is called periodically when teleop is enabled.
	 * It is here that the joystick inputs will be interpreted in all the different subsystems
	 */
	@Override
	public void teleopPeriodic() {
		handler.teleopUpdate();
	}
}