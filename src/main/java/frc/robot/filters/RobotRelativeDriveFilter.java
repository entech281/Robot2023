package frc.robot.filters;

/**
 * Changes the yawAngle inside DriveInput
 */
public class RobotRelativeDriveFilter extends DriveInputFilter{

	@Override
	protected DriveInput doFilter(DriveInput input) {
		DriveInput robotRelative = new DriveInput(input);
		robotRelative.setYawAngleDegrees(0.0);
		return robotRelative;
	}

}
