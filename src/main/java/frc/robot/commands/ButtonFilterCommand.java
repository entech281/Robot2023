package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveFilterManager;

/**
 *
 * 
 * @author aheitkamp
 */
public class ButtonFilterCommand extends EntechCommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final DriveSubsystem drive;
    private DriveFilterManager dfm;
    private int buttonNumber;
    private boolean enable;
    private boolean isFinished = false;

    /**
     * 
     *
     * @param drive The drive subsystem.
     * @param buttonNumber The button that has been pressed
     * @param enabled If you want to set the filter to be on or off 
     */
    public ButtonFilterCommand(DriveSubsystem drive, int buttonNumber, boolean enable) {
        super(drive);
        this.drive = drive;
        this.buttonNumber = buttonNumber;
        this.enable = enable;
    }

    @Override
    public void initialize() {
        dfm = drive.getDFM();
    }

    @Override
    public void execute() {
        switch (buttonNumber) {
            case RobotConstants.DRIVER_STICK.TURN_TOGGLE:
                dfm.getTurnToggle().setEnabled(enable);
                break;
            default:
                return;
        }
        isFinished = true;
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
