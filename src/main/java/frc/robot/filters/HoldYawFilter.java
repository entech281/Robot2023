package frc.robot.filters;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controllers.RobotYawPIDController;

/**
 *
 * 
 * @author mandrews
 */
public class HoldYawFilter extends DriveInputFilter {

    private RobotYawPIDController pid;
    private static final double INVALID_ROT = 99.0;
    private static final double P_GAIN = 0.01;
    private static final double I_GAIN = 0.0;
    private static final double D_GAIN = 0.0;
    private double yawSetPoint = 9999.0;
    private boolean setPointValid;

    public HoldYawFilter() {
        pid = new RobotYawPIDController(P_GAIN, I_GAIN, D_GAIN);
        setPointValid = false;
    }

    public DriveInput doFilter(DriveInput original) {

        DriveInput newDi = new DriveInput(original);        		
        double rot = INVALID_ROT;
        if (setPointValid) {
            rot = pid.calculate(original.getRawYawAngleDegrees(), yawSetPoint);
            // newDi.setRotation(pid.calculate(rot);
        }
        SmartDashboard.putNumber("HoldYaw meas", original.getRawYawAngleDegrees());
        SmartDashboard.putNumber("HoldYaw setp", yawSetPoint);
        SmartDashboard.putNumber("HoldYaw rot", rot);
        return newDi;
    }

    public void updateSetpoint( double yaw ) {
        yawSetPoint = yaw;
        setPointValid = true;
    }

    public void reset() {
        pid.reset();
    }

}
