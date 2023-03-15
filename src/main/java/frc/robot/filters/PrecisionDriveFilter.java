package frc.robot.filters;

import frc.robot.RobotConstants;

/**
 * Changes forward, right, rotation
 * 
 * @author aheitkamp
 */
public class PrecisionDriveFilter extends DriveInputFilter {
    @Override
    protected DriveInput doFilter(DriveInput inputDI) {
        if (!isEnabled()) {
            return inputDI;
        }
        DriveInput outDI = new DriveInput(inputDI);
        outDI.setForward(inputDI.getForward() * RobotConstants.DRIVE.PRECISION_DRIVE_FACTOR);
        outDI.setRight(inputDI.getRight() * RobotConstants.DRIVE.PRECISION_DRIVE_FACTOR);
        outDI.setRotation(inputDI.getRotation() * RobotConstants.DRIVE.PRECISION_DRIVE_FACTOR);
        return outDI;
    }
}
