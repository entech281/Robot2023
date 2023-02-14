package frc.robot.pose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;
public class TestVisionFirstNavxAsBackupPoseEstimator {

	public static double METERS_TO_INCHES=0.0254;
	
	protected VisionFirstNavxAsBackupPoseEstimator estimator = new VisionFirstNavxAsBackupPoseEstimator();
	public static final double TOLERANCE=0.1;
	
	protected void assertPose2dEquals( Pose2d expected, Pose2d actual) {
		assertEquals(expected.getX(), actual.getX(),TOLERANCE);
		assertEquals(expected.getY(), actual.getY(),TOLERANCE);
		assertEquals(expected.getRotation().getDegrees(), actual.getRotation().getDegrees(),TOLERANCE);
	}
	
	@Test
	public void testHavingNoVisionBestTargetPullsNavxAngle() {
		
		final int EXPECTED_ANGLE = 200;
		VisionStatus vs = new VisionStatus();
		NavxStatus ns = new NavxStatus();
		
		ns.setYawAngleDegrees(EXPECTED_ANGLE);
		
		double ZERO_OFFSETS_EXPECTED_FOR_NOW = 0.0;
		
		Pose2d r = estimator.estimateRobotPose(vs, ns, null);
		
		
		assertEquals(EXPECTED_ANGLE, r.getRotation().getDegrees(),TOLERANCE);
		assertEquals(ZERO_OFFSETS_EXPECTED_FOR_NOW,r.getX(),TOLERANCE);
		assertEquals(ZERO_OFFSETS_EXPECTED_FOR_NOW,r.getY(),TOLERANCE);
		
	}
	
	
	@Test
	public void testEstimatedPoseSeemsAboutRight() {
		
		VisionStatus vs = new VisionStatus();
		
		/*
		 * Transform 3d:
		 * https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/geometry/Transform3d.html
		 * Translation 3d is x, y, z rotation
		 * Rotation 3d is rotation about the x, y, and z axes respectively-- IN RADIANS
		 */
		
		
		//imagine we are sitting somewhere close to RED_MIDDLE,
		//and we get a position slightly in front of us 
		Transform3d cameraToTarget = new Transform3d(
				new Translation3d(24,0, 0 ),
				new Rotation3d(0,0,Math.toRadians(180))
		);
		vs.setBestTarget(
				new RecognizedAprilTagTarget(
						cameraToTarget,
						AprilTagLocation.RED_MIDDLE
				)				
		);
		
		//what is our estimated pose in that case?
		//facing a red target, angle should be zero,
		//we should be about 1/3 of the field in the y direciton, and
		//in INCHES
		Pose2d r = estimator.estimateRobotPose(vs, null, null);
		Pose2d EXPECTED = new Pose2d(587.07,108.39 ,Rotation2d.fromDegrees(0));
		
		assertPose2dEquals(EXPECTED,r);
	}
}
