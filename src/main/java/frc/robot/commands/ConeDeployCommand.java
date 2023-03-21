// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotConstants;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.GripperSubsystem.GripperState;

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
  public ConeDeployCommand(ElbowSubsystem elbowSubsystem, GripperSubsystem gripperSubsystem) {
      super(elbowSubsystem,gripperSubsystem);
      this.elbowSubsystem = elbowSubsystem;
      this.gripperSubsystem =  gripperSubsystem;
  }
  
  private void calculateReleasePosition() {
	  double currentElbowPosition = elbowSubsystem.getActualPosition();
	  if ( currentElbowPosition > RobotConstants.ELBOW.SETTINGS.MIDDLE_HIGH_CONE_DEPLOY_THRESHOLD) {
		  releasePosition = RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_RELEASE_DEGREES;
	  } else {
		  releasePosition = RobotConstants.ELBOW.POSITION_PRESETS.SCORE_MID_RELEASE_DEGREES;
	  }
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    calculateReleasePosition();
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
		  gripperSubsystem.setGripperState(GripperState.kOpen);
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