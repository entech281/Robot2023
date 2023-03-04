// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.nudge;

import frc.robot.commands.EntechCommandBase;
import frc.robot.controllers.PositionController;
import frc.robot.subsystems.ArmSubsystem;


/** An example command that uses an example subsystem. */
public class NugeTelescopeBackwardsCommand extends EntechCommandBase {

  private final ArmSubsystem armSubsystem;
  private boolean waitToComplete = false;
  /**
   * Creates a new PositionArmCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public NugeTelescopeBackwardsCommand(ArmSubsystem subsystem, int requestedPosition, boolean waitToComplete, PositionController positionController) {
      super(subsystem);
      armSubsystem = subsystem;
      this.waitToComplete = waitToComplete;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    armSubsystem.nudgeArmBackwards();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    armSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
	  if ( waitToComplete) {
		  return armSubsystem.isAtRequestedPosition();
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