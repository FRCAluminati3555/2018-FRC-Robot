/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3555.robot;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.Autonomous.Conditions.AutoHandler;
import org.usfirst.frc.team3555.robot.Autonomous.Conditions.AutonomousCondition.Position;
import org.usfirst.frc.team3555.robot.SubSystems.Handler;
import org.usfirst.frc.team3555.robot.SubSystems.CubeLift.Positions;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private static final String LEFT = "Left";
	private static final String CENTER = "Center";
	private static final String CROSS_THE_LINE = "Cross The Line";
	private static final String RIGHT = "Right";
	
	private SendableChooser<String> autoChooser = new SendableChooser<>();
	
	private AutoHandler autoHandler;//Class that will handle the auto game data and fill up a queue with actions
	private Handler handler;//Hold references to all subsystems
	private ActionQueue actions;//Actions that will be executed depending on game data and robot position
	private ActionQueue initActions;//Actions that initialize the robot's components
	
//	private long startTime;//panick
	
	
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
		
		autoChooser.addDefault(LEFT, LEFT);
		autoChooser.addObject(CENTER, CENTER);
		autoChooser.addObject(CROSS_THE_LINE, CROSS_THE_LINE);
		autoChooser.addObject(RIGHT, RIGHT);
		
		SmartDashboard.putData("Robot Location", autoChooser);
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
		String ownership = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
		String selected = autoChooser.getSelected();
		
		initActions.add(handler.getCubeLift().genPositionAction(Positions.Switch));
		
		if(ownership.equals("L")) {
			switch(selected) {
				case LEFT:
					//153.85 inches forward (3.90779 meters)
					actions.add(handler.getDriveTrain().drive(3.85, 3));
					//Turn 90 right on a dime
					actions.add(handler.getDriveTrain().turnRightOnDimeDegrees(90, 1.5));
					//Drive forward 54 inches (1.3716 meters)
					actions.add(handler.getDriveTrain().drive(.5, .75));
					//Eject cube
					actions.add(handler.getCubeIO().pushOut(3));
					
					break;
					
				case RIGHT:
					actions.add(handler.getDriveTrain().drive(2.6, 3));
					
					break;
					
				case CROSS_THE_LINE:
					actions.add(handler.getDriveTrain().drive(2.4, 3));
					
					break;
					
				case CENTER:
					actions.add(handler.getDriveTrain().turnLeftDegrees(30.5, .5));
					actions.add(handler.getDriveTrain().drive(3.1, 3));
					actions.add(handler.getDriveTrain().turnRightDegrees(30.5, .5));
					
					actions.add(handler.getCubeIO().pushOut(3));
					
					break;
			}
		} else if(ownership.equals("R")) {
			switch(selected) {
				case LEFT: 
					actions.add(handler.getDriveTrain().drive(2.6, 3));
					
					break;
					
				case RIGHT:
					//forward 153.85 inches (3.90779 meters)
					actions.add(handler.getDriveTrain().drive(3.85, 3));
					//dime 90 left
					actions.add(handler.getDriveTrain().turnLeftOnDimeDegrees(90, 1.5));
					//forward 54 inches (1.3716 meters)
					actions.add(handler.getDriveTrain().drive(.5, .75));
					//eject cube
					actions.add(handler.getCubeIO().pushOut(3));
					
					break;
					
				case CROSS_THE_LINE:
					actions.add(handler.getDriveTrain().drive(2.6, 3));
					
//					actions.add(handler.getCubeIO().pushOut(3));
					
					break;
					
				case CENTER:
					actions.add(handler.getDriveTrain().turnRightDegrees(26, 1));
					actions.add(handler.getDriveTrain().drive(2.9, 2.5));
					actions.add(handler.getDriveTrain().turnLeftDegrees(26, 1));
					
					actions.add(handler.getCubeIO().pushOut(3));
					
					break;
			}
		}
		
//		autoHandler.handle();
//		initActions.add(handler.getCubeLift().genPositionAction(Positions.Switch));
		
//		startTime = System.currentTimeMillis();
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
		
//		if(System.currentTimeMillis() > startTime + 1500)
//			handler.getDriveTrain().getMainGroup().set(0);
//		else 
//			handler.getDriveTrain().getMainGroup().set(.5);
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