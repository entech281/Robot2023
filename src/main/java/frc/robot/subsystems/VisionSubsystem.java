/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import frc.robot.pose.AprilTagLocation;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.pose.VisionStatus;

public class VisionSubsystem extends EntechSubsystem {

  private PhotonCamera camera;
  private VisionStatus currentStatus;
  private final Transform3d ROBOT_TO_CAM = new Transform3d( 
		  new Translation3d( 
				  RobotConstants.VISION.CAMERA_POSITION.FORWARD_OF_CENTER_METETRS,
				  RobotConstants.VISION.CAMERA_POSITION.LEFT_OF_CENTER_METERS,
				  RobotConstants.VISION.CAMERA_POSITION.UP_METERS), new Rotation3d(0,0,0));
  private PhotonPoseEstimator photonPoseEstimator;
  
  @Override
  public void initialize() {
    camera = new PhotonCamera(RobotConstants.VISION.PHOTON_HOST);

    AprilTagFieldLayout photonAprilTagFieldLayout;
	try {
		photonAprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);		
		photonPoseEstimator = new PhotonPoseEstimator(photonAprilTagFieldLayout,PoseStrategy.CLOSEST_TO_LAST_POSE,camera,ROBOT_TO_CAM);
	} catch (IOException e) {
		throw new RuntimeException("Could not load wpilib AprilTagFields");
	}
    
  }

  @Override
  public void initSendable(SendableBuilder builder) {
     //TODO:: fill out
  }

  public VisionStatus getStatus(){
	  return currentStatus;
  }
  

  
  public void updateCurrentStatus() {
	  	VisionStatus newStatus = new VisionStatus();
	  	
		Optional<EstimatedRobotPose> updatedPose = photonPoseEstimator.update();
		  
		if ( updatedPose.isPresent()) {
			newStatus.setPhotonEstimatedPose(updatedPose.get().estimatedPose);  
		}	  	  	
	  	
	    PhotonPipelineResult result = camera.getLatestResult();
	    newStatus.setLatency(camera.getLatestResult().getLatencyMillis());

	    if ( result.hasTargets()) {
	        for ( PhotonTrackedTarget t: result.getTargets()){	          
	        	newStatus.addRecognizedTarget(createRecognizedTarget(t));
	        }	   	    

		    PhotonTrackedTarget  bestTarget = result.getBestTarget();
		    if ( bestTarget != null ) {
		    	newStatus.setBestTarget(createRecognizedTarget(bestTarget));
		    }	    	    	
	    }
 
	    //SmartDashboard.putBoolean("hasTargets", result.hasTargets());	        
        //SmartDashboard.putNumber("getcameraX", target3D.getX());
        //SmartDashboard.putNumber("getcameraY", target3D.getY());
        //SmartDashboard.putNumber("getcameraZ", target3D.getZ());
      
        //SmartDashboard.putNumber("getcameraPitch", bestTarget.getPitch());
        //SmartDashboard.putNumber("getcameraSkew", bestTarget.getSkew());
        //SmartDashboard.putNumber("getcameraYaw", bestTarget.getYaw());
	    currentStatus = newStatus;
  }

  public static RecognizedAprilTagTarget createRecognizedTarget(PhotonTrackedTarget t) {
	  
      Transform3d t3d = t.getBestCameraToTarget();
      AprilTagLocation loc = AprilTagLocation.findFromTag(t.getFiducialId());
      return new RecognizedAprilTagTarget (t3d,loc);
  }
  
  @Override
  public void periodic() {
	  updateCurrentStatus();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}