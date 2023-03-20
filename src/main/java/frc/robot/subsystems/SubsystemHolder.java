package frc.robot.subsystems;

import java.util.List;

public class SubsystemHolder {


	private DriveSubsystem drive;
	private VisionSubsystem vision;
	private NavXSubSystem navx;
	private ArmSubsystem arm;
	private ElbowSubsystem elbow;
	private GripperSubsystem gripper;
	private LEDSubsystem led;
	private BrakeSubsystem brake;

	public SubsystemHolder(DriveSubsystem drive, NavXSubSystem navx, VisionSubsystem vision, ArmSubsystem arm, ElbowSubsystem elbow,GripperSubsystem gripper, LEDSubsystem led, BrakeSubsystem brake) {
		this.drive = drive;
		this.navx = navx;
		this.vision = vision;
		this.arm = arm;
		this.elbow = elbow;
		this.gripper = gripper;
		this.led = led;
		this.brake = brake;
	}
	
	public List<EntechSubsystem> asList(){
		return List.of(drive,vision,navx,arm,elbow,gripper,brake);
	}
	public DriveSubsystem getDrive() {
		return drive;
	}

	public VisionSubsystem getVision() {
		return vision;
	}

	public NavXSubSystem getNavx() {
		return navx;
	}

	public ArmSubsystem getArm() {
		return arm;
	}

	public ElbowSubsystem getElbow() {
		return elbow;
	}

	public GripperSubsystem getGripper() {
		return gripper;
	}	
	
	public LEDSubsystem getLED() {
		return led;
	}

	public BrakeSubsystem getBrake() {
		return brake;
	}
}
