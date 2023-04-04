// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.BrakeSubsystem;
import frc.robot.subsystems.BrakeSubsystem.BrakeState;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;

public class DriveCSForwardBalanceCommand extends EntechCommandBase {
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
  public DriveCSForwardBalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, BrakeSubsystem bsubsys, boolean useBrakes) {
	  super(dsubsys,nsubsys,bsubsys);
      this.drive = dsubsys;
      this.navx = nsubsys;
      this.brake = bsubsys;
      speed = RobotConstants.BALANCE_PARAMETERS.BALANCE_SPEED;
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
  public DriveCSForwardBalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, BrakeSubsystem bsubsys, double speed,boolean useBrakes) {
    super(dsubsys,nsubsys,bsubsys);
    this.drive = dsubsys;
    this.navx = nsubsys;
    this.brake = bsubsys;
    this.speed = speed;
    original_speed = speed;
    this.useBrakes = useBrakes;
    this.backNudgeTimer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pitch_seen = false;
    pitch_stable_count = 0;
    speed = original_speed;
    drive.setDriveMode(DriveMode.BRAKE);
    backNudgeTimer.stop();
    backNudgeTimer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double pitch_angle = navx.getPitch();
    if (Math.abs(pitch_angle) < RobotConstants.BALANCE_PARAMETERS.BALANCE_PITCH_THRESHOLD) {
        drive.stop();
        brake.setBrakeState(BrakeState.kDeploy);
        pitch_stable_count += 1;
    } else {
        brake.setBrakeState(BrakeState.kRetract);
        pitch_stable_count = 0;
        DriveInput di=new DriveInput(Math.copySign(this.speed, pitch_angle),0.0,0.0, 0.0);
        drive.drive(di);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
    brake.setBrakeState(BrakeState.kDeploy);
    drive.stop();	  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (DriverStation.isAutonomous() && 
        ( DriverStation.getMatchTime() < RobotConstants.BALANCE_PARAMETERS.DEPLOY_BRAKE_AUTO_TIME_REMAINING)) {
      DriverStation.reportWarning("AUTONOMOUS ENDING: DEPLOY BRAKE",false);
      return true;
    }
    if (pitch_stable_count > RobotConstants.BALANCE_PARAMETERS.BALANCE_STABLE_COUNT) {
        DriverStation.reportWarning("END:" + this,false);
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
