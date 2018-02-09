package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.AutonomousKnob;

public class Handler {
	private DriveTrain driveTrain;
	private CubeIO cubeIO;
	private Climber climber;
	private Lift lift;
	
	private AutonomousKnob autoKnob;
	
	public Handler() {
		driveTrain = new DriveTrain();
		autoKnob = new AutonomousKnob();
	}
	
	public void teleopUpdate() {
		driveTrain.teleopUpdate();
	}
	
	public DriveTrain getDriveTrain() { return driveTrain; }
	public AutonomousKnob getAutoKnob() { return autoKnob; }
}
