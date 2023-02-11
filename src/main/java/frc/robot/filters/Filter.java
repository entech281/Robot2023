package frc.robot.filters;

import frc.robot.pose.RobotPose;

/**
 *
 * 
 * @author aheitkamp
 */
public abstract class Filter {
    protected boolean enable;

    protected abstract void doFilter(DriveInput di, RobotPose rp);
    protected abstract void resetVariables();

    public final void filter(DriveInput di, RobotPose rp) {
        if (!enable) {
            resetVariables();
            return;
        }

        doFilter(di, rp);
    };

    public void setEnabled(boolean enabled) {
        enable = enabled;
    }

    public boolean getEnabled() {
        return enable;
    };
}
