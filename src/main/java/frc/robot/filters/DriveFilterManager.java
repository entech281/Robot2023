package frc.robot.filters;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 * 
 * @author aheitkamp
 */
public class DriveFilterManager {
    private TurnToggleFilter turnToggle;
    private AutoYawFilter autoYaw;

    public DriveFilterManager() {
        autoYaw = new AutoYawFilter();
        turnToggle = new TurnToggleFilter();
    }

    public void refreshFilterEnable(boolean fieldAligned) {
        autoYaw.setEnabled(fieldAligned && !(turnToggle.getEnabled()));

        SmartDashboard.putBoolean("Turn Toggle Filter", turnToggle.getEnabled());
        SmartDashboard.putBoolean("Auto Yaw Filter", autoYaw.getEnabled());
    }

    public DriveInput filtered(DriveInput original) {
    	DriveInput current = original;
        current = turnToggle.filter(current);
        current = autoYaw.filter(current);
        return current;
    }

    public void clearFilters() {
        turnToggle.setEnabled(false);
        autoYaw.setEnabled(false);
    }

    public Filter getTurnToggle() {
        return turnToggle;
    }
}
