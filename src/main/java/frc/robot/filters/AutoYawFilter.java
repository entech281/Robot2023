package frc.robot.filters;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.pose.RobotPose;

/**
 *
 * 
 * @author aheitkamp
 */
public class AutoYawFilter extends Filter {
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

    public void doFilter(DriveInput di, RobotPose rp) {
        double currentYaw = rp.getCalculatedPose().getRotation().getDegrees();

        if (Math.abs(di.getForward()) < 0.1 && Math.abs(di.getRight()) < 0.1) {
            resetVariables();
            return;
        }

        if (di.getOverrideAutoYaw()) {
            resetVariables();
            return;
        }

        double setPoint = Math.toDegrees(Math.atan2(di.getRight(), di.getForward()));

        if (Math.abs(setPoint - currentYaw) > 90) {
            setPoint += 180;
        }

        double calcValue = pid.calculate(currentYaw, setPoint);
        calcValue = Math.round(calcValue/JITTER_ROUNDING) * JITTER_ROUNDING;

        di.setOverrideYawLock(true);
        di.setRotation(Math.max(-SPEED_LIMIT, Math.min(calcValue, SPEED_LIMIT)));
    }

    @Override
    protected void resetVariables() {
        pid.reset();
    }
}
