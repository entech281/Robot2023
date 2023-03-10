package frc.robot.filters;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;
import frc.robot.controllers.RobotYawPIDController;

/**
 *
 * 
 * @author mandrews
 */
public class HoldYawFilter extends DriveInputFilter {

    private RobotYawPIDController pid;
    private static final double AUTO_SETPOINT = 9999.0;
    private static final double TOLERANCE = 0.1;
    private static final double P_GAIN = 0.01;
    private static final double I_GAIN = 0.0;
    private double yawSetPoint = AUTO_SETPOINT;

    public HoldYawFilter() {
        pid = new RobotYawPIDController();
        pid.setP(P_GAIN);
        pid.setI(I_GAIN);
    }

    public DriveInput doFilter(DriveInput original) {

        if (Math.abs(yawSetPoint - AUTO_SETPOINT) < TOLERANCE) {
            yawSetPoint = original.getRawYawAngleDegrees();
        }
        DriveInput newDi = new DriveInput(original);        		

        newDi.setRotation(pid.calculate(original.getRawYawAngleDegrees(), yawSetPoint));
        SmartDashboard.putNumber("HoldYaw meas", original.getRawYawAngleDegrees());
        SmartDashboard.putNumber("HoldYaw setp", yawSetPoint);
        SmartDashboard.putNumber("HoldYaw rot", newDi.getRotation());
        return newDi;
    }

    public void updateSetpoint( double yaw ) {
        yawSetPoint = yaw;
    }

    public void reset() {
        pid.reset();
    }

}
