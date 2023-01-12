package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ArcadeDriveCommand;
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

        // joystickManager.addButton(RobotConstants.DRIVER_STICK.TURN_LEFT90)
        //        .whenPressed(commandFactory.snapToYawCommand(-90.0))
        //        .add();

        // joystickManager.addButton(RobotConstants.DRIVER_STICK.TURN_RIGHT90)
        //        .whenPressed(commandFactory.snapToYawCommand( 90.0))
        //        .add();

        subsystemManager.getDriveSubsystem().setDefaultCommand ( new ArcadeDriveCommand(subsystemManager.getDriveSubsystem(), driveStick) );
    }

}
