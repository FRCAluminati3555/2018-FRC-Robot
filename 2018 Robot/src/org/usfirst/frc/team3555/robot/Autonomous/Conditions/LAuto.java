package org.usfirst.frc.team3555.robot.Autonomous.Conditions;

import org.usfirst.frc.team3555.robot.Autonomous.ActionQueue;
import org.usfirst.frc.team3555.robot.SubSystems.Handler;

public class LAuto extends AutonomousCondition {

	public LAuto(Handler handler, ActionQueue actionQueue) {
		super(handler, actionQueue);
		
	}

	@Override
	protected void handleLeftOfExchange() {
		//forward 36 inches (0.9144 meters)
//		actionQueue.add(handler.getDriveTrain().drive(.9, 2));
//		//right 45 drag turn
//		actionQueue.add(handler.getDriveTrain().turnRightDegrees(45, 2));
//		//forward 33.057 inches (0.8396478 meters)
//		actionQueue.add(handler.getDriveTrain().drive(.835, 2));
//		//left 45 drag turn
//		actionQueue.add(handler.getDriveTrain().turnLeftDegrees(45, 2));
//		//forward 36.14 inches (0.917956 meters)
//		actionQueue.add(handler.getDriveTrain().drive(.8, 2));
		
		//One Turn 
		
		//Forawrd 38 inches (.965 meters)
		actionQueue.add(handler.getDriveTrain().drive(.95, 2));
		
		actionQueue.add(handler.getDriveTrain().turnRightDegrees(15, .75));
		
		actionQueue.add(handler.getDriveTrain().drive(1.727, 2));
		
		
		//eject cube
		actionQueue.add(handler.getCubeIO().pushOut(3));
	}

	@Override
	protected void handleLeft() {
		//153.85 inches forward (3.90779 meters)
		actionQueue.add(handler.getDriveTrain().drive(3.85, 3));
		//Turn 90 right on a dime
		actionQueue.add(handler.getDriveTrain().turnRightOnDimeDegrees(90, 1.5));
		//Drive forward 54 inches (1.3716 meters)
		actionQueue.add(handler.getDriveTrain().drive(.5, .75));
		//Eject cube
		actionQueue.add(handler.getCubeIO().pushOut(3));
	}

	@Override
	protected void handleRightOfExchange() {
		//drive 105.2 inches (2.67208 meters)
		actionQueue.add(handler.getDriveTrain().drive(2.4, 3));
	}

	@Override
	protected void handleRight() {
		//Drive forward 105.2 inches (2.67208 meters)
		justForward();
	}
}
