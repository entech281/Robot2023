package frc.robot.filters;

/**
 *
 * 
 * @author aheitkamp
 */
public class TurnToggleFilter extends DriveInputFilter {

    public TurnToggleFilter() {
    }

    @Override
    protected DriveInput doFilter(DriveInput di) {
    	
        	DriveInput locked = new DriveInput(di);
        	locked.setRotation(0.0);
            return locked;
    }

}
