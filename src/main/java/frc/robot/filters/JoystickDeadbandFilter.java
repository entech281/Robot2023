package frc.robot.filters;

public class JoystickDeadbandFilter extends DriveInputFilter{

	@Override
	protected DriveInput doFilter(DriveInput input) {
		return input;
	}

}
