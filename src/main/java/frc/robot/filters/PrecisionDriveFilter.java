package frc.robot.filters;

import frc.robot.RobotConstants;

/**
 *
 * 
 * @author aheitkamp
 */
public class PrecisionDriveFilter extends DriveInputFilter {
    @Override
    protected DriveInput doFilter(DriveInput di) {
        DriveInput newDI = new DriveInput(di);
        newDI.setForward(di.getForward() * RobotConstants.DRIVE.PERCISION_DRIVE_FACTOR);
        newDI.setRight(di.getRight() * RobotConstants.DRIVE.PERCISION_DRIVE_FACTOR);
        newDI.setRotation(di.getRotation() * RobotConstants.DRIVE.PERCISION_DRIVE_FACTOR);
        return newDI;
    }
}
