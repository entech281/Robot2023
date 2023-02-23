package frc.robot.filters;

import edu.wpi.first.math.controller.PIDController;

/**
 *
 * 
 * @author aheitkamp & mandrews
 */
public class HoldYawFilter extends DriveInputFilter {

    private static final double P_GAIN = 0.095;
    private static final double I_GAIN = 0.15;
    private static final double D_GAIN = 0.0085;
    private static final double ANGLE_TOLERANCE = 2;
    private static final double SPEED_LIMIT = 0.75;

    private PIDController pid;
    private double holdAngle;

    public HoldYawFilter() {
        super();
        pid = new PIDController(P_GAIN, I_GAIN, D_GAIN);
        pid.setTolerance(ANGLE_TOLERANCE);
        pid.enableContinuousInput(-180, 180);
    }

    public DriveInput doFilter(DriveInput original) {

        double calcValue = pid.calculate(original.getYawAngleDegrees(), holdAngle);
        DriveInput newDi = new DriveInput(original);        		

        newDi.setRotation(Math.max(-SPEED_LIMIT, Math.min(calcValue, SPEED_LIMIT)));
        return newDi;
    }

    public void reset() {
        pid.reset();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        reset();
    }

    public void setHoldAngle(double angle) {
        holdAngle = angle;
    }

}
