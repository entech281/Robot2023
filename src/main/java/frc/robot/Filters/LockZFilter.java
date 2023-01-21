package frc.robot.Filters;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class LockZFilter extends Filter {
    private final double startingZeroZ;
    private Gyro gyro;
    private boolean enable = false;

    public LockZFilter(Gyro Gyro) {
        gyro = Gyro;
        startingZeroZ = gyro.getAngle();
    }

    public void setEnabled(boolean enabled) {
        enable = enabled;
    }

    public boolean getEnabled() {
        return enable;
    }

    public void filter(DriveInput DI) {
        if (!enable) {
            return;
        }
        double trueAngle = gyro.getAngle() % 360;
        double outputZ = startingZeroZ - trueAngle;

        if (outputZ < 2 && outputZ > -2) {
            DI.setZ(outputZ);
        } else {
            DI.setZ(0.0);
        }
    }
}
