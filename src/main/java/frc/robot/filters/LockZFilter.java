package frc.robot.filters;

import frc.robot.subsystems.NavXSubSystem;

/**
 *
 * 
 * @author aheitkamp
 */
public class LockZFilter extends Filter {
    private final double startingZeroZ;
    private NavXSubSystem navX;

    public LockZFilter(NavXSubSystem NavX) {
        navX = NavX;
        startingZeroZ = navX.getAngle();
    }

    public void filter(DriveInput DI) {
        if (!enable) {
            return;
        }

        double trueAngle = navX.getAngle() % 360;
        double outputZ = startingZeroZ - trueAngle;

        if (outputZ < 2 && outputZ > -2) {
            DI.setZ(outputZ);
        } else {
            DI.setZ(0.0);
        }
    }
}
