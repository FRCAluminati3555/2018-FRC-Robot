package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.AutonomousKnob;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedXboxController;

public class Handler {
	private DriveTrain driveTrain;
	private Climber climber;
	private CubeIO cubeIO;
	private Lift lift;
	
	private CurvedXboxController controller;
	private AutonomousKnob autoKnob;
	private CurvedJoystick joyOp;
	
	public Handler() {
//		controller = new CurvedXboxController(1);
		autoKnob = new AutonomousKnob();
		joyOp = new CurvedJoystick(0);
		
		driveTrain = new DriveTrain(this);
		climber = new Climber(this);
		cubeIO = new CubeIO(this);
		lift = new Lift(this);
	}
	
	public void teleopUpdate() {
		driveTrain.teleopUpdate();
		climber.teleopUpdate();
		cubeIO.teleopUpdate();
		lift.teleopUpdate();
	}
	
	public DriveTrain getDriveTrain() { return driveTrain; }
	public Climber getClimber() { return climber; }
	public CubeIO getCubeIO() { return cubeIO; }
	public Lift getLift() { return lift; }
	
//	public CurvedXboxController getController() { return controller; }
	public AutonomousKnob getAutoKnob() { return autoKnob; }
	public CurvedJoystick getJoyOp() { return joyOp; } 
	
}
