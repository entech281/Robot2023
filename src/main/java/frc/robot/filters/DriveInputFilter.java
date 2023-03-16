package frc.robot.filters;

/**
 *
 * 
 * @author aheitkamp
 */
public abstract class DriveInputFilter {
    protected boolean enabled = true;

    protected abstract DriveInput doFilter(DriveInput di);

    public final DriveInput filter(DriveInput original) {
    	if ( enabled ) {
    		return doFilter(original);
    	}
    	else {
    		return original;
    	}
        
    };

    public void enable(boolean enabled) {
    	this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    };
}
