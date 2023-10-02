// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.commands.supplier.EstimatedPoseSupplier;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.filters.DriveInput;
import frc.robot.pose.ScoringLocation;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.DriveMode;

/** An example command that uses an example subsystem. */
public class DriveDistanceStraightWhileAligningCommand extends EntechCommandBase {
  private static final int STOPPING_COUNT = 4;
  private static final double LATERAL_ALIGN_GAIN = 1.5;
  private static final double DISTANCE_TO_TARGET_TOLLERENCE = 2.5;

  private double speed;
  private double minSpeed;
  private double rampFraction;
  private double desiredDistanceMeters;
  private final Drivetrain drive;
  private YawAngleSupplier yawSupplier;
  private EstimatedPoseSupplier poseSupplier;
  private Supplier<ScoringLocation> targetLocation;
  
  private int exeCounter;

  /**
   * Creates a new DriveDirectionCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveDistanceStraightWhileAligningCommand(Drivetrain drive, double desiredDistanceMeters, 
		  double speed, YawAngleSupplier yawSupplier, EstimatedPoseSupplier poseSupplier, Supplier<ScoringLocation> scoringLocation) {
      super(drive);
      this.drive = drive;
      this.desiredDistanceMeters = desiredDistanceMeters;
      this.speed = Math.copySign(speed, desiredDistanceMeters);
      this.rampFraction = 0.0;
      this.minSpeed = speed;
      this.yawSupplier = yawSupplier;
      this.poseSupplier=poseSupplier;
      this.targetLocation = scoringLocation;

  }

  public DriveDistanceStraightWhileAligningCommand(Drivetrain drive, double desiredDistanceMeters, 
		  double speed, double minSpeed, double ramp_fraction, YawAngleSupplier yawSupplier,EstimatedPoseSupplier poseSupplier, Supplier<ScoringLocation> scoringLocation) {
    super(drive);
    this.drive = drive;
    this.desiredDistanceMeters = desiredDistanceMeters;
    this.speed = Math.copySign(speed, desiredDistanceMeters);
    this.minSpeed = minSpeed;
    this.rampFraction = ramp_fraction;
    this.yawSupplier = yawSupplier;
    this.poseSupplier=poseSupplier;
    this.targetLocation = scoringLocation;
  }

  @Override
    public void initialize() {
      drive.resetEncoders();
      drive.setDriveMode(DriveMode.BRAKE);
      drive.setHoldYawAngle(yawSupplier.getYawAngleDegrees());
      DriverStation.reportWarning("INIT"+ this, false);
      exeCounter = 0;
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double s = speed;
    double ratio = Math.abs(drive.getAverageDistanceMeters()/desiredDistanceMeters);
    if ((minSpeed < speed) && (rampFraction > 0.0)) {
        if (ratio < rampFraction) {
            s = (minSpeed + (1.0-minSpeed)*(ratio/rampFraction))*speed;
        } else if (ratio > 0.9) {
            ratio = ratio - (1.0 - rampFraction);
            s = (1.0 - (1.0-minSpeed)*(ratio/rampFraction))*speed;
        } else {
            s = speed;
        }
    }
    exeCounter++;
    double lateralOutput = 0.0;
    ScoringLocation targetLocation = this.targetLocation.get();
    if ( poseSupplier.getEstimatedPose().isPresent()) {
    	Pose2d currentPose = poseSupplier.getEstimatedPose().get(); 

      double distToTarget = Math.abs(currentPose.getX() - targetLocation.computeAbsolutePose().getX());

      if (distToTarget < DISTANCE_TO_TARGET_TOLLERENCE) {
        double lateralOffset = computeRobotRelativeOffsetToTarget(targetLocation.computeAbsolutePose().getY(),currentPose.getY());
        lateralOutput = lateralOffset * LATERAL_ALIGN_GAIN;
        //SmartDashboard.putNumber("AlignWhileDriving:lateralOffset", lateralOffset); 	
      }
    }
    
	//SmartDashboard.putNumber("AlignWhileDriving:lateralOutput", lateralOutput);
    drive.driveFilterYawRobotRelative(new DriveInput(s, lateralOutput, 0, yawSupplier.getYawAngleDegrees()));
  }

  // Called once the command ends or is interrupted
  @Override
  public void end(boolean interrupted) {
    drive.stop();
    drive.setDriveMode(DriveMode.BRAKE);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (exeCounter < STOPPING_COUNT) {
      return false;
    }
    return Math.abs(drive.getAverageDistanceMeters()) >= Math.abs(desiredDistanceMeters);
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
  
  private double computeRobotRelativeOffsetToTarget(double targetAbsoluteY, double robotAbsoluteY) {
	  if ( DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
		  return targetAbsoluteY - robotAbsoluteY;
	  }
	  else {
		  return robotAbsoluteY - targetAbsoluteY;
	  }
  }
}