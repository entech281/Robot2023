package frc.robot.filters;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.util.EntechUtils;

/**
 * Changes yawAngle insider DriveInput
 * 
 * @author aheitkamp
 */
public class FieldPoseToFieldAbsoluteDriveFilter extends DriveInputFilter {

    private double angleOffset;

    public FieldPoseToFieldAbsoluteDriveFilter() {
        super();
        angleOffset = 0.0;
        if (DriverStation.getAlliance() == DriverStation.Alliance.Red) {
            angleOffset = 180.0;
        }

    }

    public DriveInput doFilter(DriveInput original) {
        DriveInput newDi = new DriveInput(original);
        newDi.setYawAngleDegrees(EntechUtils.normalizeAngle(original.getYawAngleDegrees() + angleOffset));
        return newDi;
    }

}
