package frc.robot.filters;

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
