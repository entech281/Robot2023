package frc.robot.pose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.RobotConstants;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;
public class TestVisionFirstNavxAsBackupPoseEstimator {

	public static double METERS_PER_INCH=0.0254;
	
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
		
		double TEST_OFFSET_INCHES = 24.0;
		//imagine we are sitting somewhere close to RED_MIDDLE,
		//and we get a position slightly in front of us 
		Transform3d cameraToTarget = new Transform3d(
				new Translation3d(TEST_OFFSET_INCHES*METERS_PER_INCH,0, 0 ),
				new Rotation3d(0,0,Math.toRadians(180))
		);
		
		vs.setBestTarget(
				new RecognizedAprilTagTarget(
						cameraToTarget,
						AprilTagLocation.RED_MIDDLE
				)				
		);
		
		//RED-middle = tag id 2 --> x,y = 610.77, 108.19 inches 
		double EXPECTED_X_METERS  = (610.77 - TEST_OFFSET_INCHES)*METERS_PER_INCH - (RobotConstants.VISION.CAMERA_POSITION.FORWARD_OF_CENTER_METERS) * METERS_PER_INCH;
		double EXPECTED_Y_METERS = 108.19 * METERS_PER_INCH;
		double EXPECTED_ROTATION_DEGRESS  = 0.0;
		Pose2d r = estimator.estimateRobotPose(vs, null, null);
		Pose2d EXPECTED = new Pose2d(EXPECTED_X_METERS,EXPECTED_Y_METERS ,Rotation2d.fromDegrees(EXPECTED_ROTATION_DEGRESS));
		
		
		//assertEquals(EXPECTED,r);

	}
}
