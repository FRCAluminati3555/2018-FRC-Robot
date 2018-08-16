package org.usfirst.frc.team3555.robot.Autonomous.Conditions;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.SubSystems.Handler;

import edu.wpi.first.wpilibj.DriverStation;

public class AutoHandler {
	private LAuto lAuto;//planned actions for when the switch is owned on the left side
	private RAuto rAuto;//planned actions for when the switch is owned on the right side
	
	/**
	 * This will handle distributing the game information accordingly when autonomous starts. 
	 * Meaning that it will break down the field information and react according to pre-planned actions.
	 * The constructor will build these plans, and have them ready for when auto starts.
	 * 
	 * @param handler -> Reference to all components on the robot
	 * @param actionQueue -> The queue that must be filled
	 */
	public AutoHandler(Handler handler, ActionQueue actionQueue) {
		lAuto = new LAuto(handler, actionQueue);
		rAuto = new RAuto(handler, actionQueue);
	}
	
	/**
	 * This should be called at the start of auto. This will take the game information and fill a queue with pre-planned actions.
	 */
	public void handle() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
		
		if(gameData.equalsIgnoreCase("L")) 
			lAuto.handleCondition();
		else if(gameData.equalsIgnoreCase("R")) 
			rAuto.handleCondition();
	}
}
