package frc.robot.filters;

/**
 *
 * Sets the rotation of DriveInput
 * 
 * @author aheitkamp
 */
public class NoRotationFilter extends DriveInputFilter {

    public NoRotationFilter() {
    }

    @Override
    protected DriveInput doFilter(DriveInput inputDI) {
    	if (!isEnabled()) {
            return inputDI;
        }
        DriveInput outDI = new DriveInput(inputDI);
        outDI.setRotation(0.0);
        return outDI;
    }

}
