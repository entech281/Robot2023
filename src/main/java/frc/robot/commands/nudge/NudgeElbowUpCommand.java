// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.nudge;

import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.commands.EntechCommandBase;

/** An example command that uses an example subsystem. */
public class NudgeElbowUpCommand extends EntechCommandBase {

  private final ElbowSubsystem elbowSubsystem;
  private boolean waitToComplete = false;
  /**
   * Creates a new PositionArmCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public NudgeElbowUpCommand(ElbowSubsystem elbowSubsystem, boolean waitToComplete) {
      super(elbowSubsystem);
      this.elbowSubsystem = elbowSubsystem;
      this.waitToComplete = waitToComplete;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elbowSubsystem.nudgeElbowUp();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
	  elbowSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
	  if ( waitToComplete) {
		  return elbowSubsystem.isAtRequestedPosition();
	  }
	  else {
		  return true;
	  }
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}