package frc.robot.filters;

/**
 * 
 *
 * @author aheitkamp
 */
public class FeildOrientToggle extends Filter {

    public FeildOrientToggle() {}

    @Override
    public void filter(DriveInput DI) {
        if (!enable) {
            DI.setFeildOrientated(false);
            return;
        }
        
        DI.setFeildOrientated(true);;
    }
}