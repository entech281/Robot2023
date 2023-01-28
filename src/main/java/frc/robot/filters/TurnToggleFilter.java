package frc.robot.filters;

/**
 * 
 *
 * @author aheitkamp
 */
public class TurnToggleFilter extends Filter {

    public TurnToggleFilter() {}

    public void filter(DriveInput DI) {
        if (!enable) {
            DI.setZ(0);
            return;
        }
        
        DI.setX(0);
    }
}
