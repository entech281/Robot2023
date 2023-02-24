package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import static frc.robot.util.PoseUtil.inchesToMeters;

public class AlignmentCalculator {

	public static final double NOT_FOUND = 999;
	
	public double calculateAngleToScoringLocation(ScoringLocation loc, Pose2d estimatedRobotPose) {
		Pose2d scoringLocation = inchesToMeters(loc.computeAbsolutePose());
		Pose2d rotated = new Pose2d(scoringLocation.getX(), scoringLocation.getY(), scoringLocation.getRotation().rotateBy(Rotation2d.fromDegrees(180)));
		return estimatedRobotPose.minus(rotated).getRotation().getDegrees();

	}	

}
