// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;

/** An example command that uses an example subsystem. */
public class DriveDistanceCommand extends EntechCommandBase {
  private static final double COUNTS_PER_METER = (0.478779 * 42);
  private static final double ENCODER_GEAR_RATIO = 9.92;
  private static final double SPEED_LIMIT = 0.875;
  private final double desiredDistanceMeters;
  private final DriveSubsystem drive;

  /**
   * Creates a new DriveDirectionCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveDistanceCommand(DriveSubsystem drive, double desiredDistanceMeters) {
      super(drive);
      this.drive = drive;
      this.desiredDistanceMeters = desiredDistanceMeters;
  }

  @Override
    public void initialize() {
      drive.resetEncoders();
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.drive(new DriveInput(SPEED_LIMIT, 0, 0, 0));
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
    return desiredDistanceMeters >= Math.abs(drive.getAveragePosition() / ENCODER_GEAR_RATIO / COUNTS_PER_METER);
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}