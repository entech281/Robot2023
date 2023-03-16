// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotConstants;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.GripperSubsystem;

/** An example command that uses an example subsystem. */
public class ConeDeployCommand extends EntechCommandBase {

  private final ElbowSubsystem elbowSubsystem;
  private final GripperSubsystem gripperSubsystem;
  private double releasePosition;
  private boolean reachedTargetHeight = false;
  /**
   * Score High. 
   * Assumes that the arm is already positioned in high mode.
   * Moves down slowly until target position is reached ( at which point it will let go)
   * or until cancelled, returning to previous position
   * 
   * NOTE: commands are interruptable by defeault
   */
  public ConeDeployCommand(ElbowSubsystem elbowSubsystem, GripperSubsystem gripperSubsystem, double releasePosition) {
      super(elbowSubsystem);
      this.elbowSubsystem = elbowSubsystem;
      this.gripperSubsystem =  gripperSubsystem;
      this.releasePosition = releasePosition;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
	  elbowSubsystem.setMotorSpeed(-RobotConstants.ELBOW.SETTINGS.ELBOW_SLOWDOWN_SPEED);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
	  
	  elbowSubsystem.stop();
	  
	  if (reachedTargetHeight) {
		  gripperSubsystem.setOpen(true);
		  elbowSubsystem.setPosition(elbowSubsystem.getActualPosition());
	  }
	  else {
		  elbowSubsystem.restorePosition();
	  }
	  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
	  reachedTargetHeight = ( elbowSubsystem.getActualPosition() <= releasePosition ); 
	  return reachedTargetHeight;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}