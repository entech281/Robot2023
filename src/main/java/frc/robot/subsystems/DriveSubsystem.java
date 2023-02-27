/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;

/**
 *
 *
 * @author aheitkamp
 */
public class DriveSubsystem extends EntechSubsystem {
  private CANSparkMax frontLeftSparkMax;
  private CANSparkMax rearLeftSparkMax;
  private CANSparkMax frontRightSparkMax;
  private CANSparkMax rearRightSparkMax;
  private MecanumDrive robotDrive;
  
  /**
   *
   * 
   * @param navX The NavXSubsystem that some filters use and the drive in field absolute
   */
  public DriveSubsystem() {
  }

  public DriveStatus getStatus(){
      return new DriveStatus();
  }

  @Override
  public void initialize() {
    frontLeftSparkMax  = new CANSparkMax(RobotConstants.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
    rearLeftSparkMax   = new CANSparkMax(RobotConstants.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
    frontRightSparkMax = new CANSparkMax(RobotConstants.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
    rearRightSparkMax  = new CANSparkMax(RobotConstants.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
    robotDrive      = new MecanumDrive(frontLeftSparkMax, rearLeftSparkMax, frontRightSparkMax, rearRightSparkMax);

    robotDrive.setDeadband(0.1);

    frontLeftSparkMax.setInverted(false);
    rearLeftSparkMax.setInverted(false);
    frontRightSparkMax.setInverted(true);
    rearRightSparkMax.setInverted(true);

  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Front Left Talon", frontLeftSparkMax.get());
    SmartDashboard.putNumber("Front Right Talon", frontRightSparkMax.get());
    SmartDashboard.putNumber("Back Left Talon", rearLeftSparkMax.get());
    SmartDashboard.putNumber("Back Right Talon", rearRightSparkMax.get());
    //SmartDashboard.putNumber("Driver Input Forward", loggingDriveInput.getForward());
    //SmartDashboard.putNumber("Driver Input Left", loggingDriveInput.getRight());
    //SmartDashboard.putNumber("Driver Input Rotation", loggingDriveInput.getRotation());
    //SmartDashboard.putBoolean("Field Absolute", isFieldAbsoluteActive());
    //SmartDashboard.putNumber("Auto Align Angle", autoAlignAngle);

    robotDrive.feed();
    robotDrive.feedWatchdog();
  }

  public void driveFieldAbsolute( DriveInput di, double yawAngleDegrees) {
      	  
  }
  
  public void drive(DriveInput di) {

    robotDrive.driveCartesian(di.getForward(), di.getRight(), di.getRotation(), Rotation2d.fromDegrees(di.getYawAngleDegrees()));
  }

  public void brake() {
    robotDrive.stopMotor();
  }

  public void setCoast() {
    frontLeftSparkMax.setIdleMode(IdleMode.kCoast);
    frontRightSparkMax.setIdleMode(IdleMode.kCoast);
    rearLeftSparkMax.setIdleMode(IdleMode.kCoast);
    rearRightSparkMax.setIdleMode(IdleMode.kCoast);
  }

  public void setBrake() {
    frontLeftSparkMax.setIdleMode(IdleMode.kBrake);
    frontRightSparkMax.setIdleMode(IdleMode.kBrake);
    rearLeftSparkMax.setIdleMode(IdleMode.kBrake);
    rearRightSparkMax.setIdleMode(IdleMode.kBrake);
  }
}
