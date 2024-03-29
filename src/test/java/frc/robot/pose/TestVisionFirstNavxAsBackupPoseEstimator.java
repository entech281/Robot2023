package frc.robot.pose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.RobotConstants;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;
import frc.robot.util.PoseUtil;
public class TestVisionFirstNavxAsBackupPoseEstimator {

	public static double METERS_PER_INCH = 0.0254;
	
	protected VisionFirstNavxAsBackupPoseEstimator estimator = new VisionFirstNavxAsBackupPoseEstimator(false);
	public static final double TOLERANCE = 0.1;
	
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
		
		Pose2d r = estimator.estimateRobotPose(vs, ns, null).get();
		
		
		assertEquals(EXPECTED_ANGLE, r.getRotation().getDegrees(),TOLERANCE);
		assertEquals(ZERO_OFFSETS_EXPECTED_FOR_NOW,r.getX(),TOLERANCE);
		assertEquals(ZERO_OFFSETS_EXPECTED_FOR_NOW,r.getY(),TOLERANCE);
		
	}
	
	
	@Test
	public void testEstimatedPoseSeemsAboutRight() {
		
		VisionStatus vs = new VisionStatus();
        NavxStatus ns = new NavxStatus(0.0,0.0,0.0,0.0);
		
		/*
		 * Transform 3d:
		 * https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/geometry/Transform3d.html
		 * Translation 3d is x, y, z rotation
		 * Rotation 3d is rotation about the x, y, and z axes respectively-- IN RADIANS
		 */
		
		double TEST_OFFSET_METERS = 0.6096;
		//imagine we are sitting somewhere close to RED_MIDDLE,
		//and we get a position slightly in front of us 
		Transform3d cameraToTarget = PoseUtil.cameraToTarget(TEST_OFFSET_METERS,0,180);
		
		vs.setBestTarget(
				new RecognizedAprilTagTarget(
						cameraToTarget,
						AprilTagLocation.RED_MIDDLE, null
				)				
		);
		
		//RED-middle = tag id 2 --> x,y = 15.513558 , 2.748026 meters 
		double EXPECTED_X_METERS  = (15.513558 - TEST_OFFSET_METERS) - RobotConstants.VISION.CAMERA_POSITION.FORWARD_OF_CENTER_METERS;
		double EXPECTED_Y_METERS = 2.748026- RobotConstants.VISION.CAMERA_POSITION.LEFT_OF_CENTER_METERS;
		double EXPECTED_ROTATION_DEGREES  = 0.0;
		Pose2d r = estimator.estimateRobotPose(vs, ns, null).get();
		Pose2d EXPECTED = new Pose2d(EXPECTED_X_METERS,EXPECTED_Y_METERS ,Rotation2d.fromDegrees(EXPECTED_ROTATION_DEGREES));
		//assertEquals(EXPECTED_X_METERS,r.getX(),TOLERANCE);
		assertPose2dEquals(EXPECTED, r);

	}
}
