// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.RobotConstants;

import edu.wpi.first.util.sendable.SendableBuilder;

public class ExampleSubsystem extends EntechSubsystem {
  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
  }

  // Entech does all the creation work in the initialize method
  @Override
  public void initialize() {
    // Create the internal objects here
  }

  @Override
  public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType(getName());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
