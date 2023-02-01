package frc.robot.filters;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.NavXSubSystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class AutoYawFilter extends Filter {
    private NavXSubSystem navX;
    private PIDController PID;
    
    public AutoYawFilter(NavXSubSystem NavX) {
        navX = NavX;

        PID = new PIDController(0.03, 0, 0.01);
        PID.setTolerance(4);
        PID.enableContinuousInput(-180, 180);
    }

    public void filter(DriveInput DI) {
        if (!enable) {
            return;
        }

        double setPoint = Math.toDegrees(Math.atan2(DI.getX(), DI.getY()));

        DI.setZ(PID.calculate(navX.getAngle(), Math.max(-1, Math.min(setPoint, 1))));
    }
}
