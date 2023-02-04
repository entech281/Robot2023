// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.RobotConstants;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.pose.RecognizedAprilTagTarget;
import frc.robot.pose.VisionPose;
import org.photonvision.targeting.PhotonPipelineResult;

public class VisionSubsystem extends EntechSubsystem {
  PhotonTrackedTarget bestTarget;
  Transform3d target3D;
  PhotonCamera camera;
  PhotonTrackedTarget target;
  double latency;

  public VisionSubsystem() {
    
  }
  
  // Entech does all the creation work in the initialize method
  @Override
  public void initialize() {
    camera = new PhotonCamera(RobotConstants.VISION.PHOTON_HOST);
    bestTarget = camera.getLatestResult().getBestTarget();
  }

  @Override
  public void initSendable(SendableBuilder builder) {
      builder.addDoubleProperty("CameraLatency", () -> { return latency; }, null);
  }

  public VisionPose getVisionOutput(){

      VisionPose visionOutput = new VisionPose();
      
      PhotonPipelineResult result = camera.getLatestResult();
      visionOutput.setLatency(result.getLatencyMillis());
      
      /**
       *   Old code using only a single target. See updated version below that does multiple
            Boolean cameraHasTargets = result.hasTargets();
            visionOutput.setCameraHasTargets(cameraHasTargets);

            int tagIDs = target.getFiducialId();
            visionOutput.setTagIDs(tagIDs);



            PhotonTrackedTarget t = result.getBestTarget();
            bestTarget = result.getBestTarget();
            target3D = t.getBestCameraToTarget();
            double targetXin = target3D.getX();
            double targetYin = target3D.getY();
            visionOutput.setTagPosesRelativeToCamera(new Pose2d(targetXin, targetYin, null));
      */
      
      for ( PhotonTrackedTarget t: result.getTargets()){
          
          Transform3d t3d = t.getBestCameraToTarget();
          Pose2d p = new Pose2d(t3d.getX(),t3d.getY(), t3d.getRotation().toRotation2d());
          RecognizedAprilTagTarget rat = new RecognizedAprilTagTarget(p,t.getFiducialId());
          visionOutput.addRecognizedTarget(rat);
      }


      return visionOutput;
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
