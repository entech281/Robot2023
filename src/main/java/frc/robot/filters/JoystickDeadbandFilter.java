package frc.robot.filters;

/**
 * Changes forward, right, and rotation in DriveInput
 */
public class JoystickDeadbandFilter extends DriveInputFilter {

    private double minMagnitude;

    public JoystickDeadbandFilter() {
        this.minMagnitude = 0.1;
    }

    public JoystickDeadbandFilter(double minMagnitude) {
        this.minMagnitude = minMagnitude;
    }

	@Override
	protected DriveInput doFilter(DriveInput inputDI) {
        if (!isEnabled()) {
            return inputDI;
        }
        DriveInput output = new DriveInput(inputDI);
        output.setForward(applyDeadband(output.getForward(), minMagnitude));
        output.setRight(applyDeadband(output.getRight(), minMagnitude));
        output.setRotation(applyDeadband(output.getRotation(), minMagnitude));
		return output;
	}

    public void setDeadband(double minMagnitude) {
        this.minMagnitude = minMagnitude;
    }

    private double applyDeadband(double value, double min) {
        if (Math.abs(value) < Math.abs(min)) return 0.0;
        return value;
    }

}
