package org.usfirst.frc.team3555.robot.SubSystems;

/**
 * This class represents a general representation of a subsystem on the robot.
 * This essentially states that every subsystem will contain a reference to the handler class (which contains all other subsystems),
 * and that they all have a method that will be called during teleop that must be overridden. 
 * 
 * @author Sam S.
 */
public abstract class SubSystem {
	protected Handler handler;//Hold reference to all other subsystems
	
	/**
	 * 
	 * @param handler -> The handler object that holds reference to all other subsystems
	 */
	public SubSystem(Handler handler) { 
		this.handler = handler;
	}
	
	/**
	 * Update the state of the subsystem during teleop
	 */
	public abstract void teleopUpdate();
}
