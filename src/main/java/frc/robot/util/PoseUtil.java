package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

/**
 *
 * @author dcowden
 */
public class PoseUtil {
    public static Pose2d poseFromDoubles ( double xInches, double yInches, double angleDegrees){
        return new Pose2d ( new Translation2d(xInches,yInches), Rotation2d.fromDegrees(angleDegrees));
    }
    
	public static final double METERS_PER_INCH = 0.0254;
	public static Pose2d inchesToMeters ( Pose2d input) {
		return new Pose2d( input.getTranslation().times(METERS_PER_INCH), input.getRotation());
	}    
}
