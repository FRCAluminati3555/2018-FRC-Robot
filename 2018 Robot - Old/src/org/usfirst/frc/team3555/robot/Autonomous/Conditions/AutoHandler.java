package org.usfirst.frc.team3555.robot.Autonomous.Conditions;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.SubSystems.Handler;

import edu.wpi.first.wpilibj.DriverStation;

public class AutoHandler {
	private LAuto lAuto;
	private RAuto rAuto;
	
	public AutoHandler(Handler handler, ActionQueue actionQueue) {
		lAuto = new LAuto(handler, actionQueue);
		rAuto = new RAuto(handler, actionQueue);
	}
	
	public void handle() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
		
		if(gameData.equalsIgnoreCase("L")) 
			lAuto.handleCondition();
		else if(gameData.equalsIgnoreCase("R")) 
			rAuto.handleCondition();
	}
}
