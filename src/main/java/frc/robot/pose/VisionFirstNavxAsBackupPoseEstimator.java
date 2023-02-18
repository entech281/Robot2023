package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.RobotConstants;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;

import edu.wpi.first.math.util.Units;

/**
 * Estimates the Pose of the Robot using Vision Data, and
 * navx as a backup if we do not have a target (in which case pose will have only
 * the yaw angle!
 * 
 * This is where we take the vision target selected and Transform3d, 
 * and use it to 
 * @author dcowden
 */
public class VisionFirstNavxAsBackupPoseEstimator implements PoseEstimator{

	
  public static double METERS_PER_INCH=0.0254;	
  private Transform3d ROBOT_TO_CAM = new Transform3d( 
		  new Translation3d( 
				  RobotConstants.VISION.CAMERA_POSITION.FORWARD_OF_CENTER_METETRS,
				  RobotConstants.VISION.CAMERA_POSITION.LEFT_OF_CENTER_METERS,
				  RobotConstants.VISION.CAMERA_POSITION.UP_METERS), new Rotation3d(0,0,0));
	@Override
	public Pose2d estimateRobotPose(VisionStatus vs, NavxStatus ns, DriveStatus ds) {
        Pose2d estPose = ns.getPose2d();
    	if ( vs.hasBestTarget() ) {
    		Pose2d camPose = estimatePoseFromCamera(vs);
            if (camPose != null) {
                estPose = new Pose2d(camPose.getX(), camPose.getY(), Rotation2d.fromDegrees(ns.getYawAngleDegrees()));
            }
        }
        return estPose;
	}
    
	//photon vision gives us transform in meters
	//also the Field2d widget is in meters
    private Pose2d estimatePoseFromCamera(VisionStatus vs) {
    	
    	RecognizedAprilTagTarget target = vs.getBestAprilTagTarget();
        if (target == null) {
            return null;
        }
    	Transform3d cameraToTarget =  target.getCameraToTargetTransform();
    	
    	AprilTagLocation tagLocation = target.getTagLocation();
    	
    	Pose3d tagLocationPose = tagLocation.asPose3d();
    	Pose3d tagLocationMeters = tagLocationPose.times(METERS_PER_INCH);
    	
    	
    	//watch out for meters to inches, (use PoseUtil if needed to convert)
    	//HINT:: something like this should work:
    	
    	//robotPose -> robotToCamera -> cameraToTag = tagLocation
    	//thus tagLocation -> tagToCamera -> cameraToRobot = robotPose
    	Pose3d  estimatedPose = tagLocationMeters.transformBy(cameraToTarget.inverse()).transformBy(ROBOT_TO_CAM.inverse());    	
    	
    	return estimatedPose.toPose2d();
    }

}
