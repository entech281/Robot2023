package frc.robot.filters;

import frc.robot.pose.RobotPose;

/**
 *
 * 
 * @author aheitkamp
 */
public abstract class Filter {
    protected boolean enable;

    public abstract void filter(DriveInput di, RobotPose currentPose);

    public void setEnabled(boolean enabled) {
        enable = enabled;
    }

    public boolean getEnabled() {
        return enable;
    };
}
