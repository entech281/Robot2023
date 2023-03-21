// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;

public class DriveForwardToBalanceCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final NavXSubSystem navx;
  private boolean pitch_seen;
  private int pitch_stable_count;
  private double speed = 0.0;
  private static final int    ROBOT_STABLE_COUNT = 500;
  private static final double DRIVE_SPEED = 0.15;
  private static final double PITCH_THRESHOLD = 12.0;

  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   */
  public DriveForwardToBalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys) {
      super(dsubsys,nsubsys);
      drive = dsubsys;
      navx = nsubsys;
      speed = DRIVE_SPEED;
  }

  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   * @param speed Drive speed (forward is positive, default)
   */
  public DriveForwardToBalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, double speed) {
    super(dsubsys,nsubsys);
    drive = dsubsys;
    navx = nsubsys;
    this.speed = speed;
}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pitch_seen = false;
    pitch_stable_count = 0;
    drive.setDriveMode(DriveMode.BRAKE);
    double yaw_setpoint = 0.0;
    if (Math.abs(navx.getYaw()) > 90.0) {
        yaw_setpoint = 180.0;
    }
    drive.setHoldYawAngle(yaw_setpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveInput di=new DriveInput(speed,0.0,0.0, navx.getYaw());
    double pitch_angle = navx.getPitch();
    if (!pitch_seen) {
        if (Math.abs(pitch_angle) > PITCH_THRESHOLD) {
            pitch_seen = true;
        }
        drive.driveFilterYawOnly(di);
    } else {
        if (Math.abs(pitch_angle) < PITCH_THRESHOLD) {
            drive.stop();
            pitch_stable_count += 1;
        } else {
            pitch_stable_count = 0;
            di.setForward(Math.copySign(speed, pitch_angle));
            drive.driveFilterYawOnly(di);
        }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return pitch_stable_count > ROBOT_STABLE_COUNT;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
