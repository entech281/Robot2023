/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.pose.NavxPose;
/**
 *
 * @author mandrews
 * 
 * This code implements the NavX sensor into proper right handed ROBOT coordinate systems
 *   X => +forward/-reverse, Y => +left/-right, Z => +up/-down, rotX (roll) => +rollRight, rotY (pitch) => +noseDown, rotZ (yaw) => +ccw  
 * Assumes the following robot and NavX installation.  MXP shows location of MXP connection on NavX
 * 
 *              ^ +X
 *              | 
 *       \\+---------+//
 *         |  +---+  |
 *   +Y    |  |MXP|  |
 *   <---- |  |   |  |
 *         |  |   |  |
 *         |  +---+  |
 *       //+---------+\\
 */
public class NavXSubSystem extends EntechSubsystem implements Gyro {

    private final AHRS navX = new AHRS(SPI.Port.kMXP);
    private double initialYawAngle = 0.0;
    
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

    public void zeroYaw() {
        navX.zeroYaw();
    }

    public double getRoll() {
      return navX.getPitch();
    }

    public double getPitch() {
      return -navX.getRoll();
    }

    public double getYaw() {
      return -navX.getYaw() + initialYawAngle;
    }

    public double getX() {
      return navX.getDisplacementX();
    }

    public double getY() {
      return navX.getDisplacementY();
    }

    public double getZ() {
      return navX.getDisplacementZ();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("NavX", getYaw());
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

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType(getName());
        builder.addDoubleProperty("NavX Yaw", this::getYaw, null);
        builder.addDoubleProperty("NavX Pitch", this::getPitch, null);
        builder.addDoubleProperty("NavX Roll", this::getRoll, null);
        builder.addDoubleProperty("NavX X", this::getX, null);
        builder.addDoubleProperty("NavX Y", this::getY, null);
        builder.addDoubleProperty("NavX Z", this::getZ, null);
    }

    @Override
    public void close() throws Exception {
      navX.close();
    }

    @Override
    public void calibrate() {
      navX.calibrate();
    }

    @Override
    public void reset() {
      navX.reset();
    }

    @Override
    public double getRate() {
      return navX.getRate();
    }

    @Override
    public double getAngle() {
      return navX.getAngle();
    }

}
