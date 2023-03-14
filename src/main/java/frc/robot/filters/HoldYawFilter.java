package frc.robot.filters;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controllers.RobotYawPIDController;

/**
 * sets the rotation of the DriveInput
 * 
 * @author mandrews
 */
public class HoldYawFilter extends DriveInputFilter {

    private RobotYawPIDController pid;
    private static final double INVALID_ROT = 99.0;
    private static final double P_GAIN = 0.01;
    private static final double I_GAIN = 0.0;
    private static final double D_GAIN = 0.0;
    private double yawSetPoint = 0.0;
    private boolean setPointValid;
    private final boolean active = false;

    public HoldYawFilter() {
        pid = new RobotYawPIDController(P_GAIN, I_GAIN, D_GAIN);
        setPointValid = false;
    }

    public DriveInput doFilter(DriveInput original) {

        DriveInput newDi = new DriveInput(original);        		
        double rot = INVALID_ROT;
        if (setPointValid) {
            rot = pid.calculate(original.getYawAngleDegrees(), yawSetPoint);
            if (active) {
                newDi.setRotation(pid.calculate(rot));
            }
        }
        SmartDashboard.putNumber("HoldYaw meas", original.getYawAngleDegrees());
        SmartDashboard.putNumber("HoldYaw setp", yawSetPoint);
        SmartDashboard.putNumber("HoldYaw rot", rot);
        return newDi;
    }

    public void updateSetpoint( double yaw ) {
        yawSetPoint = yaw;
        setPointValid = true;
    }

    public boolean isSetpointValid() {
        return setPointValid;
    }

    public boolean isActive() {
        return active;
    }

    public void reset() {
        pid.reset();
    }

}
