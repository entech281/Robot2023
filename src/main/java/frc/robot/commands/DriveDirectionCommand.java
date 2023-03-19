// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;
import frc.robot.filters.DriveInput;

import edu.wpi.first.wpilibj.Timer;

/** An example command that uses an example subsystem. */
public class DriveDirectionCommand extends EntechCommandBase {
  private final DriveSubsystem drive;
  private double forwardSpeed;
  private double rightSpeed;
  private double timeSec;
  private Timer driveTimer;

  /**
   * Drives by certain time in the directions given.  Negative time means opposite direction (so backward or left)
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveDirectionCommand(DriveSubsystem drive, double forwardSpeed, double rightSpeed, double timeSec) {
      super(drive);
      this.drive = drive;
      this.timeSec = timeSec;
      this.forwardSpeed = forwardSpeed;
      this.rightSpeed = rightSpeed;
      driveTimer = new Timer();
  }

  @Override
    public void initialize() {
        drive.setDriveMode(DriveMode.BRAKE);
        driveTimer.stop();
        driveTimer.reset();
        driveTimer.start();
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveInput di = new DriveInput(forwardSpeed,rightSpeed,0.,0.);
    drive.drive(di);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (driveTimer.get() > Math.abs(timeSec) + 0.5){
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