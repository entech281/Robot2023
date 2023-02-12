package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.pose.AlignmentSolution;
import frc.robot.pose.ArmStatus;
import frc.robot.pose.DriveStatus;
import frc.robot.pose.NavxStatus;
import frc.robot.pose.VisionStatus;

public class CommandContext {

	public Pose2d getEstimatedRobotPose() {
		return estimatedRobotPose;
	}
	public void setEstimatedRobotPose(Pose2d estimatedRobotPose) {
		this.estimatedRobotPose = estimatedRobotPose;
	}
	public NavxStatus getNavxStatus() {
		return navxStatus;
	}
	public void setNavxStatus(NavxStatus navxStatus) {
		this.navxStatus = navxStatus;
	}
	public ArmStatus getArmStatus() {
		return armStatus;
	}
	public void setArmStatus(ArmStatus armStatus) {
		this.armStatus = armStatus;
	}
	public VisionStatus getVisionStatus() {
		return visionStatus;
	}
	public void setVisionStatus(VisionStatus visionStatus) {
		this.visionStatus = visionStatus;
	}
	public AlignmentSolution getAlignmentSolution() {
		return alignmentSolution;
	}
	public void setAlignmentSolution(AlignmentSolution alignmentSolution) {
		this.alignmentSolution = alignmentSolution;
	}
	private Pose2d estimatedRobotPose;
	private NavxStatus navxStatus;
	private ArmStatus armStatus;
	private VisionStatus visionStatus;
	private DriveStatus driveStatus;
	
	public DriveStatus getDriveStatus() {
		return driveStatus;
	}
	public void setDriveStatus(DriveStatus driveStatus) {
		this.driveStatus = driveStatus;
	}
	private AlignmentSolution alignmentSolution;
}
