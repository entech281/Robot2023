// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.filters.DriveInput;

import edu.wpi.first.wpilibj.Timer;

/** An example command that uses an example subsystem. */
public class DriveDirectionCommand extends EntechCommandBase {
  private final DriveSubsystem drive;
  private double forwardDistance;
  private double rightDistance;
  private double speed;
  private Timer driveTimer;

  /**
   * Creates a new DriveDirectionCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveDirectionCommand(DriveSubsystem drive, double forwardDistance, double rightDistance, double speed) {
      super(drive);
      this.drive = drive;
      this.speed = speed;
      this.forwardDistance = forwardDistance;
      this.rightDistance = rightDistance;
      driveTimer = new Timer();
  }

  @Override
    public void initialize() {
        drive.setBrake();
        driveTimer.stop();
        driveTimer.reset();
        driveTimer.start();
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveInput di = new DriveInput(0.,0.,0.,0.);
    if (driveTimer.get() < Math.abs(forwardDistance)) {
        di.setForward(Math.copySign(speed, forwardDistance));
    }
    if (driveTimer.get() < Math.abs(rightDistance)) {
        di.setRight(Math.copySign(speed, rightDistance));
    }
    drive.drive(di);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.brake();
    drive.setCoast();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (driveTimer.get() > Math.max( Math.abs(forwardDistance), Math.abs(rightDistance)) + 0.5){
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