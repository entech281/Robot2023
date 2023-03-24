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
  private static final double PITCH_SLOW_THRESHOLD = 18.0;
  private static final double PITCH_FLAT_THRESHOLD = 12.0;
  private static final double SPEED_AFTER_PITCH = 0.15;
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
      speed = RobotConstants.DRIVE.BALANCE_APPROACH_SPEED;
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

    DriveInput di=new DriveInput(speed,0.0,0.0, navx.getYaw());
    double pitch_angle = navx.getPitch();
    if (!pitch_seen) {
        if (Math.abs(pitch_angle) > PITCH_SLOW_THRESHOLD) {
            pitch_seen = true;
            this.speed = SPEED_AFTER_PITCH;
            this.start_slowdown = drive.getAverageDistanceMeters();
            this.speed = calculateSpeed(Math.abs(drive.getAverageDistanceMeters()-start_slowdown), 0.25*CHARGESTATION_DEPTH, original_speed, SPEED_AFTER_PITCH);
        }
        drive.driveFilterYawOnly(di);
    } else {
        if (Math.abs(pitch_angle) < PITCH_FLAT_THRESHOLD) {
            if (pitch_stable_count == 0) {
                backNudgeTimer.start();
            }
            if (backNudgeTimer.get() < BACK_NUDGE_TIME) {
               di.setForward(Math.copySign(BACK_NUDGE_SPEED,-speed));
               di.setRight(0.0);
               di.setRotation(0.0);
               drive.drive(di);
            } else {
               drive.stop();
               if ( useBrakes ) {
            	   brake.setBrakeState(BrakeState.kDeploy);
               }
            }
            pitch_stable_count += 1;
        } else {
            if ( useBrakes ) {        	
            	brake.setBrakeState(BrakeState.kRetract);
            }
            pitch_stable_count = 0;
            backNudgeTimer.stop();
            backNudgeTimer.reset();
            this.speed = calculateSpeed(Math.abs(drive.getAverageDistanceMeters()-start_slowdown), 0.25*CHARGESTATION_DEPTH, original_speed, SPEED_AFTER_PITCH);
            di.setForward(Math.copySign(this.speed, pitch_angle));
            drive.drive(di);
        }
    }
  }

  private double calculateSpeed(double distance, double dref, double speed0, double speed1) {
    double fraction = Math.min(distance/dref, 1.0);
    return (1.0-fraction)*speed0 + (fraction*speed1);
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
      if ( useBrakes ) {
      	brake.setBrakeState(BrakeState.kRetract);
      }
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
