// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ElbowSubsystem;

/** An example command that uses an example subsystem. */
public class PositionElbowCommand extends EntechCommandBase {

  private final ElbowSubsystem elbowSubsystem;
  private final double desiredPosition;
  private boolean waitToComplete = false;
  /**
   * Creates a new PositionArmCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public PositionElbowCommand(ElbowSubsystem elbowSubsystem, double desiredPosition, boolean waitToComplete) {
      super(elbowSubsystem);
      this.elbowSubsystem = elbowSubsystem;
      this.desiredPosition = desiredPosition;
      this.waitToComplete = waitToComplete;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  @Override  
  public String getName() {
	return super.getName() + "@" + desiredPosition + "deg";
  }  
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elbowSubsystem.requestPosition(desiredPosition);
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