package frc.robot.filters;

import frc.robot.pose.RobotPose;

/**
 *
 * 
 * @author aheitkamp
 */
public class TurnToggleFilter extends Filter {

    public TurnToggleFilter() {
    }

    @Override
    protected void doFilter(DriveInput di, RobotPose rp) {
        if (!di.getOverrideYawLock()) {
            di.setRotation(0);
        }
    }

    @Override
    protected void resetVariables() {}
}
