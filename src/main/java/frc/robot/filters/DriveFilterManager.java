package frc.robot.filters;

import edu.wpi.first.wpilibj.interfaces.Gyro;

import frc.robot.RobotConstants;

/**
 * 
 *
 * @author aheitkamp
 */
public class DriveFilterManager {
    private Gyro gyro;

    private LockZFilter lockZ;
    private TurnToggleFilter turnToggle;

    public DriveFilterManager(Gyro Gyro) {
        gyro = Gyro;
        lockZ = new LockZFilter(gyro);
        turnToggle = new TurnToggleFilter();
    }

    public void refreshFilterEnable() {

    }

    public void applyFilters(DriveInput DI) {
        lockZ.filter(DI);
        turnToggle.filter(DI);
    }

    public void clearFilters() {
        lockZ.setEnabled(false);
    }

    public Filter getTurnToggle() {
        return turnToggle;
    }
}
