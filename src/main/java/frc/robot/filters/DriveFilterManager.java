package frc.robot.filters;

import frc.robot.pose.RobotPose;
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
     * @param NavX The NavXSubsystem that some filters use
     */
    public DriveFilterManager() {
        autoYaw = new AutoYawFilter();
        turnToggle = new TurnToggleFilter();
    }

    public void refreshFilterEnable(boolean fieldAligned) {
        autoYaw.setEnabled(fieldAligned && !(turnToggle.getEnabled()));

        SmartDashboard.putBoolean("Turn Toggle Filter", turnToggle.getEnabled());
        SmartDashboard.putBoolean("Auto Yaw Filter", autoYaw.getEnabled());
    }

    public void applyFilters(DriveInput di, RobotPose rp) {
        turnToggle.filter(di, rp);
        autoYaw.filter(di, rp);
    }

    public void clearFilters() {
        turnToggle.setEnabled(false);
        autoYaw.setEnabled(false);
    }

    public Filter getTurnToggle() {
        return turnToggle;
    }
}
