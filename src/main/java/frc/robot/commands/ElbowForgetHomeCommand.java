// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ElbowSubsystem;

/** An example command that uses an example subsystem. */
public class ElbowForgetHomeCommand extends EntechCommandBase {

  private final ElbowSubsystem elbowSubsystem;
  /**
   * Creates a new PositionArmCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ElbowForgetHomeCommand(ElbowSubsystem subsystem) {
      super(subsystem);
      elbowSubsystem = subsystem;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
	  elbowSubsystem.forgetHome();
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