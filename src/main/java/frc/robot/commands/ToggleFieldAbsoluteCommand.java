// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

/** An example command that uses an example subsystem. */
public class ToggleFieldAbsoluteCommand extends EntechCommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final DriveSubsystem drive;
  private boolean isFinished = false;

  /**
   * Creates a new ToggleFieldAbsoluteCommand.
   *
   * @param Drive The subsystem used by this command.
   */
  public ToggleFieldAbsoluteCommand(DriveSubsystem Drive) {
    super(Drive);
    drive = Drive;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    drive.toggleFieldAbsolute();
    isFinished = true;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
