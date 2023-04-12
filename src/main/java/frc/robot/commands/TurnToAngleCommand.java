// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.NavxStatus;
import frc.robot.filters.HoldYawFilter;

/** An example command that uses an example subsystem. */
public class TurnToAngleCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final NavXSubSystem navXSubSystem;
  private final HoldYawFilter holdYawFilter;

  /**
   * Creates a new TurnToAngleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */

      private double turnDegrees;
      private double targetAngleTolerance;
      private double slowDownAngle;
      private double slowDownAngleTorlerance;
      private double speedUpFactor;
      private double slowDownFactor;
      private final double INCRAMENTS = 20;

      public TurnToAngleCommand( HoldYawFilter holdYawFilter, NavXSubSystem navXSubSystem, double turnDegrees, double targetAngleTolerance, double speedUpFactor, double slowDownAngle, double slowDownFactor, double slowDownAngleTorlerance) {
          super(navXSubSystem);
          this.slowDownAngleTorlerance = slowDownAngleTorlerance;
          this.targetAngleTolerance = targetAngleTolerance;
          this.slowDownFactor = slowDownFactor;
          this.slowDownAngle = slowDownAngle;
          this.speedUpFactor = speedUpFactor;
          this.turnDegrees = turnDegrees;

          this.navXSubSystem = navXSubSystem;
          this.holdYawFilter = holdYawFilter;
      }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    holdYawFilter.enable(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Math.abs(navXSubSystem.getYaw()) < (slowDownAngle + slowDownAngleTorlerance)) {
        int numberOfIncraments = 0;
        for ( double i = 0; i <= INCRAMENTS; i++, numberOfIncraments++) {
            double currentSpeedUpFactor = speedUpFactor / (INCRAMENTS - numberOfIncraments);
            double turnAngle = navXSubSystem.getYaw() * currentSpeedUpFactor;
            holdYawFilter.setSetpoint(turnAngle);
        }
    } else {
        holdYawFilter.setSetpoint(navXSubSystem.getYaw() + turnDegrees);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      if (Math.abs(navXSubSystem.getYaw()))
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
