// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;

/** An example command that uses an example subsystem. */
public class GripperCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ArmSubsystem armSubsystem;
  private ArmSubsystem.GripperState state;

  /**
   * Creates a new GripperCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public GripperCommand(ArmSubsystem subsystem, ArmSubsystem.GripperState state) {
      super(subsystem);
      armSubsystem = subsystem;
      this.state = state;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    armSubsystem.setGripperState(state);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Nothing to do.  Everything is managed in the ArmSubsystem periodic()
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
