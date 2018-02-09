package org.usfirst.frc.team3555.robot.Autonomous.Conditions;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.SubSystems.Handler;

public abstract class AutonomousCondition {
	public static enum Position {
		Left(0), Center(1), Right(2);
		
		public static Position getPosition(int value) {
			for(Position pos : Position.values()) 
				if(pos.getValue() == value) return pos;
			return null;
		}
		
		private int value;
		private Position(int value) {
			this.value = value;
		}
		
		public int getValue() { return value; }
	}
	
	protected Handler handler;
	protected ActionQueue actionQueue;
	
	public AutonomousCondition(Handler handler, ActionQueue actionQueue) {
		this.handler = handler;
		this.actionQueue = actionQueue;
	}
	
	public void handleCondition() {
		Position position = Position.getPosition(handler.getAutoKnob().getValue());
		
		if(position == Position.Left)
			handleLeftCondition();
		else if(position == Position.Center) 
			handleCenterCondition();
		else if(position == Position.Right)
			handleRightCondition();
	}
	
	protected abstract void handleLeftCondition(); 
	protected abstract void handleCenterCondition(); 
	protected abstract void handleRightCondition(); 
}
