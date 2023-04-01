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
public class AlignToAprilTagOffset extends EntechCommandBase {
  private final DriveSubsystem drive;
  private final VisionSubsystem vision;
  private PIDController lateralPID;
  private double lastGoodX = 0.0;
  private double lateralOut = 0.0;
  private AprilTagOffset offset;
  public double LATERAL_TOLERANCE = 10.0;


  public enum AprilTagOffset { LEFT, CENTER, RIGHT };
public static interface REFERENCE{
	  public static double LATERAL_RIGHT=55.0;
	  public static double LATERAL_LEFT=301.0;
	  public static double LATERAL_CENTER=218.0;
  }

  public static interface LATERAL_GAINS{
	  public static double P = 0.009;
	  public static double I = 0.002;
	  public static double D = 0.0;
  }

/**
   * Drives by certain time in the directions given.  Negative time means opposite direction (so backward or left)
   *
   * @param subsystem The subsystem used by this command.
   */
  public AlignToAprilTagOffset( DriveSubsystem drive, VisionSubsystem vision, AprilTagOffset offset) {
      super(drive);
      this.drive = drive;
      this.vision = vision;
	  lateralPID = new PIDController(LATERAL_GAINS.P,LATERAL_GAINS.I, LATERAL_GAINS.D);	
	  this.offset = offset;
	  setName("AlignToAprilTagOffset" + offset);
  }

  @Override
public void initSendable(SendableBuilder builder) {
	super.initSendable(builder);
	builder.addCloseable(lateralPID);
}

@Override
public void initialize() {
	if ( offset == AprilTagOffset.RIGHT) {
		lateralPID.setSetpoint(REFERENCE.LATERAL_RIGHT);
	}
	else if ( offset == AprilTagOffset.CENTER) {
		lateralPID.setSetpoint(REFERENCE.LATERAL_CENTER);
	}
	else {
		lateralPID.setSetpoint(REFERENCE.LATERAL_LEFT);
	}  
	lateralPID.setTolerance(LATERAL_TOLERANCE);
	lateralPID.reset();
}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
	if ( vision.hasTarget()) {
		lastGoodX = vision.getLastX();
	}
		
	lateralOut = -lateralPID.calculate(lastGoodX);

	DriveInput di = new DriveInput(0,lateralOut,0.,0.);
    drive.drive(di);
  }


public void populateControls(ShuffleboardTab alignTest) {
	alignTest.add(this.toString(),(Sendable)this).withSize(4, 1).withPosition(0, 0);
	alignTest.add("LateralPID", lateralPID).withSize(2, 2).withPosition(0, 1);
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
	if ( interrupted ) {
		DriverStation.reportWarning(">>>Cancelling Align Command", false);
	}
	else {
		DriverStation.reportWarning("Align DONE", false);
	}
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
	  return lateralPID.atSetpoint();
  }

  // Returns true if this command should run when robot is disabled.
  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}