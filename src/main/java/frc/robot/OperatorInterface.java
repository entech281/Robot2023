package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.CommandFactory;
import frc.robot.subsystems.SubsystemManager;

public class OperatorInterface {

    private Joystick driveStick;
    private JoystickButtonManager joystickManager;
    private SubsystemManager subsystemManager;
    private CommandFactory commandFactory;

    public OperatorInterface(final SubsystemManager subMan, final CommandFactory cf) {
        this.subsystemManager = subMan;
        this.commandFactory = cf;
        this.driveStick = new Joystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);
        this.joystickManager = new JoystickButtonManager(driveStick);

        joystickManager.addButton(RobotConstants.DRIVER_STICK.TURN_TOGGLE)
                .whenPressed(commandFactory.ButtonFilterCommand(
                    subsystemManager.getDriveSubsystem(), 
                    RobotConstants.DRIVER_STICK.TURN_TOGGLE, 
                    true
                    )
                )
                .add();

        joystickManager.addButton(RobotConstants.DRIVER_STICK.TURN_TOGGLE)
            .whenReleased(commandFactory.ButtonFilterCommand(
                    subsystemManager.getDriveSubsystem(), 
                    RobotConstants.DRIVER_STICK.TURN_TOGGLE,
                    false
                )
            )
            .add();

        subsystemManager.getDriveSubsystem().setDefaultCommand(new DriveCommand(subsystemManager.getDriveSubsystem(), driveStick));
    }

}
