/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.pose.NavxStatus;
/**
 *
 * @author dcowden
 */
public class NavXSubSystem extends EntechSubsystem implements Sendable{

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

    @Override
    public NavxStatus getStatus(){
        NavxStatus ns = new NavxStatus();
        ns.setYawAngleDegrees(getYawAngle());
        return ns;
    }
 
    public void resetGyro() {
    	navX.reset();
    }
    private double getYawAngle() {
    	return navX.getAngle();
    }
    public void zeroYaw() {
        navX.zeroYaw();
    }

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.addDoubleProperty("NavX Yaw", this::getYawAngle , null);
	}

}
