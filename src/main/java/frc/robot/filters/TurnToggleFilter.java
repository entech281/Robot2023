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

    public void filter(DriveInput DI, RobotPose rp) {
        if (!enable && !DI.getOverrideYawLock()) {
            DI.setRotation(0);
            return;
        }
    }
}
