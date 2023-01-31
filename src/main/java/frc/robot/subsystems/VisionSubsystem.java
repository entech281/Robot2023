// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.RobotConstants;
import frc.robot.pose.VisionOutputs;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.pose.VisionOutput;

public class VisionSubsystem extends EntechSubsystem {
  PhotonTrackedTarget bestTarget;
  Transform3d target3D;
  PhotonCamera camera;
  PhotonTrackedTarget target;
  double latency;

  public VisionSubsystem() {
    
  }

  public VisionOutput getVisionOutput(){
      return new VisionOutput();
  }
  
  // Entech does all the creation work in the initialize method
  @Override
  public void initialize() {
    // Create the internal objects here
    camera = new PhotonCamera(RobotConstants.VISION.PHOTON_HOST);
    bestTarget = camera.getLatestResult().getBestTarget();
  }

  @Override
  public void initSendable(SendableBuilder builder) {

      builder.addDoubleProperty("CameraLatency", () -> {return latency; }, null);
  }

  public void getVisionOutputs(){

      VisionOutputs visionOutputs = new VisionOutputs();

      var result = camera.getLatestResult();
      Boolean cameraHasTargets = result.hasTargets();
      visionOutputs.setCameraHasTargets(cameraHasTargets);
      PhotonTrackedTarget NumberOfTargets = result.getBestTarget();

      int tagIDs = target.getFiducialId();
      visionOutputs.setTagIDs(tagIDs);

      double latency = camera.getLatestResult().getLatencyMillis();
      visionOutputs.setLatency(latency);

      PhotonTrackedTarget t = result.getBestTarget();
      bestTarget = result.getBestTarget();
      target3D = t.getBestCameraToTarget();
      double targetXin = target3D.getX();
      double targetYin = target3D.getY();
      visionOutputs.setTagPosesRelativeToCamera(new Pose2d(targetXin, targetYin, null));


  }
  
  @Override
  public void periodic() {
    var result = camera.getLatestResult();
    latency = camera.getLatestResult().getLatencyMillis();
    if (result.hasTargets()){
      PhotonTrackedTarget t = result.getBestTarget();
      bestTarget = result.getBestTarget();
      target3D = t.getBestCameraToTarget();
      
      SmartDashboard.putNumber("getcameraX", target3D.getX());
      SmartDashboard.putNumber("getcameraY", target3D.getY());
      SmartDashboard.putNumber("getcameraZ", target3D.getZ());
      
      SmartDashboard.putNumber("getcameraPitch", bestTarget.getPitch());
      SmartDashboard.putNumber("getcameraSkew", bestTarget.getSkew());
      SmartDashboard.putNumber("getcameraYaw", bestTarget.getYaw());
      SmartDashboard.putBoolean("getNumberOfTags", result.hasTargets());
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
} 
