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
    protected DriveInput doFilter(DriveInput di) {
    	
        	DriveInput locked = new DriveInput(di);
        	locked.setRotation(0.0);
            return locked;
    }

}
