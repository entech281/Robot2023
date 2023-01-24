package frc.robot.filters;

public class TurnToggleFilter extends Filter {

    public TurnToggleFilter() {}

    public void filter(DriveInput DI) {
        if (!enable) {
            return;
        }
        
        DI.setZ(DI.getY());
        DI.setY(0);
    }
}
