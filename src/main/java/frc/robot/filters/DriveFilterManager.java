package frc.robot.filters;

import frc.robot.subsystems.NavXSubSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * 
 * @author aheitkamp
 */
public class DriveFilterManager {
    private NavXSubSystem navX;

    private LockZFilter lockZ;
    private TurnToggleFilter turnToggle;
    private AutoYawFilter autoYaw;

    public DriveFilterManager(NavXSubSystem NavX) {
        navX = NavX;

        lockZ = new LockZFilter(navX);
        autoYaw = new AutoYawFilter(navX);
        turnToggle = new TurnToggleFilter();
    }

    public void refreshFilterEnable(boolean fieldAligned) {
        autoYaw.setEnabled(fieldAligned && !(turnToggle.getEnabled()));

        SmartDashboard.putBoolean("Turn Toggle Filter", turnToggle.getEnabled());
        SmartDashboard.putBoolean("Auto Yaw Filter", autoYaw.getEnabled());
    }

    public void applyFilters(DriveInput DI) {
        lockZ.filter(DI);
        turnToggle.filter(DI);
        autoYaw.filter(DI);
    }

    public void clearFilters() {
        lockZ.setEnabled(false);
        turnToggle.setEnabled(false);
        autoYaw.setEnabled(false);
    }

    public Filter getTurnToggle() {
        return turnToggle;
    }
}
