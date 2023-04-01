// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.Optional;

import org.opencv.core.Point;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/** An example command that uses an example subsystem. */
public class AlignToGamePieceCommand extends EntechCommandBase {
  private final DriveSubsystem drive;
  private final VisionSubsystem vision;
  private PIDController lateralPID;
  private PIDController forwardPID;


  private double fwdOut = 0.0;
  private double lateralOut = 0.0;
  
  public double LATERAL_TOLERANCE = 12.0;
  public double FWD_TOLERANCE = 3.0;

public static interface REFERENCE{
	  public static double LATERAL=160.0;
	  public static double FWD=12.0;
  }

  public static interface LATERAL_GAINS{
	  public static double P = 0.008;
	  public static double I = 0.002;
	  public static double D = 0.0;
  }

  public static interface FWD_GAINS{
	  public static double P = 0.05;
	  public static double I = 0.0;
	  public static double D = 0.0;
  }

/**
   * Drives by certain time in the directions given.  Negative time means opposite direction (so backward or left)
   *
   * @param subsystem The subsystem used by this command.
   */
  public AlignToGamePieceCommand( DriveSubsystem drive, VisionSubsystem vision) {
      super(drive);
      this.drive = drive;
      this.vision = vision;
	  lateralPID = new PIDController(LATERAL_GAINS.P,LATERAL_GAINS.I, LATERAL_GAINS.D);	  
	  forwardPID = new PIDController(FWD_GAINS.P,FWD_GAINS.I, FWD_GAINS.D);

  }

  @Override
public void initSendable(SendableBuilder builder) {
	super.initSendable(builder);
	builder.addCloseable(lateralPID);
}

@Override
    public void initialize() {
	  lateralPID.setSetpoint(REFERENCE.LATERAL);
	  forwardPID.setSetpoint(REFERENCE.FWD);
	  lateralPID.setTolerance(LATERAL_TOLERANCE);
	  forwardPID.setTolerance(FWD_TOLERANCE);
	  lateralPID.reset();
	  forwardPID.reset();
    }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriverStation.reportWarning("setpoint:" + lateralPID.getSetpoint(),false);
	Optional<Point> coneCenter = vision.getColoredObjectCenter();
	double area = vision.getLastArea();
	Point center = new Point();
	center.x = lateralPID.getSetpoint();
	center.y = forwardPID.getSetpoint();
	
	if ( coneCenter.isPresent()) {
		Point visionCenter = coneCenter.get();

		center.x = visionCenter.x;
		center.y = visionCenter.y;
	}
	lateralOut = -lateralPID.calculate(center.x  );
	fwdOut = forwardPID.calculate(area  );
	if ( area > 14.0 ) {
		fwdOut = 0;
	}

	DriveInput di = new DriveInput(0,0,0.,0.);
	if ( area > 0 ) {
		di.setForward(fwdOut);
		di.setRight(lateralOut);
	}
	
    drive.drive(di);
  }


public void populateControls(ShuffleboardTab alignTest) {
	alignTest.add(this.toString(),(Sendable)this).withSize(4, 1).withPosition(0, 0);
	alignTest.add("LateralPID", lateralPID).withSize(2, 2).withPosition(0, 1);
	alignTest.add("FWD PID", forwardPID).withSize(2, 2).withPosition(2, 1);
	alignTest.addDouble("fwdErr", () -> { return forwardPID.getPositionError();} ).withSize(1, 1).withPosition(0, 3);
	alignTest.addDouble("fwdOut", () -> { return fwdOut;} ).withSize(1, 1).withPosition(1, 3);
	alignTest.addDouble("latErr", () -> { return lateralPID.getPositionError();} ).withSize(1, 1).withPosition(2, 3);	
	alignTest.addDouble("latOut", () -> { return lateralOut;} ).withSize(1, 1).withPosition(3, 3);	
}


@Override
public InterruptionBehavior getInterruptionBehavior() {
	return InterruptionBehavior.kCancelSelf;
}

// Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
	DriverStation.reportWarning(">>>Cancelling Align Command", false);
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
	  return lateralPID.atSetpoint() && forwardPID.atSetpoint();
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}