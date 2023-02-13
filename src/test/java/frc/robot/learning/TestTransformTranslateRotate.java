package frc.robot.learning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class TestTransformTranslateRotate {

	
	public static final Pose3d ZERO = new Pose3d (new Translation3d(0,0,0), new Rotation3d(0,0,0) );
	public static final double TOLERANCE=0.1;
	
	protected Pose3d createPose3d ( double x, double y, double z, double rx, double ry, double rz) {
		return new Pose3d(new Translation3d(x,y,z), new Rotation3d(rx, ry, rz));
	}
	
	protected void assertPose3dEquals( Pose3d expected, Pose3d actual) {
		assertEquals(expected.getX(), actual.getX(),TOLERANCE);
		assertEquals(expected.getY(), actual.getY(),TOLERANCE);
		assertEquals(expected.getZ(), actual.getZ(),TOLERANCE);
		assertEquals(expected.getRotation().getX(), actual.getRotation().getX(),TOLERANCE);
		assertEquals(expected.getRotation().getY(), actual.getRotation().getY(),TOLERANCE);
		assertEquals(expected.getRotation().getZ(), actual.getRotation().getZ(),TOLERANCE);
	}	
	
	@Test
	public void testassertPose3EqualsWorks() {
		assertPose3dEquals(ZERO,ZERO);
	}
	
	@Test
	public void testBasicTranslateTransform() {
		Pose3d initial = ZERO;
		
		Transform3d t = new Transform3d( new Translation3d( 1, 2, 3 ), new Rotation3d(0,0,0));
		
		Pose3d moved = initial.transformBy(t);
		assertPose3dEquals ( createPose3d(1,2,3,0,0,0), moved);
		
		Pose3d movedBack = moved.transformBy(t.inverse());
		assertPose3dEquals ( ZERO, movedBack);
	}
}
