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

    private TurnToggleFilter turnToggle;
    private AutoYawFilter autoYaw;

    /**
     *
     * 
     * @param NavX The NavXSubsystem that some filters use
     */
    public DriveFilterManager(NavXSubSystem NavX) {
        navX = NavX;

        autoYaw = new AutoYawFilter(navX.getGyro());
        turnToggle = new TurnToggleFilter();
    }

    public void refreshFilterEnable(boolean fieldAligned) {
        autoYaw.setEnabled(fieldAligned && !(turnToggle.getEnabled()));

        SmartDashboard.putBoolean("Turn Toggle Filter", turnToggle.getEnabled());
        SmartDashboard.putBoolean("Auto Yaw Filter", autoYaw.getEnabled());
    }

    public void applyFilters(DriveInput DI) {
        turnToggle.filter(DI);
        autoYaw.filter(DI);
    }

    public void clearFilters() {
        turnToggle.setEnabled(false);
        autoYaw.setEnabled(false);
    }

    public Filter getTurnToggle() {
        return turnToggle;
    }
}
