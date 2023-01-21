package frc.robot.Filters;

public abstract class Filter {
    public abstract void filter(DriveInput DI);
    public abstract void setEnabled(boolean enabled);
    public abstract boolean getEnabled();
}
