// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;

/** An example command that uses an example subsystem. */
public class DriveSetBrake extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_subsystem;

  /**
   * Creates a new DriveSetBrake.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveSetBrake(DriveSubsystem subsystem) {
      super(subsystem);
      m_subsystem = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_subsystem.setDriveMode(DriveMode.BRAKE);
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
      return true;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
