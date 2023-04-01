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
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.pose.AprilTagLocation;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.util.PoseUtil;
import edu.wpi.first.math.util.Units;

public class VisionSubsystem extends EntechSubsystem {

  private VisionStatus currentStatus = new VisionStatus();
  private PhotonCamera camera;
  private Transform3d ROBOT_TO_CAM = PoseUtil.robotToCameraTransform3d();
  private PhotonPoseEstimator photonPoseEstimator;
  private boolean enabled=false;

  public static final double NO_GOOD_ANGLE=-299;
  public static final Pose2d REFERENCE_POSE = new Pose2d(0,0,Rotation2d.fromDegrees(0));
  
  private double lastPhotonYawAngle = 0;
  @Override
  public void initialize() {

    AprilTagFieldLayout photonAprilTagFieldLayout;
	try {
		photonAprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);		
		
	} catch (IOException e) {
		throw new RuntimeException("Could not load wpilib AprilTagFields");
	}
	if ( Robot.isReal()) {
	    camera = new PhotonCamera(RobotConstants.VISION.PHOTON_HOST);
	    photonPoseEstimator = new PhotonPoseEstimator(photonAprilTagFieldLayout,PoseStrategy.AVERAGE_BEST_TARGETS,camera,ROBOT_TO_CAM);
	    photonPoseEstimator.setLastPose(REFERENCE_POSE);

	}	
  }

  @Override
  public void initSendable(SendableBuilder sb) {
	 
	 if(enabled) {
	     sb.addBooleanProperty("HasTargets", this::hasTargets, null);
	     sb.addDoubleProperty("Latency", this::getLatency, null);
	     sb.addBooleanProperty("HasPhotonPose", this::hasPhotonPose, null);
	     sb.addBooleanProperty("HasBestTarget", this::hasBestTarget, null);
	     sb.addStringProperty("Target", this::getBestTagName, null);
		 sb.addDoubleProperty("RobotLateralOffset", this::getLateralOffset , null);
		 sb.addDoubleProperty("CameraDistance", this::getCameraDistance , null);		 
	 }
	 else {
		 sb.addBooleanProperty("Enabled", () -> { return false;} , null);
	 }

  }
  
  
  public VisionStatus getStatus() {
	  return currentStatus;
  }
  
  
  private boolean hasTargets() {
	  return currentStatus.hasTargets();
  }

  private double getLatency() {
	  return currentStatus.getLatency();
  }

  private boolean hasPhotonPose() {
	  return currentStatus.getPhotonEstimatedPose().isPresent();
  }

  private boolean hasBestTarget() {
	  return currentStatus.getBestAprilTagTarget().isPresent();
  }

  private double getLateralOffset() {
	  if (currentStatus.hasTargets()) {
		return currentStatus.getBestAprilTagTarget().get().getCameraToTargetTransform().getY();
	  } else {
		return RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN;
	  }
  }

  private double getCameraDistance() {
	if (currentStatus.getBestAprilTagTarget().isPresent()) {
		return currentStatus.getBestAprilTagTarget().get().getCameraToTargetTransform().getX();
	  } else {
		return RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN;
	  }
  }

  private String getBestTagName() {
	  if ( currentStatus.getBestAprilTagTarget().isPresent()) {
          if (currentStatus.getBestAprilTagTarget().get().getTagLocation() != null) {
        	  return currentStatus.getBestAprilTagTarget().get().getTagLocation().getLocation().toString();
          }
	  }
	  return "NONE";
  }
  private void updateStatus(){
	  
	  	VisionStatus newStatus = new VisionStatus();
	  	lastPhotonYawAngle = NO_GOOD_ANGLE;
	    PhotonPipelineResult result = camera.getLatestResult();

	    newStatus.setLatency(camera.getLatestResult().getLatencyMillis());

	    if ( result.hasTargets()) {
	        for ( PhotonTrackedTarget t: result.getTargets()){	          
	        	newStatus.addRecognizedTarget(createRecognizedTarget(t));
	        }	   	    

		    PhotonTrackedTarget bestTarget = result.getBestTarget();
		    if ( bestTarget != null ) {
		    	SmartDashboard.putNumber("CAMERAY", bestTarget.getBestCameraToTarget().getY());
		    	newStatus.setCameraY(bestTarget.getBestCameraToTarget().getY());
		    	newStatus.setBestTarget(createRecognizedTarget(bestTarget));
		    	lastPhotonYawAngle = bestTarget.getYaw();
		    }
	    }
 
		Optional<EstimatedRobotPose> updatedPose = photonPoseEstimator.update();
		  
		if ( updatedPose.isPresent()) {
			newStatus.setPhotonEstimatedPose(updatedPose.get().estimatedPose);  
			photonPoseEstimator.setLastPose(updatedPose.get().estimatedPose);
		}		    

	    
	  currentStatus = newStatus;
	  debugStatus();
  }
  
  private void updateStatusSimulated(){
	  /**
	   * this is the robot location we will see in shuffleboard after .\gradlew simulateJava
	   */
	  VisionStatus newStatus = new VisionStatus();
	  	
	  //pretend we have exactly one target, pretty much right in front of us
	  //Transform3d T = PoseUtil.cameraToTargetDirectlyInFrontOfCamera(24);
	  Transform3d T = PoseUtil.cameraToTarget(Units.inchesToMeters(48),Units.inchesToMeters(3),180);
	  newStatus.setLatency(20);
	  newStatus.addRecognizedTarget(new RecognizedAprilTagTarget(T, AprilTagLocation.BLUE_MIDDLE,null));
  	  newStatus.setCameraY(T.getY());
  	  
  	  
  	  newStatus.setPhotonEstimatedPose(
  			  new Pose3d(new Pose2d(
  					  3.0,2.0,Rotation2d.fromDegrees(0)
  	  )));
	  currentStatus = newStatus;
	  debugStatus();
}  

private void debugStatus() {
    SmartDashboard.putString("getStatus Best Target:", "*" + currentStatus.getBestAprilTagTarget() +"*");
    SmartDashboard.putString("vs:", "*" + currentStatus +"*");
    SmartDashboard.putBoolean("hasTargets", currentStatus.hasTargets());
}
  public static RecognizedAprilTagTarget createRecognizedTarget(PhotonTrackedTarget t) {
	  
      Transform3d t3d = t.getBestCameraToTarget();
      int tagId = t.getFiducialId();
      AprilTagLocation loc = null;
      if( isValidTagId(tagId)) {
    	  loc = AprilTagLocation.findFromTag(tagId);
      }
      else {
    	  DriverStation.reportWarning("Photon Vision Target with bad tag id'" + tagId + "'", false);  
      }
      return new RecognizedAprilTagTarget (t3d,loc,t);
  }
  
  private static boolean isValidTagId(int tagId) {
	  return tagId > 0 && tagId < 9;
  }
  
  @Override
  public void periodic() {
	  if ( Robot.isReal()) {
		  if (enabled) {
			  updateStatus();
		  }		    
	  }
	  else {
		  updateStatusSimulated();
	  }
  }

@Override
public boolean isEnabled() {
	return enabled;
}
}