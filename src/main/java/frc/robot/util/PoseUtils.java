package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;

public class PoseUtils {

	public static final double METERS_PER_INCH = 0.0254;
	public static Pose2d inchesToMeters ( Pose2d input) {
		return new Pose2d( input.getTranslation().times(METERS_PER_INCH), input.getRotation());
	}
}
