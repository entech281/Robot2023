// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ElbowSubsystem;

/** An example command that uses an example subsystem. */
public class HomeElbowCommand extends EntechCommandBase {

  private final ElbowSubsystem elbowSubsystem;
  private boolean waitToComplete = false;

  /**
   * Creates a new PositionArmCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public HomeElbowCommand(ElbowSubsystem subsystem) {
    super(subsystem);
    elbowSubsystem = subsystem;
 
  }
  public HomeElbowCommand(ElbowSubsystem subsystem, boolean waitToComplete) {
	  super(subsystem);
	  elbowSubsystem = subsystem;
	  this.waitToComplete = waitToComplete;
  }

  @Override
  public String getName(){
     return super.getName() + "wait:" + waitToComplete;
  }
  
// Called when the command is initially scheduled.
  @Override
  public void initialize() {
	  elbowSubsystem.home();
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
	  if ( waitToComplete) {
		  return elbowSubsystem.isHomed();
	  } else {
		  return true;
	  }
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}