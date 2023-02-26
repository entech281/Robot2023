package frc.robot.pose;

import java.util.Optional;

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
public class PhotonVisionPoseEstimator implements PoseEstimator{

	@Override
	public Optional<Pose2d> estimateRobotPose(VisionStatus vs, NavxStatus ns, DriveStatus ds) {
		Optional<Pose3d> p3d = vs.getPhotonEstimatedPose();
		
		if ( p3d.isPresent()) {
			return Optional.of(p3d.get().toPose2d());
		}
		else {
			return Optional.empty();
		}
	}

}
