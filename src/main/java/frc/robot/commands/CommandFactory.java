package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.RobotConstants;

/**
 *
 * @author dcowden
 */
public class CommandFactory {

    private final SubsystemManager sm;
    public CommandFactory(SubsystemManager subsystemManager){
        this.sm = subsystemManager;
    }

    public Command ButtonFilterCommand(int buttonNumber, boolean enabled) {
        return new ButtonFilterCommand(sm.getDriveSubsystem(), buttonNumber, enabled);
    }

    public Command TurnToggleFilter(boolean enabled) {
        return ButtonFilterCommand(RobotConstants.DRIVER_STICK.TURN_TOGGLE, enabled);
    }

    public Command DriveCommand(Joystick joystick) {
        return new DriveCommand(sm.getDriveSubsystem(), joystick);
    }

    public Command ToggleFieldAbsolute() {
        return new ToggleFieldAbsoluteCommand(sm.getDriveSubsystem());
    }

    public Command SnapYawDegreesCommand(double Angle) {
        return new SnapYawDegreesCommand(sm.getDriveSubsystem(), sm.getNavXSubSystem(), Angle);
    }

    public Command getAutonomousCommand() {
        return SnapYawDegreesCommand(160);
    }

    public Command getZeroGyro() {
        return new ZeroGyroCommand(sm.getNavXSubSystem());
    }

    public Command NudgeLeftCommand() {
        return new NudgeLeftCommand(sm.getDriveSubsystem());
    }

    public Command NudgeRightCommand() {
        return new NudgeRightCommand(sm.getDriveSubsystem());
    }
}
