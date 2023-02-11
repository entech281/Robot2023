package frc.robot.filters;

/**
 *
 * 
 * @author aheitkamp
 */
public abstract class Filter {
    protected boolean enable;

    protected abstract void doFilter(DriveInput di);
    protected abstract void resetVariables();

    public final void filter(DriveInput di) {
        if (!enable) {
            resetVariables();
            return;
        }

        doFilter(di);
    };

    public void setEnabled(boolean enabled) {
        enable = enabled;
    }

    public boolean getEnabled() {
        return enable;
    };
}
