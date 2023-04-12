// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.RobotConstants;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.commands.supplier.YawAngleSupplier;
import frc.robot.filters.DriveInput;

/** A command that will turn the robot to the specified angle using a motion profile. */
public class TurnAngleCommand extends ProfiledPIDCommand {
  /**
   * Turns to robot to the specified angle using a motion profile.
   *
   * @param targetAngleDegrees The angle to turn to
   * @param drive The drive subsystem to use
   */
  public TurnAngleCommand(double targetAngleDegrees, DriveSubsystem drive, YawAngleSupplier yawSupplier) {
    super(
        new ProfiledPIDController(
        	0.005,
        	0.0,
        	0.0,
            new TrapezoidProfile.Constraints(
            	20,
            	200.)),
        // Close loop on heading
        yawSupplier::getYawAngleDegrees,
        // Set reference to target
        targetAngleDegrees,
        // Pipe output to turn robot
        (output, setpoint) -> drive.drive(new DriveInput(0.0, 0.0, output, 0.0)),
        // Require the drive
        drive);

    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-180, 180);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(5.0, 15.0);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atGoal();
  }
}