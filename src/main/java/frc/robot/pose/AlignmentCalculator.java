package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AlignmentCalculator {

	public double calculateAngleToScoringLocation(Pose2d scoringLocation, Pose2d estimatedRobotPose) {
		Translation2d t = estimatedRobotPose.minus(scoringLocation).getTranslation();
		double robotToScoringLocationAngle = Units.radiansToDegrees(Math.atan2(t.getY(), t.getX()));

		double fieldAbsoluteTargetAngle = scoringLocation.getRotation().rotateBy(Rotation2d.fromDegrees(180)).getDegrees();

		SmartDashboard.putNumber("robotToScoringLocationAngle", robotToScoringLocationAngle);
		SmartDashboard.putNumber("fieldAbsoluteTargetAngle", fieldAbsoluteTargetAngle);
		SmartDashboard.putNumber("t-getY()", t.getY());
		SmartDashboard.putNumber("t-getX()",  t.getX());
		SmartDashboard.putNumber("sl-getX()",  scoringLocation.getX());
		SmartDashboard.putNumber("sl-getY()",  scoringLocation.getY());
		SmartDashboard.putNumber("pose-getX()",  estimatedRobotPose.getX());
		SmartDashboard.putNumber("pose-getY()",  estimatedRobotPose.getY());
		return fieldAbsoluteTargetAngle + robotToScoringLocationAngle;
	}
}
