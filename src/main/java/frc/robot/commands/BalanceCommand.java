// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.BrakeSubsystem;
import frc.robot.subsystems.BrakeSubsystem.BrakeState;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;

public class BalanceCommand extends EntechCommandBase {

  private final DriveSubsystem drive;
  private final NavXSubSystem navx;
  private BrakeSubsystem brake;
  private PIDController pid;

  public static final double P_GAIN = 0.03;
  public static final double I_GAIN = 0.001;
  public static final double D_GAIN = 0.0;
  public static final double ANGLE_TOLERANCE = 1.0;
  /**
   * Creates a new DriveForwardToBalanceCommand.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   */
  public BalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, BrakeSubsystem brakeSubsystem) {
	  super(dsubsys,nsubsys,brakeSubsystem);
      drive = dsubsys;
      navx = nsubsys;
      brake = brakeSubsystem;
  }

  /**
   * Creates a new DriveForwardToBalanceCommand.
   * Designed to be run immediately after deadrec
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   * @param speed Drive speed (forward is positive, default)
   */
  public BalanceCommand(DriveSubsystem dsubsys, NavXSubSystem nsubsys, BrakeSubsystem brakeSubsystem, double speed,boolean useBrakes) {
    super(dsubsys,nsubsys,brakeSubsystem);
    drive = dsubsys;
    navx = nsubsys;
    brake = brakeSubsystem;
}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
	  pid = new PIDController(P_GAIN, I_GAIN, D_GAIN);
	  pid.setTolerance(ANGLE_TOLERANCE);
	  brake.setBrakeState(BrakeState.kRetract);
  }

  //lets assume we are driving forward
  //also assume we are within about 3 degrees
  // 3 degrees * KP = 0.1 --> KP = 0.03
  @Override
  public void execute() {

	double out = pid.calculate(navx.getPitch());
    DriveInput di=new DriveInput(out,0.0,0.0, 0);
    drive.drive(di);
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
      return pid.atSetpoint();
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
