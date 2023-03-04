/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.EntechUtils;
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
public class NavXSubSystem extends EntechSubsystem  {

    private final AHRS navX = new AHRS(SPI.Port.kMXP);
    private double initialYawAngleForFieldDrive = 180.0;
    private double initialYawAngleForFieldPose = 0.0;
    private double knownForwardMeters = 0.0;
    private double knownRightMeters = 0.0;
    private double knownUpMeters = 0.0;

    public NavXSubSystem() {
    }

    @Override
    public void initialize() {
        DriverStation.reportWarning("NavX Initialize Start", false);
        while (navX.isCalibrating()) {
          ;
        }
        DriverStation.reportWarning("NavX Initialize Complete", false);
        zeroYaw();
        zeroPosition();
        assignAlliance();
    }

    public void assignAlliance() {
        if (DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
            initialYawAngleForFieldPose = 180.0;
        }
    }

    public NavxStatus getStatus() {
    	return getFieldPoseStatus();

    }

    public NavxStatus getFieldAbsoluteDriveStatus() {
    	return new NavxStatus(getForward(), getRight(), getYawForFieldAbsoluteDrive(), getPitch());

    }
    
    public NavxStatus getFieldPoseStatus() {
    	return new NavxStatus(getForward(), getRight(), getYawForFieldPose(), getPitch());

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
      return getYawForFieldPose();
    }

    public double getYawForFieldAbsoluteDrive() {
        return EntechUtils.normalizeAngle(-navX.getYaw() + initialYawAngleForFieldDrive);
    }

    public double getYawForFieldPose() {
        return EntechUtils.normalizeAngle(-navX.getYaw() + initialYawAngleForFieldPose);
    }

    public double getForward() {
      return navX.getDisplacementX()+knownForwardMeters;
    }

    public double getRight() {
      return navX.getDisplacementY()+knownRightMeters;
    }

    public double getUp() {
      return navX.getDisplacementZ()+knownUpMeters;
    }

    public void updatePosition(double forward, double right) {
        zeroPosition();
        knownForwardMeters = forward;
        knownRightMeters = right;
    }

    public void zeroPosition() {
        navX.resetDisplacement();
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
        builder.addDoubleProperty("NavX Forward", this::getForward, null);
        builder.addDoubleProperty("NavX Right", this::getRight, null);
        builder.addDoubleProperty("NavX Height", this::getUp, null);
    }

	@Override
	public void simulationPeriodic() {
		// TODO Auto-generated method stub
		super.simulationPeriodic();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
