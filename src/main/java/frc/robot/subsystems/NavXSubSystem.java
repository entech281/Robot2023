/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 * @author dcowden
 */
public class NavXSubSystem extends EntechSubsystem {

    private final AHRS navX = new AHRS(SPI.Port.kMXP);
    private double angle_scale = 1.0;
    private double latestYawAngle = 0.0;
    
    
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

    public double getAngle() {
        return latestYawAngle;
    }

    public void zeroYaw() {
        navX.zeroYaw();
    }

    public void flipOutputAngle(boolean flip) {
      if (flip) {
        angle_scale = -1.0;
      } else {
        angle_scale = 1.0;
      }
    }

    @Override
    public void periodic() {
        latestYawAngle = angle_scale*navX.getYaw();
        SmartDashboard.putNumber("NavX", latestYawAngle);
    }    

    public static double findNearestQuadrant(double angle){
        if (angle <= -135.0) {
          return -180.0;
        } else if (angle <= -45.0) {
          return -90.0;
        } else if (angle <= 45.0) {
          return 0.0;
        } else if (angle <= 135.0) {
          return 90.0;
        } else {
          return 180.0;
        }        
    }
    
    public static double findNearestRocketSide(double angle){
        if (angle < -81.25) {
           return -140.25;
        } else if (angle < 0) {
            return -22.25;
        } else if (angle < 81.25) {
            return 22.25;
        } else{
            return 151.25;
        }        
    }    
    
    public double findNearestAngledQuadrant(){
        return findNearestRocketSide(angle_scale * navX.getYaw());
    }
    public double findNearestQuadrant() {
        return findNearestQuadrant(angle_scale * navX.getYaw());
    }

    public Gyro getGyro() {
      return navX;
    }
}
