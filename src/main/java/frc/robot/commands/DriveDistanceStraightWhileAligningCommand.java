// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.commands.supplier.LateralOffsetSupplier;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;

/** An example command that uses an example subsystem. */
public class DriveDistanceStraightWhileAligningCommand extends EntechCommandBase {
  private static final int STOPPING_COUNT = 4;
  public static final double LATERAL_ALIGN_GAIN = 0.2;
  private double speed;
  private double minSpeed;
  private double rampFraction;
  private double desiredDistanceMeters;
  private final DriveSubsystem drive;
  private YawAngleSupplier yawSupplier;
  private LateralOffsetSupplier offsetSupplier;
  
  private int exeCounter;

  /**
   * Creates a new DriveDirectionCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveDistanceStraightWhileAligningCommand(DriveSubsystem drive, double desiredDistanceMeters, 
		  double speed, YawAngleSupplier yawSupplier, LateralOffsetSupplier offsetSupplier) {
      super(drive);
      this.drive = drive;
      this.desiredDistanceMeters = desiredDistanceMeters;
      this.speed = Math.copySign(speed, desiredDistanceMeters);
      this.rampFraction = 0.0;
      this.minSpeed = speed;
      this.yawSupplier = yawSupplier;
      this.offsetSupplier=offsetSupplier;
  }

  public DriveDistanceStraightWhileAligningCommand(DriveSubsystem drive, double desiredDistanceMeters, 
		  double speed, double minSpeed, double ramp_fraction, YawAngleSupplier yawSupplier,LateralOffsetSupplier offsetSupplier) {
    super(drive);
    this.drive = drive;
    this.desiredDistanceMeters = desiredDistanceMeters;
    this.speed = Math.copySign(speed, desiredDistanceMeters);
    this.minSpeed = minSpeed;
    this.rampFraction = ramp_fraction;
    this.yawSupplier = yawSupplier;
    this.offsetSupplier=offsetSupplier;    
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
    double lateral = 0.0;
    if ( offsetSupplier.getLateralOffset().isPresent()) {
    	lateral = offsetSupplier.getLateralOffset().get() * LATERAL_ALIGN_GAIN;
    }
    drive.driveFilterYawRobotRelative(new DriveInput(s, lateral, 0, yawSupplier.getYawAngleDegrees()));
  }

  // Called once the command ends or is interrupted
  @Override
  public void end(boolean interrupted) {
    DriverStation.reportWarning("END"+this, false);
    DriverStation.reportWarning("Encoder average meters: " + drive.getAverageDistanceMeters() + this, false);
    drive.stop();
    drive.setDriveMode(DriveMode.BRAKE);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // DriverStation.reportWarning("Distance: " + drive.getAverageDistanceMeters(), false);
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
}