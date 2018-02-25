package org.usfirst.frc.team3555.robot.SubSystems;

public abstract class SubSystem {
	protected Handler handler;
	
	public SubSystem(Handler handler) { 
		this.handler = handler;
	}
	
	public abstract void teleopUpdate();
}
