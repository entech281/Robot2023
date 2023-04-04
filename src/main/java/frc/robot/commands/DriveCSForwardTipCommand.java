// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;

public class DriveCSForwardTipCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final NavXSubSystem navx;
  private double speed = 0.0;
  private boolean pitch_seen;
  private double last_pitch;

  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   */
  public DriveCSForwardTipCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys) {
	  super(dsubsys,nsubsys);
      this.drive = dsubsys;
      this.navx = nsubsys;
      this.speed = RobotConstants.BALANCE_PARAMETERS.CHARGESTATION_APPROACH_SPEED;
  }

  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   * @param speed Drive speed (forward is positive, default)
   */
  public DriveCSForwardTipCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, double speed) {
    super(dsubsys,nsubsys);
    this.drive = dsubsys;
    this.navx = nsubsys;
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    last_pitch = Math.abs(navx.getPitch());
    pitch_seen = false;
    drive.setDriveMode(DriveMode.BRAKE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (Math.abs(navx.getPitch()) > RobotConstants.BALANCE_PARAMETERS.TIP_PITCH_THRESHOLD) {
        pitch_seen = true;
    }
    DriveInput di=new DriveInput(speed,0.0,0.0, navx.getYawAngleDegrees());
    drive.driveFilterYawRobotRelative(di);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (pitch_seen) {
        DriverStation.reportWarning("END:" + this, false);
    }
    return pitch_seen;
    // double pitch = Math.abs(navx.getPitch());
    // if (pitch_seen && (pitch < last_pitch)) {
    //     return true;
    // }
    // last_pitch = pitch;
    // return false;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
