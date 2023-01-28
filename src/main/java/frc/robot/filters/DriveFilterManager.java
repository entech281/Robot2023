package frc.robot.filters;

import edu.wpi.first.wpilibj.interfaces.Gyro;

import frc.robot.RobotConstants;

/**
 * 
 *
 * @author aheitkamp
 */
public class DriveFilterManager {
    public static interface filters {
        public static final int TURNTOGGLEFILTER = 1;
        public static final int FEILDORIENTTOGGLE = 6;
    }
    private Gyro gyro;

    private LockZFilter lockZ;
    private TurnToggleFilter turnToggle;
    private FeildOrientToggle feildOrient;

    public DriveFilterManager(Gyro Gyro) {
        gyro = Gyro;
        lockZ = new LockZFilter(gyro);
        turnToggle = new TurnToggleFilter();
        feildOrient = new FeildOrientToggle();
    }

    public void refreshFilterEnable() {

    }

    public void applyFilters(DriveInput DI) {
        lockZ.filter(DI);
        turnToggle.filter(DI);
        feildOrient.filter(DI);
    }

    public void clearFilters() {
        lockZ.setEnabled(false);
        turnToggle.setEnabled(false);
        feildOrient.setEnabled(false);
    }

    public Filter getFilter(int filterNumber) {
        switch (filterNumber) {
            case filters.TURNTOGGLEFILTER: return turnToggle;
            case filters.FEILDORIENTTOGGLE: return turnToggle;
            default: return null;
        }
    }
}
