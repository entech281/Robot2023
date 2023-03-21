// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;

/**
 *
 *
 * @author aheitkamp
 */
public class ZeroGyroCommand extends EntechCommandBase {
	
  private final NavXSubSystem navX;
  private DriveSubsystem driveSubsystem;

  /**
   * Creates a new ZeroGyro Command that will reset the gyro value
   *
   * 
   * @param navX The NavXSubsystem this command runs on.
   */
  public ZeroGyroCommand(NavXSubSystem navX, DriveSubsystem driveSubsystem) {
    super(navX);
    this.navX = navX;
    this.driveSubsystem = driveSubsystem;
  }

  @Override
  public void initialize() {
    navX.zeroYaw();
    driveSubsystem.setHoldYawAngle(navX.getYaw());
  }


  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
