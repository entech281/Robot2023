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

    public void filter(DriveInput di, RobotPose currentPose) {
        if (!enable && !di.getOverrideYawLock()) {
            di.setRotation(0);
            return;
        }
    }
}
