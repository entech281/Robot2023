package frc.robot.filters;

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
    private double yawSetPoint = AUTO_SETPOINT;

    public HoldYawFilter() {
        pid = new RobotYawPIDController();
    }

    public DriveInput doFilter(DriveInput original) {

        if (Math.abs(yawSetPoint - AUTO_SETPOINT) < TOLERANCE) {
            yawSetPoint = original.getRawYawAngleDegrees();
        }
        DriveInput newDi = new DriveInput(original);        		

        newDi.setRotation(pid.calculate(original.getRawYawAngleDegrees(), yawSetPoint));
        return newDi;
    }

    public void updateSetpoint( double yaw ) {
        yawSetPoint = yaw;
    }

}
