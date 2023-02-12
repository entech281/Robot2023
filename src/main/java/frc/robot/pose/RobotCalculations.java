
package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

/**
 *
 * @author dcowden
 */
public class RobotCalculations implements Sendable {

    public Pose2d getEstimatedRobotPose() {
		return estimatedRobotPose;
	}


	public void setEstimatedRobotPose(Pose2d estimatedRobotPose) {
		this.estimatedRobotPose = estimatedRobotPose;
	}


	public AlignmentSolution getAlignmentSolution() {
		return alignmentSolution;
	}


	public void setAlignmentSolution(AlignmentSolution alignmentSolution) {
		this.alignmentSolution = alignmentSolution;
	}


	public VisionStatus getVisionStatus() {
		return visionStatus;
	}


	public void setVisionStatus(VisionStatus visionStatus) {
		this.visionStatus = visionStatus;
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


	public DriveStatus getDriveStatus() {
		return driveStatus;
	}


	public void setDriveStatus(DriveStatus driveStatus) {
		this.driveStatus = driveStatus;
	}


	private Pose2d estimatedRobotPose;
    private AlignmentSolution alignmentSolution;
    private VisionStatus visionStatus;
    private NavxStatus navxStatus;
    private ArmStatus armStatus;
    private DriveStatus driveStatus;
    

    @Override
    public void initSendable(SendableBuilder sb) {
    	//TODO::
    }

}
