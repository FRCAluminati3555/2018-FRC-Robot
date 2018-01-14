package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CANTalon;

public class Lift {
	private CANTalon liftTalon;
	
	public Lift() {
		liftTalon = new CANTalon(0);
	}
	
	public void teleopUpdate() {
		liftTalon.update();
	}
}
