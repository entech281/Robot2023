/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.pose.NavxPose;
/**
 *
 * @author dcowden
 */
public class NavXSubSystem extends EntechSubsystem {

    private final AHRS navX = new AHRS(SPI.Port.kMXP);
    
    public NavXSubSystem() {
    }

    @Override
    public void initialize() {
        DriverStation.reportWarning("NavX Initialize Start", false);
        while (navX.isCalibrating()) {
          ;
        }
        navX.zeroYaw();
        DriverStation.reportWarning("NavX Initialize Complete", false);
    }

    public NavxPose getNavxOutput(){
        return new NavxPose(getAngle(), new Pose2d());
    }
    
    public double getAngle() {
        return navX.getYaw();
    }

    public void zeroYaw() {
        navX.zeroYaw();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("NavX Yaw", navX.getYaw());
    }

    public Gyro getGyro() {
      return navX;
    }
}
