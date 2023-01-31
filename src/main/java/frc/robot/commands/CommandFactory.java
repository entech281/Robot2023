package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SubsystemManager;

/**
 *
 * @author dcowden
 */
public class CommandFactory {

    private final SubsystemManager sm;
    public CommandFactory(SubsystemManager subsystemManager){
        this.sm = subsystemManager;
    }

    public Command ButtonFilterFalseCommand(int ButtonNumber) {
        return new ButtonFilterCommand(sm.getDriveSubsystem(), ButtonNumber, false);
    }

    public Command ButtonFilterTrueCommand(int ButtonNumber) {
        return new ButtonFilterCommand(sm.getDriveSubsystem(), ButtonNumber, true);
    }

    public Command DriveCommand(Joystick joystick) {
        return new DriveCommand(sm.getDriveSubsystem(), joystick);
    }

    public Command getToggleFieldAbsolute() {
        return new ToggleFieldAbsoluteCommand(sm.getDriveSubsystem());
    }

    public Command getAutonomousCommand(){
        return null;
    }

    public Command getZeroGyro() {
        return new ZeroGyroCommand(sm.getNavXSubSystem());
    }
}
