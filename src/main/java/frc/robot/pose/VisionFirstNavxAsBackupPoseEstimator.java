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
    
  private final Transform3d ROBOT_TO_CAM = new Transform3d( 
		  new Translation3d( 
				  RobotConstants.VISION.CAMERA_POSITION.FORWARD_OF_CENTER_METETRS,
				  RobotConstants.VISION.CAMERA_POSITION.LEFT_OF_CENTER_METERS,
				  RobotConstants.VISION.CAMERA_POSITION.UP_METERS), new Rotation3d(0,0,0));
	@Override
	public Pose2d estimateRobotPose(VisionStatus vs, NavxStatus ns, DriveStatus ds) {
    	if ( vs.hasBestTarget() ) {
    		return estimatePoseFromCamera(vs);
    	}
    	else {
    		return estimatePoseFromNavx(ns);
    	}
	}
    
	//this is where you take the Transform3d provided by
	//the vision system, and the tag it's based on, to compute 
    private Pose2d estimatePoseFromCamera(VisionStatus vs) {
    	
    	RecognizedAprilTagTarget target = vs.getBestAprilTagTarget();
    	Transform3d cameraToTarget =  target.getCameraToTargetTransform();
    	AprilTagLocation tagLocation = target.getTagLocation();
    	
    	Pose3d tagLocationPose = tagLocation.asPose3d();
    	
    	//robotPose + ROBOT_TO_CAM + cameraToTarget = tagLocation
    	//thus 
    	//robotPose = tagLocation - ROBOT_TO_CAM - cameraToTarget
    	
    	//watch out for meters to inches, (use PoseUtil if needed to convert)
    	//HINT:: something like this should work:
    	
    	//this might not be exactly right. lets do some tests! 
    	Pose3d  estimatedPose = tagLocationPose.transformBy(ROBOT_TO_CAM.inverse()).transformBy(cameraToTarget.inverse());
    	return estimatedPose.toPose2d();
    	
    }
    
    private Pose2d estimatePoseFromNavx(NavxStatus ns) {
    	return new Pose2d(0,0,Rotation2d.fromDegrees(ns.getYawAngleDegrees()));
    }


}
