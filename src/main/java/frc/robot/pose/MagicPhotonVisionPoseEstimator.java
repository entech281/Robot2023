package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;

/**
 * this estimator  uses only PhotonVision's pose estimate.
 * @author davec
 *
 */
public class MagicPhotonVisionPoseEstimator implements PoseEstimator{

	@Override
	public Pose2d estimateRobotPose(VisionStatus vs, NavxStatus ns, DriveStatus ds) {
		Pose3d p3d = vs.getPhotonEstimatedPose();
		return p3d.toPose2d();
	}

}
