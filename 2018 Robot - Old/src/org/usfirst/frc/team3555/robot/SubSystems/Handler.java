package org.usfirst.frc.team3555.robot.SubSystems;

import org.usfirst.frc.team3555.robot.SubSystems.Controllers.AutonomousKnob;
import org.usfirst.frc.team3555.robot.SubSystems.Controllers.CurvedJoystick;

public class Handler {
	private DriveTrain driveTrain;
	private Climber climber;
	private CubeIO cubeIO;
	private CubeLift cubeLift;
	
	private AutonomousKnob autoKnob;
	private CurvedJoystick joyOp;
	
	public Handler() {
		autoKnob = new AutonomousKnob();
		joyOp = new CurvedJoystick(0);
		
		driveTrain = new DriveTrain(this);
		climber = new Climber(this);
		cubeIO = new CubeIO(this);
		cubeLift = new CubeLift(this);
	}
	
	public void teleopUpdate() {
		driveTrain.teleopUpdate();
		climber.teleopUpdate();
		cubeIO.teleopUpdate();
		cubeLift.teleopUpdate();
	}
	
	public DriveTrain getDriveTrain() { return driveTrain; }
	public Climber getClimber() { return climber; }
	public CubeIO getCubeIO() { return cubeIO; }
	public CubeLift getCubeLift() { return cubeLift; }
	
	public AutonomousKnob getAutoKnob() { return autoKnob; }
	public CurvedJoystick getJoyOp() { return joyOp; } 
}