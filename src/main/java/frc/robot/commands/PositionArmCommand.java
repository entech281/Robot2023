// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;

/** An example command that uses an example subsystem. */
public class PositionArmCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ArmSubsystem armSubsystem;
  private final ArmSubsystem.ArmPreset preset;

  /**
   * Creates a new PositionArmCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public PositionArmCommand(ArmSubsystem subsystem, ArmSubsystem.ArmPreset preset) {
      super(subsystem);
      armSubsystem = subsystem;
      this.preset = preset;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    armSubsystem.setDesiredPosition(preset);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Nothing here.  Work is all done inside ArmSubsystem.periodic()
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    armSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return armSubsystem.isAtDesiredPosition();
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
