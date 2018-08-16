package org.usfirst.frc.team3555.robot.Autonomous.Conditions;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.SubSystems.Handler;

public class RAuto extends AutonomousCondition {

	public RAuto(Handler handler, ActionQueue actionQueue) {
		super(handler, actionQueue);

	}

	@Override
	protected void handleLeftOfExchange() {
//		//forward 105.2 inches (2.67208 meters)
//		justForward();
		
//		Drag turn 25.71 degrees to the right
//		Travel 116 inches forward
//		Drag turn 25.71 degrees to the left (quick adjustment. Doesn't need to be precise. Just for squaring up to the switch)
//		Outtake cube
		
		actionQueue.add(handler.getDriveTrain().turnRightDegrees(26, 1));
		actionQueue.add(handler.getDriveTrain().drive(2.9, 2.5));
		actionQueue.add(handler.getDriveTrain().turnLeftDegrees(26, 1));
		actionQueue.add(handler.getCubeIO().pushOut(3));
	}

	@Override
	protected void handleLeft() {
		//forward 105.2 inches (2.67208 meters)
		justForward();
	}

	@Override
	protected void handleRightOfExchange() {
		//forward 105.2 inches (2.67208 meters)
		//eject cube
		justForward();
		actionQueue.add(handler.getCubeIO().pushOut(3));
	}

	@Override
	protected void handleRight() {
		//forward 153.85 inches (3.90779 meters)
		actionQueue.add(handler.getDriveTrain().drive(3.85, 3));
		//dime 90 left
		actionQueue.add(handler.getDriveTrain().turnLeftOnDimeDegrees(90, 1.5));
		//forward 54 inches (1.3716 meters)
		actionQueue.add(handler.getDriveTrain().drive(.5, .75));
		//eject cube
		actionQueue.add(handler.getCubeIO().pushOut(3));
	}
}