/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.RobotConstants;
import frc.robot.filters.DriveFilterManager;
import frc.robot.filters.DriveInput;
import frc.robot.pose.AlignmentSolution;
import frc.robot.pose.DrivePose;

public class DriveSubsystem extends EntechSubsystem {
  private WPI_TalonSRX frontLeftTalon;
  private WPI_TalonSRX rearLeftTalon;
  private WPI_TalonSRX frontRightTalon;
  private WPI_TalonSRX rearRightTalon;
  private MecanumDrive robotDrive;
  private DriveFilterManager DFM;

  private boolean useFieldAbsolute = false;

  private NavXSubSystem navX; 

  private DriveInput loggingDriveInput = new DriveInput(0, 0, 0);
  
  public DriveSubsystem(NavXSubSystem navx) {
    navX = navx;
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
    DFM             = new DriveFilterManager(navX);

    robotDrive.setDeadband(0.1);

    frontLeftTalon.setInverted(true);
    rearLeftTalon.setInverted(true);
    frontRightTalon.setInverted(false);
    rearRightTalon.setInverted(false);
    
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

    SmartDashboard.putNumber("Driver Input X", loggingDriveInput.getX());
    SmartDashboard.putNumber("Driver Input Y", loggingDriveInput.getY());
    SmartDashboard.putNumber("Driver Input Z", loggingDriveInput.getZ());

    SmartDashboard.putBoolean("Field Absolute", isFieldAbsoluteActive());


    DFM.refreshFilterEnable();
    robotDrive.feed();
    robotDrive.feedWatchdog();
  }

  public void drive(DriveInput DI) {
    loggingDriveInput = DI;
    DFM.applyFilters(DI);
    if (isFieldAbsoluteActive()) {
      robotDrive.driveCartesian(DI.getY(), DI.getX(), DI.getZ(), Rotation2d.fromDegrees(navX.getYaw()));
    } else {
      robotDrive.driveCartesian(DI.getY(), DI.getX(), DI.getZ());
    }
  }

  public void activateAlignmentSolution ( AlignmentSolution solution ){
      //use the solution to affect the drive
  }
  public DriveFilterManager getDFM() {
    return DFM;
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
}
