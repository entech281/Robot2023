package frc.robot.filters;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controllers.RobotYawPIDController;

/**
 * Changes the rotation
 * 
 * @author mandrews
 */
public class HoldYawFilter extends DriveInputFilter {

    private static final double P_GAIN = 0.01;
    private static final double I_GAIN = 0.001;
    private static final double MAX_ROT = 0.2;
    private static final double TOLERANCE = 2.0;
    private static double yawSetPoint = 0.0;
    private static boolean setPointValid;
    private RobotYawPIDController pid;
    
    public HoldYawFilter() {
        setPointValid = false;
        pid = new RobotYawPIDController();
        pid.setP(P_GAIN);
        pid.setI(I_GAIN);
        pid.reset();
    }

    public DriveInput doFilter(DriveInput inputDI) {

        if ( !setPointValid) {
            return inputDI;
        }
        DriveInput outDI = new DriveInput(inputDI);
        
        //double rot = P_GAIN*(inputDI.getYawAngleDegrees() - yawSetPoint);
        double rot = pid.calculate(inputDI.getYawAngleDegrees());
        
        if (Math.abs(rot) > MAX_ROT) {
            rot = Math.copySign(MAX_ROT, rot);
        }
        if (Math.abs(inputDI.getYawAngleDegrees() - yawSetPoint) < TOLERANCE) {
        	rot = 0.0;
        }
        outDI.setRotation(rot);
        SmartDashboard.putNumber("HoldYaw meas", inputDI.getYawAngleDegrees());
        SmartDashboard.putNumber("HoldYaw setp", yawSetPoint);
        SmartDashboard.putNumber("HoldYaw rot", rot);
        return outDI;
    }

    public void updateSetpoint( double yaw ) {
        yawSetPoint = yaw;
        setPointValid = true;
    }

    public boolean isSetpointValid() {
        return setPointValid;
    }

}
