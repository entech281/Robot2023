/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
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
  // private static final int AMP_CURRENT_LIMIT = 25;

  private RelativeEncoder frontLeftEncoder;
  private RelativeEncoder rearLeftEncoder;
  private RelativeEncoder frontRightEncoder;
  private RelativeEncoder rearRightEncoder;

  private CANSparkMax frontLeftSparkMax;
  private CANSparkMax rearLeftSparkMax;
  private CANSparkMax frontRightSparkMax;
  private CANSparkMax rearRightSparkMax;
  private MecanumDrive robotDrive;
  
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
    robotDrive         = new MecanumDrive(frontLeftSparkMax, rearLeftSparkMax, frontRightSparkMax, rearRightSparkMax);

    robotDrive.setDeadband(0.1);

    frontLeftSparkMax.setInverted(false);
    rearLeftSparkMax.setInverted(false);
    frontRightSparkMax.setInverted(true);
    rearRightSparkMax.setInverted(true);
    
    frontLeftSparkMax.setSmartCurrentLimit(DRIVE.CURRENT_LIMIT_AMPS);
    rearLeftSparkMax.setSmartCurrentLimit(DRIVE.CURRENT_LIMIT_AMPS);
    frontRightSparkMax.setSmartCurrentLimit(DRIVE.CURRENT_LIMIT_AMPS);
    rearRightSparkMax.setSmartCurrentLimit(DRIVE.CURRENT_LIMIT_AMPS);    


    frontLeftEncoder = frontLeftSparkMax.getEncoder();
    rearLeftEncoder = rearLeftSparkMax.getEncoder();
    frontRightEncoder = frontRightSparkMax.getEncoder();
    rearRightEncoder = rearRightSparkMax.getEncoder();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Front Left SparkMax", frontLeftSparkMax.get());
    SmartDashboard.putNumber("Front Right SparkMax", frontRightSparkMax.get());
    SmartDashboard.putNumber("Back Left SparkMax", rearLeftSparkMax.get());
    SmartDashboard.putNumber("Back Right SparkMax", rearRightSparkMax.get());
    SmartDashboard.putNumber("Average Position", getAveragePosition());

    robotDrive.feed();
    robotDrive.feedWatchdog();
  }

  public void driveFieldAbsolute( DriveInput di, double yawAngleDegrees) {
      	  
  }
  
  public void drive(DriveInput di) {
    robotDrive.driveCartesian(di.getForward(), di.getRight(), di.getRotation(), Rotation2d.fromDegrees(di.getYawAngleDegrees()));
  }

  public void stop() {
    robotDrive.stopMotor();
  }

  public void setCoastMode() {
    frontLeftSparkMax.setIdleMode(IdleMode.kCoast);
    frontRightSparkMax.setIdleMode(IdleMode.kCoast);
    rearLeftSparkMax.setIdleMode(IdleMode.kCoast);
    rearRightSparkMax.setIdleMode(IdleMode.kCoast);
  }

  public void setBrakeMode() {
    frontLeftSparkMax.setIdleMode(IdleMode.kBrake);
    frontRightSparkMax.setIdleMode(IdleMode.kBrake);
    rearLeftSparkMax.setIdleMode(IdleMode.kBrake);
    rearRightSparkMax.setIdleMode(IdleMode.kBrake);
  }

@Override
public boolean isEnabled() {
	return true;
}
  public void resetEncoders() {
    frontLeftEncoder.setPosition(0);
    rearLeftEncoder.setPosition(0);
    frontRightEncoder.setPosition(0);
    rearRightEncoder.setPosition(0);
  }

  public double getAveragePosition() {
    double position = 0;
    position += frontLeftEncoder.getPosition();
    position += rearLeftEncoder.getPosition();
    position += frontRightEncoder.getPosition();
    position += rearRightEncoder.getPosition();
    return position / 4;
  }

  public double getAverageDistanceMeters() {
    double distance = ((getAveragePosition() / RobotConstants.DRIVE.COUNTS_PER_MOTOR_REVOLUTION) / RobotConstants.DRIVE.GEAR_BOX_RATIO) * RobotConstants.DRIVE.METERS_PER_GEARBOX_REVOLTION;
    DriverStation.reportWarning("Distance: " + distance, false);
    return distance;
  }
}
