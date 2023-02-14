package frc.robot.filters;

/**
 *
 * 
 * @author aheitkamp
 */
public abstract class Filter {
    protected boolean enable;

    protected abstract DriveInput doFilter(DriveInput di);
    protected abstract void resetVariables();

    public final DriveInput filter(DriveInput original) {
        if (!enable) {
            resetVariables();
            return original;
        }

        return doFilter(original);
    };

    public void setEnabled(boolean enabled) {
        enable = enabled;
    }

    public boolean getEnabled() {
        return enable;
    };
}
