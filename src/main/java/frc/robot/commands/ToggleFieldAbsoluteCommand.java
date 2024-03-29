// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

/** An example command that uses an example subsystem. */
public class ToggleFieldAbsoluteCommand extends  EntechCommandBase {
    private DriveSubsystem drive;

  /**
   * Creates a new ToggleFieldAbsoluteCommand.
   *
   * @param drive The subsystem used by this command.
   */
  public ToggleFieldAbsoluteCommand(DriveSubsystem drive) {
    super(drive);
    this.drive = drive;
  }

  @Override
  public void initialize() {
    drive.toggleFieldAbsolute();
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

}
