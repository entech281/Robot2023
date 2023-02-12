package frc.robot.filters;

/**
 *
 * 
 * @author aheitkamp
 */
public class TurnToggleFilter extends Filter {

    public TurnToggleFilter() {
    }

    @Override
    protected DriveInput doFilter(DriveInput di) {
    	
        if (!di.getOverrideYawLock()) {
        	DriveInput locked = new DriveInput(di);
        	locked.setRotation(0.0);
            return locked;
        }
        else {
        	return di;
        }
    }

    @Override
    protected void resetVariables() {}
}
