package org.usfirst.frc.team3555.robot.Autonomous.InnerType;

@FunctionalInterface
public interface ActionUpdate {
	/**
	 * 
	 * @param startTime -> The time that this method was started at
	 * @return -> whether or not is complete
	 */
	public abstract boolean update(long startTime);
}
