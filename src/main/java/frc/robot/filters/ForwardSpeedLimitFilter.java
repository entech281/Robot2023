package frc.robot.filters;

/**
 * Changes forward, right, and rotation in DriveInput
 */
public class ForwardSpeedLimitFilter extends DriveInputFilter {

    private double maxSpeedPercent = 1.0;

    
    public ForwardSpeedLimitFilter() {

    }


	@Override
	protected DriveInput doFilter(DriveInput inputDI) {
        DriveInput output = new DriveInput(inputDI);
        if ( inputDI.getForward() > 0 ) {
        	output.setForward(Math.min(maxSpeedPercent, inputDI.getForward()));
        }

		return output;
	}

    public void setMaxSpeedPercent(double maxSpeedPercent) {
        this.maxSpeedPercent = maxSpeedPercent;
    }


}
