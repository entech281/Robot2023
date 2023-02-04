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
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ButtonFilterCommand(DriveSubsystem Drive, int ButtonNumber, boolean enabled) {
        super(Drive);
        drive = Drive;
        buttonNumber = ButtonNumber;
        enable = enabled;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        DFM = drive.getDFM();
    }

    // Called every time the scheduler runs while the command is scheduled.
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

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    // Returns true if this command should run when robot is disabled.
    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
