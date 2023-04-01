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
  public static final int PIPELINE_COLOR = 0;
  public static final int PIPELINE_APRILTAG = 1;
  
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

	}	
  }

  @Override
  public void initSendable(SendableBuilder sb) {
     sb.addDoubleProperty("getLastX", this::getLastX, null);
     sb.addDoubleProperty("Latency", this::getLatency, null);
     sb.addDoubleProperty("getLastY", this::getLastY, null);

  }
  
  public double getLastX() {
	  if ( lastPosition.isPresent()) {
		  return lastPosition.get().x;
	  }
	  else {
		  return -1.0;
	  }
  }
 
  public double getLastY() {
	  if ( lastPosition.isPresent()) {
		  return lastPosition.get().y;
	  }
	  else {
		  return -1.0;
	  }
  }
  public double getLatency() {
	  return this.latency;
  }
  public Optional<Point> getColoredObjectCenter() {
	  Point p = new Point();
	  p.x = 100;
	  p.y = 20;
	  return Optional.of(p);
//	  if ( camera != null ) {
//		  PhotonPipelineResult result = camera.getLatestResult();
//		  latency = result.getLatencyMillis();
//		  if ( result.hasTargets()) {
//			  PhotonTrackedTarget bestTarget = result.getBestTarget();
//			  if ( bestTarget != null ) {
//				  lastPosition = Optional.of(getCenter(bestTarget));
//				  return lastPosition;
//			  }
//		  }
//		  return Optional.empty();		  
//	  }
//	  return Optional.empty();
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