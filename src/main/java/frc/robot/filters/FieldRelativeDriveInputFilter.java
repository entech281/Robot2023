package frc.robot.filters;

/**
 * Changes the yawAngle inside DriveInput
 */
public class FieldRelativeDriveInputFilter extends DriveInputFilter{

	@Override
	protected DriveInput doFilter(DriveInput input) {
		DriveInput fieldRelative = new DriveInput(input);
		fieldRelative.setYawAngleDegrees(0.0);
		return fieldRelative;
	}

}
