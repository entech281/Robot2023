package frc.robot.filters;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.subsystems.NavXSubSystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class AutoYawFilter extends Filter {
    private Gyro gyro;
    private PIDController PID;

    public AutoYawFilter(Gyro gyro) {
        this.gyro = gyro;

        PID = new PIDController(0.095, 0.15, 0.0085);
        PID.setTolerance(2);
        PID.enableContinuousInput(-180, 180);
    }

    public void filter(DriveInput DI) {
        if (!enable) {
            PID.reset();
            return;
        }

        if (Math.abs(DI.getForward()) < 0.1 && Math.abs(DI.getRight()) < 0.1) {
            PID.reset();
            return;
        }

        if (DI.getOverrideAutoYaw()) {
            PID.reset();
            return;
        }

        double setPoint = Math.toDegrees(Math.atan2(DI.getRight(), DI.getForward()));

        if (Math.abs(setPoint - gyro.getAngle()) > 90) {
            setPoint += 180;
        }

        double calcValue = PID.calculate(gyro.getAngle(), setPoint);
        calcValue = Math.round(calcValue/4) * 4;

        DI.setOverrideYawLock(true);
        DI.setRotation(Math.max(-0.75, Math.min(calcValue, 0.75)));
    }
}
