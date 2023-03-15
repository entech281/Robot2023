package frc.robot.filters;

import frc.robot.RobotConstants;

/**
 * Changes forward, right, rotation
 * 
 * @author aheitkamp
 */
public class PrecisionDriveFilter extends DriveInputFilter {
    @Override
    protected DriveInput doFilter(DriveInput di) {
        DriveInput newDI = new DriveInput(di);
        newDI.setForward(di.getForward() * RobotConstants.DRIVE.PRECISION_DRIVE_FACTOR);
        newDI.setRight(di.getRight() * RobotConstants.DRIVE.PRECISION_DRIVE_FACTOR);
        newDI.setRotation(di.getRotation() * RobotConstants.DRIVE.PRECISION_DRIVE_FACTOR);
        return newDI;
    }
}
