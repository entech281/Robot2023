// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.filters.DriveInput;
import frc.robot.subsystems.BrakeSubsystem;
import frc.robot.subsystems.BrakeSubsystem.BrakeState;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;
import frc.robot.utils.CrosshairController;

/**
 * 
 * 
 * Tries to auto balence on the docking station using a CrosshairController
 * @author aheitkamp
 */
public class DriveCrosshairBalence extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final NavXSubSystem navx;
  private final BrakeSubsystem brake;
  private CrosshairController chc;
  private static final double FIRST_STAGE_DISTANCE = 2.425;
  private static final double DEGREES_TOLERANCE = 10;
  private static final double P_GAIN = 0.175;
  private static final double MIN_SPEED = -0.15;
  private static final double MAX_SPEED = 0.375;
  private static final double RAMMING_PRECENT = 0.8;
  private static final double THRESHOLD = 0.03;

  /**
   * Creates a new DriveCrosshairBalence.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
   * @param brakeSubsystem for the command to prepare to deploy it after moving
   */
  public DriveCrosshairBalence(DriveSubsystem dsubsys, NavXSubSystem nsubsys, BrakeSubsystem brakeSubsystem) {
	  super(dsubsys,nsubsys,brakeSubsystem);
    drive = dsubsys;
    navx = nsubsys;
    brake = brakeSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    chc = new CrosshairController();
    chc.setPGain(P_GAIN);
    chc.setMinSpeed(MIN_SPEED);
    chc.setMaxSpeed(MAX_SPEED);
    chc.setRammingPrecent(RAMMING_PRECENT);
    chc.setThreshold(THRESHOLD);
    chc.setStartingError(FIRST_STAGE_DISTANCE);
    chc.addCondition((() -> {return Math.abs(navx.getPitch()) <= DEGREES_TOLERANCE; }));
    drive.setDriveMode(DriveMode.BRAKE);
    drive.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveInput di = new DriveInput(0.0, 0.0, 0.0, navx.getYaw());
    di.setForward(chc.calculate(FIRST_STAGE_DISTANCE - Math.abs(drive.getAverageDistanceMeters())));
    drive.drive(di);
    //SmartDashboard.putNumber("Crosshair dist", drive.getAverageDistanceMeters());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
    brake.setBrakeState(BrakeState.kRetract);
  	drive.stop();	  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      return chc.isFinnished();
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
