package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.RobotConstants;

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
	
	public static Pose3d inchesToMeters ( Pose3d input) {
		return input.times(METERS_PER_INCH);
	}
	
	public static double inchesToMeters ( double inches) {
		return inches/METERS_PER_INCH;
	}
	public static double meters_to_inches ( double meters) {
		return meters*METERS_PER_INCH;
	}

	public static Transform3d robotToCameraTransfor3d() {
		return new Transform3d( 
				new Translation3d(RobotConstants.VISION.CAMERA_POSITION.FORWARD_OF_CENTER_INCHES,
								  RobotConstants.VISION.CAMERA_POSITION.LEFT_OF_CENTER_INCHES,
								  RobotConstants.VISION.CAMERA_POSITION.UP_INCHES
				),
				new Rotation3d(0,
							   RobotConstants.VISION.CAMERA_POSITION.CAMERA_PITCH_DEGREES,
							   RobotConstants.VISION.CAMERA_POSITION.CAMERA_YAW_DEGREES
				)
		);
	}
}
