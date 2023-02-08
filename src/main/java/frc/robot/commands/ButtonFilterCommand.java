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
    private DriveFilterManager DFM;
    private int buttonNumber;
    private boolean enable;
    private boolean isFinished = false;

    /**
     * 
     *
     * @param Drive The drive subsystem.
     * @param ButtonNumber The button that has been pressed
     * @param enabled If you want to set the filter to be on or off 
     */
    public ButtonFilterCommand(DriveSubsystem Drive, int ButtonNumber, boolean enabled) {
        super(Drive);
        drive = Drive;
        buttonNumber = ButtonNumber;
        enable = enabled;
    }

    @Override
    public void initialize() {
        DFM = drive.getDFM();
    }

    @Override
    public void execute() {
        switch (buttonNumber) {
            case RobotConstants.DRIVER_STICK.TURN_TOGGLE:
                DFM.getTurnToggle().setEnabled(enable);
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
