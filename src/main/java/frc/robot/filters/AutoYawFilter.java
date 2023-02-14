package frc.robot.filters;

import edu.wpi.first.math.controller.PIDController;

/**
 *
 * 
 * @author aheitkamp
 */
public class AutoYawFilter extends DriveInputFilter {

    private static final double P_GAIN = 0.095;
    private static final double I_GAIN = 0.15;
    private static final double D_GAIN = 0.0085;
    private static final double ANGLE_TOLERANCE = 2;
    private static final double SPEED_LIMIT = 0.75;
    private static final double JITTER_ROUNDING = 4;

    private PIDController pid;

    public AutoYawFilter() {
        pid = new PIDController(P_GAIN, I_GAIN, D_GAIN);
        pid.setTolerance(ANGLE_TOLERANCE);
        pid.enableContinuousInput(-180, 180);
    }

    public DriveInput doFilter(DriveInput original) {

        if (Math.abs(original.getForward()) < 0.1 && Math.abs(original.getRight()) < 0.1) {
//            resetVariables();
            return original;
        }

//        if (original.getOverrideAutoYaw()) {
//            resetVariables();
//            return original;
//        }

        double setPoint = computeSetPoint(original);

        double calcValue = pid.calculate(original.getYawAngleDegrees(), setPoint);
        calcValue = Math.round(calcValue/JITTER_ROUNDING) * JITTER_ROUNDING;

        DriveInput newDi = new DriveInput(original);        		
        		
//        newDi.setOverrideYawLock(true);
        newDi.setRotation(Math.max(-SPEED_LIMIT, Math.min(calcValue, SPEED_LIMIT)));
        return newDi;
    }


    private double computeSetPoint( DriveInput original ) {
        double setPoint = Math.toDegrees(Math.atan2(original.getRight(), original.getForward()));

        if (Math.abs(setPoint - original.getYawAngleDegrees()) > 90) {
            setPoint += 180;
        }
        return setPoint;
    }
    
//    @Override
//    protected void resetVariables() {
//        pid.reset();
//    }
}
