package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;

public interface PoseEstimator {

	public Pose2d estimateRobotPose ( VisionStatus vs, NavxStatus ns, DriveStatus ds);
}
