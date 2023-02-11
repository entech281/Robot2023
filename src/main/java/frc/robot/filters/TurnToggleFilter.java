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
    protected void doFilter(DriveInput di) {
        if (!di.getOverrideYawLock()) {
            di.setRotation(0);
        }
    }

    @Override
    protected void resetVariables() {}
}
