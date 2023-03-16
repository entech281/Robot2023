// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;

/** An example command that uses an example subsystem. */
public class DriveForwardToBalanceCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final NavXSubSystem navx;
  private boolean pitch_seen;
  private int pitch_stable_count;
  private static final int    ROBOT_STABLE_COUNT = 50;
  private static final double DRIVE_SPEED = 0.3;
  private static final double PITCH_THRESHOLD = 10.0;

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
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pitch_seen = false;
    pitch_stable_count = 0;
    drive.setDriveMode(DriveMode.BRAKE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveInput di=new DriveInput(DRIVE_SPEED,0.0,0.0, 0.0);
    double pitch_angle = navx.getPitch();
    if (!pitch_seen) {
        if (Math.abs(pitch_angle) > PITCH_THRESHOLD) {
            pitch_seen = true;
        }
        drive.drive(di);
        return;
    } else {
        if (Math.abs(pitch_angle) < PITCH_THRESHOLD) {
            drive.stop();
            pitch_stable_count += 1;
            return;
        }
        pitch_stable_count = 0;
        // TODO: verify that positive pitch means we need to move forward
        di.setForward(Math.copySign(DRIVE_SPEED, pitch_angle));
        drive.drive(di);
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
