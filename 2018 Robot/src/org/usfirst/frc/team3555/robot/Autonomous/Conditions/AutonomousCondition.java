package org.usfirst.frc.team3555.robot.Autonomous.Conditions;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.SubSystems.Handler;

public abstract class AutonomousCondition {
	public static enum Position {
		Right(0), RightOfExchange(1), LeftOfExchange(2), Left(3);
		
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
			handleLeft();
		else if(position == Position.LeftOfExchange)
			handleLeftOfExchange();
		else if(position == Position.Right)
			handleRight();
		else if(position == Position.RightOfExchange)
			handleRightOfExchange();
	}
	
	protected void justForward() {
		actionQueue.add(handler.getDriveTrain().drive(2.6, 3));
	}
	
	protected abstract void handleLeftOfExchange();
	protected abstract void handleLeft();
	
	protected abstract void handleRightOfExchange();
	protected abstract void handleRight();
}
