package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.VISION;
/**
 *
 * @author dcowden
 */
public class PoseUtil {
    public static Pose2d poseFromDoubles ( double xMeters, double yMeters, double angleDegrees){
        return new Pose2d ( new Translation2d(xMeters,yMeters), Rotation2d.fromDegrees(angleDegrees));
    }


	public static Transform3d robotToCameraTransform3d() {
		return new Transform3d( 
				new Translation3d(VISION.CAMERA_POSITION.FORWARD_OF_CENTER_METERS,
						RobotConstants.VISION.CAMERA_POSITION.LEFT_OF_CENTER_METERS,
								RobotConstants.VISION.CAMERA_POSITION.UP_METERS
				),
				new Rotation3d(0,
							   Units.degreesToRadians(RobotConstants.VISION.CAMERA_POSITION.CAMERA_PITCH_DEGREES),
							   Units.degreesToRadians(RobotConstants.VISION.CAMERA_POSITION.CAMERA_YAW_DEGREES)
				)
		);
	}
	
	public static Transform3d cameraToTarget (double xMeters, double yMeters, double degrees) {
		return new Transform3d(
				new Translation3d(xMeters,yMeters, 0 ),
				new Rotation3d(0,0,Math.toRadians(degrees)));		
	}
	
	public static Transform3d cameraToTargetDirectlyInFrontOfRobot () {
		return new Transform3d( 
				new Translation3d(VISION.CAMERA_POSITION.FORWARD_OF_CENTER_METERS,
						RobotConstants.VISION.CAMERA_POSITION.LEFT_OF_CENTER_METERS,
								RobotConstants.VISION.CAMERA_POSITION.UP_METERS
				),
				new Rotation3d(0,
							   Units.degreesToRadians(RobotConstants.VISION.CAMERA_POSITION.CAMERA_PITCH_DEGREES),
							   Units.degreesToRadians(RobotConstants.VISION.CAMERA_POSITION.CAMERA_YAW_DEGREES)
				)
		);
	}	
	
	public static Transform3d flipBy180Degrees3d() {
		return new Transform3d( new Translation3d(0,0,0), new Rotation3d(0,0,Math.toRadians(180)));		
	}
	public static Transform2d flipBy180Degrees2d() {
		return new Transform2d( new Translation2d(0,0), Rotation2d.fromDegrees(180));
	}
	
    public static Pose2d inchesToMeters(Pose2d inches ) {
    	return new Pose2d (Units.inchesToMeters(inches.getX()), Units.inchesToMeters(inches.getY()), inches.getRotation() );
    }	
	
}
