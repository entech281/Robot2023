package frc.robot.Filters;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DriveFilterManager {
    private Gyro gyro;

    private LockZFilter lockZ;

    public DriveFilterManager(Gyro Gyro) {
        gyro = Gyro;
        lockZ = new LockZFilter(gyro);
    }

    public void refreshFilterEnable() {

    }

    public void applyFilters(DriveInput DI) {
        lockZ.filter(DI);
    }

    public void clearFilters() {
        lockZ.setEnabled(false);
    }
}
