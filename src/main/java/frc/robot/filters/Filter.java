package frc.robot.filters;

public abstract class Filter {
    protected boolean enable;

    public abstract void filter(DriveInput DI);

    public void setEnabled(boolean enabled) {
        enable = enabled;
    }

    public boolean getEnabled() {
        return enable;
    };
}
