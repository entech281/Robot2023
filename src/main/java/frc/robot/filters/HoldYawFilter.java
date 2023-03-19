package frc.robot.filters;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Changes the rotation
 * 
 * @author mandrews
 */
public class HoldYawFilter extends DriveInputFilter {

    private static final double P_GAIN = 0.05;
    private static final double MAX_ROT = 0.2;
    private static final double TOLERANCE = 2.0;
    private static double yawSetPoint = 0.0;
    private static boolean setPointValid;

	public HoldYawFilter() {
        setPointValid = false;
    }

    public DriveInput doFilter(DriveInput inputDI) {

        if ( !setPointValid) {
            return inputDI;
        }
        DriveInput outDI = new DriveInput(inputDI);
        
        double delta = inputDI.getYawAngleDegrees() - yawSetPoint;
        if (Math.abs(delta) > Math.abs(delta + 360.)) {
            delta += 360;
        }
        if (Math.abs(delta) > Math.abs(delta - 360.)) {
            delta -= 360;
        }
        double rot = P_GAIN*delta;
        //double rot = pid.calculate(inputDI.getYawAngleDegrees());
        if (Math.abs(rot) > MAX_ROT) {
            rot = Math.copySign(MAX_ROT, rot);
        }
        if (Math.abs(delta) < TOLERANCE) {
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
