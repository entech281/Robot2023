/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;
import java.util.Optional;

import org.opencv.core.Point;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.Robot;
import frc.robot.RobotConstants;

public class VisionSubsystem extends EntechSubsystem {

  private VisionStatus currentStatus = new VisionStatus();
  private PhotonCamera camera;

  private PhotonPoseEstimator photonPoseEstimator;
  private double latency = 0.0;
  private boolean enabled=true;
  private Optional<Point> lastPosition = Optional.empty();
  private Optional<Double> lastArea = Optional.empty();
  public static final int PIPELINE_COLOR = 1;
  public static final int PIPELINE_APRILTAG = 2;
  public static final double NO_TARGET = -1.0;
  
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
		camera.setPipelineIndex(PIPELINE_COLOR);

	}	
  }

  @Override
  public void initSendable(SendableBuilder sb) {
     sb.addDoubleProperty("getLastX", this::getLastX, null);
     sb.addDoubleProperty("Latency", this::getLatency, null);
     sb.addDoubleProperty("getLastY", this::getLastY, null);
     sb.addDoubleProperty("targetArea", this::getLastArea, null);

  }
  public boolean hasTarget() {
	  return getLastX() > 0 && getLastArea() > 0 ;
  }
  
  public double getLastX() {
	  if ( lastPosition.isPresent()) {
		  return lastPosition.get().x;
	  }
	  else {
		  return NO_TARGET;
	  }
  }
 
  public double getLastY() {
	  if ( lastPosition.isPresent()) {
		  return lastPosition.get().y;
	  }
	  else {
		  return NO_TARGET;
	  }
  }
  
  public double getLastArea() {
	  if ( lastArea.isPresent()) {
		  return lastArea.get();
	  }
	  else {
		  return NO_TARGET;
	  }
  }  
  
  public double getLatency() {
	  return this.latency;
  }
  private void updateCone() {
	  lastArea = Optional.empty();
	  lastPosition = Optional.empty();
	  if ( camera != null ) {
		  PhotonPipelineResult result = camera.getLatestResult();
		  latency = result.getLatencyMillis();
		  if ( result.hasTargets()) {
			  PhotonTrackedTarget bestTarget = result.getBestTarget();
			  if ( bestTarget != null ) {
				  lastPosition = Optional.of(getCenter(bestTarget));
				  lastArea = Optional.of(bestTarget.getArea());
			  }
		  }	  
	  }
  }
  
  private Point getCenter(PhotonTrackedTarget target) {
	 
	  double avgX = 0.0;
	  double avgY = 0.0;
	  for ( TargetCorner tc: target.getMinAreaRectCorners()){
		  avgX += tc.x;
		  avgY += tc.y;
	  }
	  return new Point(avgX/4.0, avgY/4.0);
  }
  
  @Override
  public void periodic() {
	  updateCone();
  }

@Override
public boolean isEnabled() {
	return enabled;
}

@Override
public VisionStatus getStatus() {
	// TODO Auto-generated method stub
	return new VisionStatus();
}
}