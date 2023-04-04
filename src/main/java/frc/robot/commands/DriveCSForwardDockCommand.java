// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;

/**
 * Assumes that the robot has already tipped the charging station.  
 * Moves about 1 robot depth on to the charging station
 */
public class DriveCSForwardDockCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final NavXSubSystem navx;
  private double start_speed = 0.0;
  private double end_speed = 0.0;
  private double startDistance;

  /**
   * Creates a new DriveCSForwardDockCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   */
  public DriveCSForwardDockCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys) {
	  super(dsubsys);
      this.drive = dsubsys;
      this.navx = nsubsys;
      double pitch = this.navx.getPitch();
      start_speed = Math.copySign(RobotConstants.BALANCE_PARAMETERS.DOCK_INITIAL_SPEED, pitch);
      end_speed = Math.copySign(RobotConstants.BALANCE_PARAMETERS.DOCK_FINAL_SPEED, pitch);
  }

  /**
   * Creates a new DriveCSForwardDockCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   * @param speed Drive speed (forward is positive, default)
   */
  public DriveCSForwardDockCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, double start_speed, double end_speed) {
    super(dsubsys);
    this.drive = dsubsys;
    this.navx = nsubsys;
    double pitch = this.navx.getPitch();
    this.start_speed = Math.copySign(start_speed, pitch);
    this.end_speed = Math.copySign(end_speed, pitch);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.resetEncoders();
    startDistance = drive.getAverageDistanceMeters();
    startDistance = 0.0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double s = calculateSpeed(Math.abs(drive.getAverageDistanceMeters()-startDistance), RobotConstants.BALANCE_PARAMETERS.DOCK_DISTANCE, start_speed, end_speed);
    DriveInput di=new DriveInput(s,0.0,0.0, navx.getYawAngleDegrees());
    drive.driveFilterYawRobotRelative(di);
  }

  private double calculateSpeed(double distance, double dref, double speed0, double speed1) {
    double fraction = Math.min(distance/dref, 1.0);
    return (1.0-fraction)*speed0 + (fraction*speed1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(drive.getAverageDistanceMeters()-startDistance) > RobotConstants.BALANCE_PARAMETERS.DOCK_DISTANCE) {
        DriverStation.reportWarning("END:" + this, false);
        return true;
    }
      return false;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
