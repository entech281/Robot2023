// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.DriveMode;

/** An example command that uses an example subsystem. */
public class DriveBrakeForSeconds extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_drive;
  private double time;
  private Timer timer;

  /**
   * Creates a new DriveBrakeForSeconds.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveBrakeForSeconds(Drivetrain subsystem, double time) {
      super(subsystem);
      m_drive = subsystem;
      this.time = time;
      timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.stop();
    m_drive.setDriveMode(DriveMode.BRAKE);
    timer.stop();
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return (timer.get() > time);
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
