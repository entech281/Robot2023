package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

public interface PoseEstimator {

	public Pose2d estimateRobotPose ( VisionStatus vs, NavxStatus ns, DriveStatus ds);
}
