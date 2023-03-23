// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.BrakeSubsystem;
import frc.robot.subsystems.BrakeSubsystem.BrakeState;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;

public class DriveForwardToBalanceCommand extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final NavXSubSystem navx;
  BrakeSubsystem brake;
  private boolean pitch_seen;
  private int pitch_stable_count;
  private double speed = 0.0;
  private double original_speed = 0.0;
  private static final int    ROBOT_STABLE_COUNT = 500;
  private static final double PITCH_THRESHOLD = 12.0;

  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   */
  public DriveForwardToBalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, BrakeSubsystem brakeSubsystem) {
      super(dsubsys,nsubsys);
      drive = dsubsys;
      navx = nsubsys;
      brake = brakeSubsystem;
      speed = RobotConstants.DRIVE.BALANCE_SPEED;
      original_speed = speed;
  }

  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   * @param speed Drive speed (forward is positive, default)
   */
  public DriveForwardToBalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, BrakeSubsystem brakeSubsystem, double speed) {
    super(dsubsys,nsubsys,brakeSubsystem);
    drive = dsubsys;
    navx = nsubsys;
    brake = brakeSubsystem;
    this.speed = speed;
    original_speed = speed;
}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pitch_seen = false;
    pitch_stable_count = 0;
    speed = original_speed;
    drive.setDriveMode(DriveMode.BRAKE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveInput di=new DriveInput(speed,0.0,0.0, navx.getYaw());
    double pitch_angle = navx.getPitch();
    if (!pitch_seen) {
        if (Math.abs(pitch_angle) > PITCH_THRESHOLD) {
            pitch_seen = true;
        }
        drive.driveFilterYawOnly(di);
    } else {
        if (Math.abs(pitch_angle) < PITCH_THRESHOLD) {
            drive.stop();
            brake.setBrakeState(BrakeState.kDeploy);
            pitch_stable_count += 1;
        } else {
        	brake.setBrakeState(BrakeState.kRetract);
            pitch_stable_count = 0;
            di.setForward(Math.copySign(speed, pitch_angle));
            drive.driveFilterYawOnly(di);
        }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
	/** NOTE:
	 * this command is used in two contexts: in auto, and activated by driver in 
	 * endgame.
	 * In auto, the balance time (ROBOT_STABLE_COUNT) is long enough that the 
	 * command will not end before autonomous ends. it will continually try to seek balance and stop
	 * 
	 * In endgame, we want the operator to have control, but to use the balancing code to mount.
	 * the operator runs this command, and then when happy holds down the brake button.
	 * The brake button will cancel this command.  We free the the brakes so that the operator
	 * can move if they choose to do so
	**/
  	brake.setBrakeState(BrakeState.kRetract);
  	drive.stop();	  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return pitch_stable_count > ROBOT_STABLE_COUNT;
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
