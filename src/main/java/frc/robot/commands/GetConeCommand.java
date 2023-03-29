// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.DriveSubsystem.DriveMode;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;

import java.util.Optional;

import org.opencv.core.Point;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** An example command that uses an example subsystem. */
public class GetConeCommand extends EntechCommandBase {
  private final DriveSubsystem drive;
  private final VisionSubsystem vision;
  private PIDController lateralPID;
  private PIDController forwardPID;
  
  public double TOLERANCE = 2.0;
  private double fwdReference = REFERENCE.FWD;
  private Point lastPoint;
  public double getFwdReference() {
	return fwdReference;
}

public void setFwdReference(double fwdReference) {
	this.fwdReference = fwdReference;
}

public static interface REFERENCE{
	  public static double LATERAL=160.0;
	  public static double FWD=120;
  }
  
  public static interface LATERAL_GAINS{
	  public static double P = 0.0004;
	  public static double I = 0.0;
	  public static double D = 0.0;
  }
  
  public static interface FWD_GAINS{
	  public static double P = 0.0004;
	  public static double I = 0.0;
	  public static double D = 0.0;
  }
  
  private double getLastX() {
	  if ( lastPoint == null ) {
		  return -1.0;
	  }
	  else {
		  return lastPoint.x;
	  }
  }
  private double getLastY() {
	  if ( lastPoint == null ) {
		  return -1.0;
	  }
	  else {
		  return lastPoint.y;
	  }
  }  
  /**
   * Drives by certain time in the directions given.  Negative time means opposite direction (so backward or left)
   *
   * @param subsystem The subsystem used by this command.
   */
  public GetConeCommand(DriveSubsystem drive, VisionSubsystem vision) {
      super(drive);
      this.drive = drive;
      this.vision = vision;
  }

  @Override
    public void initialize() {
	  lateralPID = new PIDController(LATERAL_GAINS.P,LATERAL_GAINS.I, LATERAL_GAINS.D);
	  forwardPID = new PIDController(FWD_GAINS.P,FWD_GAINS.I, FWD_GAINS.D);
	  forwardPID.setSetpoint(0);
	  lateralPID.setSetpoint(0);
	  lateralPID.setTolerance(TOLERANCE);
	  forwardPID.setTolerance(TOLERANCE);
	  vision.setColorMode();
	  lateralPID.reset();
	  forwardPID.reset();
	  SmartDashboard.putData("LateralConePID",lateralPID);
	  SmartDashboard.putData("ForwardConePID",forwardPID);
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveInput di = new DriveInput(0,0,0.,0.);
	Optional<Point> coneCenter = vision.getColoredObjectCenter();
	
	if ( coneCenter.isPresent()) {
		Point center = coneCenter.get();
		double lateral = lateralPID.calculate(center.x - REFERENCE.LATERAL );
		double fwd = forwardPID.calculate(center.y - REFERENCE.FWD );
		di.setRight(lateral);
		di.setForward(fwd);
	}
    
    drive.drive(di);
  }

  @Override
public void initSendable(SendableBuilder builder) {
	super.initSendable(builder);
	builder.addDoubleProperty("fwdRef", this::getFwdReference, this::setFwdReference);
	builder.addDoubleProperty("X", this::getLastX, null);
	builder.addDoubleProperty("Y", this::getLastY, null);
}

// Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stop();
    vision.setAprilTagMode();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
	  //return false;
	  return lateralPID.atSetpoint() && forwardPID.atSetpoint();
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}