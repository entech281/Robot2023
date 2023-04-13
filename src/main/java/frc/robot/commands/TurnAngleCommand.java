// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;

/** A command that will turn the robot to the specified angle. */
public class TurnAngleCommand extends EntechCommandBase {

	 public static final double P = 0.01;
	 public static final double I = 0.0;
	 public static final double D = 0.0;
	 private double targetAngleDegrees = 0.0;
	 private DriveSubsystem drive;
	 private NavXSubSystem navx;
	 private PIDController controller;
	/**
   * Turns to robot to the specified angle.
   *
   * @param targetAngleDegrees The angle to turn to
   * @param drive The drive subsystem to use
   */
  public TurnAngleCommand(double targetAngleDegrees, DriveSubsystem drive, NavXSubSystem navx) {
	  this.drive = drive;
	  this.navx = navx;
	  this.targetAngleDegrees = targetAngleDegrees;
	  controller = new PIDController(P,I,D);
	  SmartDashboard.putData("TurnAnglePID",controller);
  }

  @Override
	public void initialize() {	 
	  controller.setSetpoint(targetAngleDegrees);
	  controller.enableContinuousInput(-180, 180);
	  controller.setTolerance(5.0,10.0);
	  controller.reset();
	}

@Override
public String getName() {
	return "TurnAngleCommand->" + targetAngleDegrees;
}

@Override
public void execute() {
	double out = controller.calculate(navx.getYawAngleDegrees()); 
	DriverStation.reportWarning("P=" + controller.getP()+ ",setpoint=" + controller.getSetpoint(), false);
	drive.drive(new DriveInput(0,0,out,0));
}



@Override
public void end(boolean interrupted) {
	DriverStation.reportWarning("TurnAngle: Exit at angle" + navx.getYawAngleDegrees() , false);
}

@Override
  public boolean isFinished() {	
    return controller.atSetpoint();
  }
}