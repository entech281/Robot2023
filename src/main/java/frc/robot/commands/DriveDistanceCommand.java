// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/** An example command that uses an example subsystem. */
public class DriveDistanceCommand extends EntechCommandBase {
  private double speed;
  private double desiredDistanceMeters;
  private final DriveSubsystem drive;

  /**
   * Creates a new DriveDirectionCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveDistanceCommand(DriveSubsystem drive, double desiredDistanceMeters, double speed) {
      super(drive);
      this.drive = drive;
      this.desiredDistanceMeters = desiredDistanceMeters;
      this.speed = Math.copySign(speed, desiredDistanceMeters);
  }

  @Override
    public void initialize() {
      drive.resetEncoders();
      drive.setBrakeMode();
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.drive(new DriveInput(speed, 0, 0, 0));
  }

  // Called once the command ends or is interrupted
  @Override
  public void end(boolean interrupted) {
    drive.stop();
    drive.setCoastMode();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(desiredDistanceMeters) >= Math.abs(drive.getAverageDistanceMeters());
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}