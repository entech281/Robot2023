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
import frc.robot.util.CrosshairController;

public class DriveCrosshairBalence extends EntechCommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final NavXSubSystem navx;
  private final BrakeSubsystem brake;
  private CrosshairController chc;
  private static final double FIRST_STAGE_DISTANCE = 2.4;
  private static final double DEGREES_TOLERANCE = 10;

  /**
   * Creates a new DriveCrosshairBalence.
   *
   * @param dsubsys Drive subsystem used by this command.
   * @param nsubsys NavX subsystem used for pitch measurement
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
    chc.setP(0.4);
    chc.setMinSpeed(0.15);
    chc.setMaxSpeed(0.4);
    chc.setRammingPrecent(0.75);
    chc.setThreshold(0.05);
    chc.setStartingError(FIRST_STAGE_DISTANCE);
    chc.addCondition((() -> {return Math.abs(navx.getPitch()) <= DEGREES_TOLERANCE; }));
    drive.setDriveMode(DriveMode.BRAKE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveInput di = new DriveInput(0.0, 0.0, 0.0, navx.getYaw());
    di.setForward(-chc.calculate(FIRST_STAGE_DISTANCE - drive.getAverageDistanceMeters()));
    drive.drive(di);
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
