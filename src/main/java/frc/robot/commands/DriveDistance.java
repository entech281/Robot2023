// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/** An example command that uses an example subsystem. */
public class DriveDistance extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private static final double countsPerMeters = (0.478779 * 42);
  private static final double encoderGearRatio = 9.92;
  private static final double speedLimt = 0.875;
  private final double desiredDistance;
  private final DriveSubsystem drive;

  /**
   * Creates a new DriveDirectionCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveDistance(DriveSubsystem drive, double desiredDistance) {
      super(drive);
      this.drive = drive;
      this.desiredDistance = desiredDistance;
  }

  @Override
    public void initialize() {
      drive.resetEncoders();
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.drive(new DriveInput(speedLimt, 0, 0, 0));
  }

  // Called once the command ends or is interrupted
  @Override
  public void end(boolean interrupted) {
    drive.brake();
    drive.setCoast();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return desiredDistance <= (drive.getAveragePosition() / encoderGearRatio) / countsPerMeters;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}