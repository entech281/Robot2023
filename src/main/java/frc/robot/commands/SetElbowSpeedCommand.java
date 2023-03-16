// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ElbowSubsystem;

/** An example command that uses an example subsystem. */
public class SetElbowSpeedCommand extends EntechCommandBase {

  private final ElbowSubsystem elbowSubsystem;
  private final double desiredSpeed;

  /**
   * Creates a new PositionArmCommand.
   *
   * @param subsystem The subsystem used by this command.
   * 
   * NOTE: commands are interruptable by defeault
   */
  public SetElbowSpeedCommand(ElbowSubsystem elbowSubsystem, double desiredSpeed) {
      super(elbowSubsystem);
      this.elbowSubsystem = elbowSubsystem;
      this.desiredSpeed = desiredSpeed;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  @Override  
  public String getName() {
	return super.getName() + ",s=" + desiredSpeed;
  }  
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elbowSubsystem.setMotorSpeed(desiredSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
	  elbowSubsystem.stop();
	  elbowSubsystem.restorePosition();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
	  return false;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}