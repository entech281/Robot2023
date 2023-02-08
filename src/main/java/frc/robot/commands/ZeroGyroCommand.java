// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.NavXSubSystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class ZeroGyroCommand extends EntechCommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final NavXSubSystem navX;
  private boolean isFinished = false;

  /**
   * Creates a new ZeroGyro Command that will reset the gyro value
   *
   * 
   * @param NavX The NavXSubsystem this command runs on.
   */
  public ZeroGyroCommand(NavXSubSystem NavX) {
    super(NavX);
    navX = NavX;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    navX.getGyro().reset();
    isFinished = true;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
