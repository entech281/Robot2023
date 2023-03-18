package frc.robot.filters;

/**
 * Changes the yawAngle
 */
public class RobotRelativeDriveFilter extends DriveInputFilter{

	@Override
	protected DriveInput doFilter(DriveInput inputDI) {
		DriveInput outDI = new DriveInput(inputDI);
		outDI.setYawAngleDegrees(0.0);
		return outDI;
	}

}
