// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubSystem;

/** A command that will turn the robot to the specified angle. */
public class TurnAngleCommand extends EntechCommandBase {

	 public static final double P = 0.006;
	 public static final double I = 0.001;
	 public static final double D = 0.0;
	 public static final double TOLERANCE = 1.5;
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
  public TurnAngleCommand(DriveSubsystem drive, NavXSubSystem navx) {
	  this.drive = drive;
	  this.navx = navx;
	  controller = new PIDController(P,I,D);
	  //SmartDashboard.putData("TurnAnglePID",controller);
  }

  @Override
	public void initialize() {	 


      if ( DriverStation.getAlliance() == Alliance.Red) {
          drive.setHoldYawAngle(-135.0);
          targetAngleDegrees = -135.0;
      }
      else {
          drive.setHoldYawAngle(-45);
          targetAngleDegrees = -45;

      }	  
	  controller.setSetpoint(targetAngleDegrees);      
	  controller.enableContinuousInput(-180, 180);
	  controller.setTolerance(TOLERANCE,20.0);
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
	drive.drive(new DriveInput(0,0,-out,0));
}



@Override
public void end(boolean interrupted) {
	DriverStation.reportWarning("TurnAngle: Exit at angle" + navx.getYawAngleDegrees() , false);
	drive.drive(new DriveInput(0,0,0,0));
	drive.setHoldYawAngle(navx.getYawAngleDegrees());
}

@Override
  public boolean isFinished() {	
    //boolean isAtTolerance = Math.abs(navx.getYawAngleDegrees() - targetAngleDegrees) < TOLERANCE;
    //return isAtTolerance;
	return controller.atSetpoint();
  }
}