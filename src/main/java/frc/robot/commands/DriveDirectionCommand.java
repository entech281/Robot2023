// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.EntechSubsystem;

/** An example command that uses an example subsystem. */
public class DriveDirectionCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private double forwardDistance;
  private double rightDistance;
  private double speed;

  /**
   * Creates a new DriveDirectionCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveDirectionCommand(DriveSubsystem drive, double forwardDistance, double rightDistance, double speed) {
      super(drive);
      this.drive = drive;
      this.forwardDistance = forwardDistance;
      this.rightDistance = rightDistance;
      this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return false;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}