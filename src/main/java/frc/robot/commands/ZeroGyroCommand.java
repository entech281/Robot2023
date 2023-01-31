// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.NavXSubSystem;

/** An example command that uses an example subsystem. */
public class ZeroGyroCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final NavXSubSystem NavX;
  private boolean isFinished = false;

  /**
   *
   *
   * @param subsystem The subsystem used by this command.
   */
  public ZeroGyroCommand(NavXSubSystem subsystem) {
      super(subsystem);
      NavX = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    NavX.getGyro().reset();
    isFinished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return isFinished;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return true;
  }
}
