package org.usfirst.frc.team3555.robot.Autonomous.Conditions;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.SubSystems.Handler;

import edu.wpi.first.wpilibj.DriverStation;

public class AutoHandler {
	private LLAuto llAuto;
	private RRAuto rrAuto;
	private RLAuto rlAuto;
	private LRAuto lrAuto;
	
	public AutoHandler(Handler handler, ActionQueue actionQueue) {
		llAuto = new LLAuto(handler, actionQueue);
		rrAuto = new RRAuto(handler, actionQueue);
		rlAuto = new RLAuto(handler, actionQueue);
		lrAuto = new LRAuto(handler, actionQueue);
	}
	
	public void handle() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage().substring(0, 2);
		
		if(gameData.equalsIgnoreCase("LR")) {
			lrAuto.handleCondition();
		} else if(gameData.equalsIgnoreCase("RL")) {
			rlAuto.handleCondition();
		} else if(gameData.equalsIgnoreCase("RR")) {
			rrAuto.handleCondition();
		} else if(gameData.equalsIgnoreCase("LL")) {
			llAuto.handleCondition();
		}
	}
}
