package frc.robot.filters;

import frc.robot.subsystems.NavXSubSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * 
 * @author aheitkamp
 */
public class DriveFilterManager {
    private TurnToggleFilter turnToggle;
    private AutoYawFilter autoYaw;

    /**
     *
     * 
     * @param navX The NavXSubsystem that some filters use
     */
    public DriveFilterManager(NavXSubSystem navX) {
        autoYaw = new AutoYawFilter(navX.getGyro());
        turnToggle = new TurnToggleFilter();
    }

    public void refreshFilterEnable(boolean fieldAligned) {
        autoYaw.setEnabled(fieldAligned && !(turnToggle.getEnabled()));

        SmartDashboard.putBoolean("Turn Toggle Filter", turnToggle.getEnabled());
        SmartDashboard.putBoolean("Auto Yaw Filter", autoYaw.getEnabled());
    }

    public void applyFilters(DriveInput di) {
        turnToggle.filter(di);
        autoYaw.filter(di);
    }

    public void clearFilters() {
        turnToggle.setEnabled(false);
        autoYaw.setEnabled(false);
    }

    public Filter getTurnToggle() {
        return turnToggle;
    }
}
