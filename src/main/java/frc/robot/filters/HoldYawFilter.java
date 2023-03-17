package frc.robot.filters;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controllers.RobotYawPIDController;

/**
 * Changes the rotation
 * 
 * @author mandrews
 */
public class HoldYawFilter extends DriveInputFilter implements Sendable {

    private RobotYawPIDController pid;
    private double yawSetPoint = 0.0;
    private boolean setPointValid;
    private double rot;
    private double measure;
    private static final double P_GAIN = 0.02;
    private static final double MAX_ROT = 0.2;

    public HoldYawFilter() {
        pid = new RobotYawPIDController();
        pid.reset();
        setPointValid = false;
    }

    public DriveInput doFilter(DriveInput inputDI) {

        if (!isEnabled() || !setPointValid) {
            return inputDI;
        }
        DriveInput outDI = new DriveInput(inputDI);
        measure = inputDI.getYawAngleDegrees();
        rot = pid.calculate(measure);
        // rot = P_GAIN*(inputDI.getYawAngleDegrees() - yawSetPoint);
        if (Math.abs(rot) > MAX_ROT) {
            rot = Math.copySign(MAX_ROT, rot);
        }
        outDI.setRotation(rot);
        SmartDashboard.putNumber("HoldYaw meas", measure);
        SmartDashboard.putNumber("HoldYaw setp", yawSetPoint);
        SmartDashboard.putNumber("HoldYaw rot", rot);
        return outDI;
    }

    public void updateSetpoint( double yaw ) {
        yawSetPoint = yaw;
        pid.setSetpoint(yawSetPoint);
        setPointValid = true;
    }

    public boolean isSetpointValid() {
        return setPointValid;
    }

    public void reset() {
        pid.reset();
    }

	@Override
    public void initSendable(SendableBuilder builder) {
  	    builder.setSmartDashboardType("HoldYawFilter");
  	    builder.addBooleanProperty("Status:", this::isEnabled , null);		  
  	    builder.addDoubleProperty("setp", ()->{return yawSetPoint;}, null);
  	    builder.addDoubleProperty("meas", ()->{return measure;}, null);
  	    builder.addDoubleProperty("rot", ()->{return rot;}, null);  	    

    }
    
}
