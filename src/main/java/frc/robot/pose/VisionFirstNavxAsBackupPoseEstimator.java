package frc.robot.pose;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.subsystems.DriveStatus;
import frc.robot.subsystems.NavxStatus;
import frc.robot.subsystems.VisionStatus;
import frc.robot.utils.PoseUtil;

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

	
  private boolean overrideYawWithNavx = false;
  
  public VisionFirstNavxAsBackupPoseEstimator ( boolean overrideYawWithNavx) {
	  this.overrideYawWithNavx=overrideYawWithNavx;
  }
  
  private Transform3d ROBOT_TO_CAM = PoseUtil.robotToCameraTransform3d();
	@Override

	public Optional<Pose2d> estimateRobotPose(VisionStatus vs, NavxStatus ns, DriveStatus ds) {
        Pose2d navXPoseEstimate = ns.getPose2d();
        Optional<Pose2d> cameraPoseEstimate = estimatePoseFromCamera(vs);
        
        if ( cameraPoseEstimate.isPresent()) {
        	if ( overrideYawWithNavx) {
        		return Optional.of(overrideYawAngleInPose(cameraPoseEstimate.get(),ns.getYawAngleDegrees()));
        	}
        	else {
        		return cameraPoseEstimate;
        	}
        }
        else {
        	return Optional.of(navXPoseEstimate);
        }

	}

	private Pose2d overrideYawAngleInPose ( Pose2d originalPose, double newYawAngle) {
		return new Pose2d (originalPose.getX(), originalPose.getY(),  Rotation2d.fromDegrees(newYawAngle ));
	}
	
	//photon vision gives us transform in meters
	//also the Field2d widget is in meters
    private Optional<Pose2d> estimatePoseFromCamera(VisionStatus vs) {
    	
    	if ( vs.getBestAprilTagTarget().isPresent()) {
        	RecognizedAprilTagTarget target = vs.getBestAprilTagTarget().get();
        	Transform3d cameraToTarget =  target.getCameraToTargetTransform();
        	
        	AprilTagLocation tagLocation = target.getTagLocation();
        	if ( tagLocation != null ) {
               	Pose3d tagLocationPose = tagLocation.asPose3d();
            	Pose3d tagLocationMeters = new Pose3d (
            			tagLocationPose.getTranslation(),
            			tagLocationPose.getRotation()
            	);
	
            	Pose3d  estimatedPose = tagLocationMeters.transformBy(cameraToTarget.inverse()).transformBy(ROBOT_TO_CAM.inverse());    	        	
            	return Optional.of(estimatedPose.toPose2d());        		
        	}
    	}
    	return Optional.empty();
    }

}
