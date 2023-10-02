// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.Drivetrain.DriveMode;

public class DriveCSForwardTipCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drive;
  private final NavXSubSystem navx;
  private double speed = 0.0;
  private double last_pitch;

  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   */
  public DriveCSForwardTipCommand(Drivetrain dsubsys, NavXSubSystem nsubsys) {
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
  public DriveCSForwardTipCommand(Drivetrain dsubsys, NavXSubSystem nsubsys, double speed) {
    super(dsubsys,nsubsys);
    this.drive = dsubsys;
    this.navx = nsubsys;
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    last_pitch = Math.abs(navx.getPitch());
    drive.setDriveMode(DriveMode.BRAKE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

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
    double curr_pitch = Math.abs(navx.getPitch());

    // If either the last pitch or the curr_pitch exceeds the threshold and the pitch is starting to drop, then we are done tipping the charging station
    if (((curr_pitch > RobotConstants.BALANCE_PARAMETERS.TIP_PITCH_THRESHOLD) || (last_pitch > RobotConstants.BALANCE_PARAMETERS.TIP_PITCH_THRESHOLD))
     && (curr_pitch < last_pitch)) {
        DriverStation.reportWarning("END:" + this, false);
        return true;
    }
    last_pitch = curr_pitch;
    return false;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
