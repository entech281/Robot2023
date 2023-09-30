package frc.robot.pose;
import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.utils.PoseUtil;


public class TestAlignmentCalculator {

	protected AlignmentCalculator calculator = new AlignmentCalculator(0, 0);
	public static final double TOLERANCE_DEGREES=1.0;
	
	
	protected Pose2d computePoseOffset ( Pose2d original, double x, double y) {
		return new Pose2d(
				original.getX()+x, 
				original.getY()+y, 
				original.getRotation().rotateBy(Rotation2d.fromDegrees(180))
				);
	}
	
	
	public void testZeroAngleFacingaTagDirectly() {
		
		ScoringLocation sloc = new ScoringLocation(AprilTagLocation.RED_MIDDLE,TargetNode.A2);
		Pose2d targetPose = sloc.computeAbsolutePose();

		//right in front of the tag
		Pose2d robotPose = computePoseOffset(targetPose, -24, 0);
		
		//must convert this to meters
		Pose2d robotPoseInMeters = PoseUtil.inchesToMeters(robotPose);
		double turnAngle = calculator.calculateAngleToScoringLocation(sloc.computeAbsolutePose(), robotPoseInMeters);
		assertEquals( 0, turnAngle, TOLERANCE_DEGREES);		
	}
	
	
	public void testPositiveAngleFromTag() {

		ScoringLocation sloc = new ScoringLocation(AprilTagLocation.RED_MIDDLE,TargetNode.A2);
		Pose2d targetPose = sloc.computeAbsolutePose();

		//48 inches left of the tag, 10 inches up. 
		//angle should be atan( 10/48 ) = 11.76 degrees
		Pose2d robotPose = computePoseOffset(targetPose, -48, 10); 
		
		//must convert this to meters
		Pose2d robotPoseInMeters = PoseUtil.inchesToMeters(robotPose);
		double turnAngle = calculator.calculateAngleToScoringLocation(sloc.computeAbsolutePose(), robotPoseInMeters);
		assertEquals( Units.radiansToDegrees(Math.atan(10/48)), turnAngle, TOLERANCE_DEGREES);			
		
	}

	
	public void testNegativeAngleFromTag() {
		
		ScoringLocation sloc = new ScoringLocation(AprilTagLocation.RED_MIDDLE,TargetNode.A2);
		Pose2d targetPose = sloc.computeAbsolutePose();

		//48 inches left of the tag, 10 inches up. 
		//angle should be atan( 10/48 ) = 11.76 degrees
		Pose2d robotPose = computePoseOffset(targetPose, -48, -10); 
		
		//must convert this to meters
		Pose2d robotPoseInMeters = PoseUtil.inchesToMeters(robotPose);
		double turnAngle = calculator.calculateAngleToScoringLocation(sloc.computeAbsolutePose(), robotPoseInMeters);
		assertEquals( -Units.radiansToDegrees(Math.atan(10/48)), turnAngle, TOLERANCE_DEGREES);		
		
	}
	
}