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
    private PIDController PID;

    public AutoYawFilter(Gyro NavX) {
        navX = NavX;

        PID = new PIDController(0.095, 0.15, 0.0085);
        PID.setTolerance(2);
        PID.enableContinuousInput(-180, 180);
    }

    public void filter(DriveInput DI) {
        if (!enable) {
            PID.reset();
            return;
        }

        if (Math.abs(DI.getForward()) < 0.1 && Math.abs(DI.getLeft()) < 0.1) {
            PID.reset();
            return;
        }

        if (DI.getOverrideAutoYaw()) {
            PID.reset();
            return;
        }

        double setPoint = Math.toDegrees(Math.atan2(DI.getLeft(), DI.getForward()));

        if (Math.abs(setPoint - navX.getAngle()) > 90) {
            setPoint += 180;
        }

        double calcValue = PID.calculate(navX.getAngle(), setPoint);
        calcValue = Math.round(calcValue/4) * 4;

        DI.setOverrideYawLock(true);
        DI.setRotation(Math.max(-0.75, Math.min(calcValue, 0.75)));
    }
}
