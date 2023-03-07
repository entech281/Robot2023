package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;


public class AlignmentCalculator {

	private double elbowAngle;
    private double telescopeLength;

	public AlignmentCalculator( double elbowAngle, double telescopeLength) {
        this.elbowAngle = elbowAngle;
        this.telescopeLength = telescopeLength;
    }

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

	public AlignmentCalculator armPoseCalculator( TargetNode target) {
		if (target.is2ndRow()) {
			double mastDistanceToMidNode = 41.7;
			double telescopeLength = Math.hypot(RobotConstants.MAST.MAST_DISTANCE_OVER_MID_NODE, mastDistanceToMidNode);
			double elbowAngle = Math.atan2(mastDistanceToMidNode, RobotConstants.MAST.MAST_DISTANCE_OVER_MID_NODE);
        	return new AlignmentCalculator(elbowAngle, telescopeLength);
		} else if(target.isThirdRow()){
			double telescopeLength = 54.33071;
			double elbowAngle = 100;
			return new AlignmentCalculator(elbowAngle, telescopeLength);
		} else {
			double mastDistanceToGround = 45.38;
			double robotMiddleDistanceToGround = 20;
			double telescopeLength = Math.hypot(robotMiddleDistanceToGround, mastDistanceToGround);
			double elbowAngle = Math.atan2(mastDistanceToGround, RobotConstants.MAST.MAST_DISTANCE_OVER_MID_NODE);
			return new AlignmentCalculator(elbowAngle, telescopeLength);
		}
	}

    public double getElbowAngle() {
        return elbowAngle;
    }

    public double getTelescopeLength() {
        return telescopeLength;
    }

}
