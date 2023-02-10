package frc.robot.filters;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.pose.RobotPose;

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

    public void applyFilters(DriveInput di, RobotPose currentPose) {
        turnToggle.filter(di,currentPose);
        autoYaw.filter(di,currentPose);
    }

    public void clearFilters() {
        turnToggle.setEnabled(false);
        autoYaw.setEnabled(false);
    }

    public Filter getTurnToggle() {
        return turnToggle;
    }
}
