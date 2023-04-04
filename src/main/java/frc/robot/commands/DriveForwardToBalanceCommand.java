// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.BrakeSubsystem;
import frc.robot.subsystems.BrakeSubsystem.BrakeState;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;

public class  DriveForwardToBalanceCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final NavXSubSystem navx;
  BrakeSubsystem brake;
  private boolean pitch_seen;
  private int pitch_stable_count;
  private double speed = 0.0;
  private double original_speed = 0.0;
  private static final int    ROBOT_STABLE_COUNT = 500;
  private static final double PITCH_THRESHOLD = 14.0;
  private static final double SPEED_AFTER_PITCH = 0.13;
  private static final double PITCH_SLOW_THRESHOLD = 18.0;
  private static final double PITCH_FLAT_THRESHOLD = 12.0;
  private static final double BACK_NUDGE_TIME = 0.0;   // Set to zero (or negative) to turn off the back nudge
  private static final double BACK_NUDGE_SPEED = 0.15;
  private static final double CHARGESTATION_DEPTH = 1.22;
  private double start_slowdown;
  private boolean useBrakes = false;
  private Timer backNudgeTimer;

  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   */
  public DriveForwardToBalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, BrakeSubsystem brakeSubsystem, boolean useBrakes) {
	  super(dsubsys,nsubsys,brakeSubsystem);
      drive = dsubsys;
      navx = nsubsys;
      brake = brakeSubsystem;
      speed = RobotConstants.BALANCE_PARAMETERS.CHARGESTATION_APPROACH_SPEED;
      original_speed = speed;
      this.useBrakes = useBrakes;
      this.backNudgeTimer = new Timer();
  }

  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   * @param speed Drive speed (forward is positive, default)
   */
  public DriveForwardToBalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, BrakeSubsystem brakeSubsystem, double speed,boolean useBrakes) {
    super(dsubsys,nsubsys,brakeSubsystem);
    drive = dsubsys;
    navx = nsubsys;
    brake = brakeSubsystem;
    this.speed = speed;
    original_speed = speed;
    this.useBrakes = useBrakes;
    this.backNudgeTimer = new Timer();
  }
}
