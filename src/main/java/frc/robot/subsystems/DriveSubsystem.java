/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.RobotConstants;
import frc.robot.filters.DriveFilterManager;
import frc.robot.filters.DriveInput;
import frc.robot.pose.AlignmentSolution;
import frc.robot.pose.DrivePose;
import frc.robot.pose.RobotPose;

/**
 *
 * 
 * @author aheitkamp
 */
public class DriveSubsystem extends EntechSubsystem {
  private WPI_TalonSRX frontLeftTalon;
  private WPI_TalonSRX rearLeftTalon;
  private WPI_TalonSRX frontRightTalon;
  private WPI_TalonSRX rearRightTalon;
  private MecanumDrive robotDrive;
  private DriveFilterManager dfm;

  private boolean useFieldAbsolute = false;

  private DriveInput loggingDriveInput = new DriveInput(0, 0, 0);

  private double autoAlignAngle = 0.0;
  
  /**
   *
   * 
   * @param navX The NavXSubsystem that some filters use and the drive in field absolute
   */
  public DriveSubsystem() {
  }

  public DrivePose getDriveOutput(){
      return new DrivePose();
  }
  
  @Override
  public void initialize() {
    frontLeftTalon  = new WPI_TalonSRX(RobotConstants.CAN.FRONT_LEFT_MOTOR);
    rearLeftTalon   = new WPI_TalonSRX(RobotConstants.CAN.REAR_LEFT_MOTOR);
    frontRightTalon = new WPI_TalonSRX(RobotConstants.CAN.FRONT_RIGHT_MOTOR);
    rearRightTalon  = new WPI_TalonSRX(RobotConstants.CAN.REAR_RIGHT_MOTOR);
    robotDrive      = new MecanumDrive(frontLeftTalon, rearLeftTalon, frontRightTalon, rearRightTalon);
    dfm             = new DriveFilterManager();

    robotDrive.setDeadband(0.1);

    frontLeftTalon.setInverted(false);
    rearLeftTalon.setInverted(false);
    frontRightTalon.setInverted(true);
    rearRightTalon.setInverted(true);
    
    frontLeftTalon.enableCurrentLimit(false);
    rearLeftTalon.enableCurrentLimit(false);
    frontRightTalon.enableCurrentLimit(false);
    rearRightTalon.enableCurrentLimit(false);
  }

  @Override
  public void periodic() {

    SmartDashboard.putNumber("Front Left Talon", frontLeftTalon.get());
    SmartDashboard.putNumber("Front Right Talon", frontRightTalon.get());
    SmartDashboard.putNumber("Back Left Talon", rearLeftTalon.get());
    SmartDashboard.putNumber("Back Right Talon", rearRightTalon.get());

    SmartDashboard.putNumber("Driver Input Forward", loggingDriveInput.getForward());
    SmartDashboard.putNumber("Driver Input Left", loggingDriveInput.getRight());
    SmartDashboard.putNumber("Driver Input Rotation", loggingDriveInput.getRotation());

    SmartDashboard.putBoolean("Field Absolute", isFieldAbsoluteActive());


    dfm.refreshFilterEnable(isFieldAbsoluteActive());
    robotDrive.feed();
    robotDrive.feedWatchdog();
  }

  public void drive(DriveInput di, RobotPose rp) {
    loggingDriveInput = di;
    dfm.applyFilters(di, rp);
    if (isFieldAbsoluteActive()) {
      robotDrive.driveCartesian(di.getForward(), di.getRight(), di.getRotation(), rp.getCalculatedPose().getRotation());
    } else {
      robotDrive.driveCartesian(di.getForward(), di.getRight(), di.getRotation());
    }
  }

  public void activateAlignmentSolution ( AlignmentSolution solution ){
      autoAlignAngle = solution.getNeededAngle();
  }

  public double getAlignmentAngle() {
    return autoAlignAngle; 
  }

  public DriveFilterManager getDFM() {
    return dfm;
  }

  public boolean isFieldAbsoluteActive() {
    return useFieldAbsolute;
  }

  public void setFieldAbsolute(boolean active) {
    useFieldAbsolute = active;
  }

  public void toggleFieldAbsolute() {
    if (isFieldAbsoluteActive()) {
      setFieldAbsolute(false);
    } else {
      setFieldAbsolute(true);
    }
  }

  public void brake() {
    robotDrive.stopMotor();
  }
}
