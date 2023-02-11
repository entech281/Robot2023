package frc.robot.filters;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 *
 * 
 * @author aheitkamp
 */
public class AutoYawFilter extends Filter {
    private Gyro navX;
    private PIDController pid;

    public AutoYawFilter(Gyro NavX) {
        navX = NavX;

        pid = new PIDController(0.095, 0.15, 0.0085);
        pid.setTolerance(2);
        pid.enableContinuousInput(-180, 180);
    }

    @Override
    protected void doFilter(DriveInput di) {
        if (Math.abs(di.getForward()) < 0.1 && Math.abs(di.getRight()) < 0.1) {
            resetVariables();
            return;
        }

        if (di.getOverrideAutoYaw()) {
            resetVariables();
            return;
        }

        double setPoint = Math.toDegrees(Math.atan2(di.getRight(), di.getForward()));

        if (Math.abs(setPoint - navX.getAngle()) > 90) {
            setPoint += 180;
        }

        double calcValue = pid.calculate(navX.getAngle(), setPoint);
        calcValue = Math.round(calcValue/4) * 4;

        di.setOverrideYawLock(true);
        di.setRotation(Math.max(-0.75, Math.min(calcValue, 0.75)));
    }

    @Override
    protected void resetVariables() {
        pid.reset();
    }
}
