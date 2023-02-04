package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.commands.CommandFactory;
import frc.robot.pose.TargetNode;
import frc.robot.subsystems.SubsystemManager;

public class OperatorInterface {

    private CommandJoystick driveStick;
    private SubsystemManager subsystemManager;
    private CommandFactory commandFactory;

    public OperatorInterface(final SubsystemManager subMan, final CommandFactory cf) {
        this.subsystemManager = subMan;
        this.commandFactory = cf;
        this.driveStick = new CommandJoystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);

        driveStick.button(RobotConstants.DRIVER_STICK.TURN_TOGGLE)
            .onTrue(commandFactory.ButtonFilterTrueCommand( RobotConstants.DRIVER_STICK.TURN_TOGGLE ))
            .onFalse(commandFactory.ButtonFilterFalseCommand( RobotConstants.DRIVER_STICK.TURN_TOGGLE ));

        driveStick.button(RobotConstants.DRIVER_STICK.TOGGLE_FIELD_ABSOLUTE)
            .onTrue(commandFactory.ToggleFieldAbsolute());

        driveStick.button(RobotConstants.DRIVER_STICK.ZERO_GYRO_ANGLE)
            .onTrue(commandFactory.getZeroGyro());
            
        subsystemManager.getDriveSubsystem().setDefaultCommand(commandFactory.DriveCommand(driveStick.getHID()));
    }
    
    public TargetNode getTargetNode(){
        return  TargetNode.NONE;
    }

}
