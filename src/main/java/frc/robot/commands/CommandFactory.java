package frc.robot.commands;

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

    public Command ButtonFilterCommand(DriveSubsystem Drive, int ButtonNumber, boolean enabled) {
        return new ButtonFilterCommand(Drive, ButtonNumber, enabled);
    }

    public Command getAutonomousCommand(){
        return null;
    }
}
