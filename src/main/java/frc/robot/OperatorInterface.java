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
            .onTrue(commandFactory.TurnToggleFilter(true))
            .onFalse(commandFactory.TurnToggleFilter(false));

        driveStick.button(RobotConstants.DRIVER_STICK.TOGGLE_FIELD_ABSOLUTE)
            .onTrue(commandFactory.ToggleFieldAbsolute());

        driveStick.button(RobotConstants.DRIVER_STICK.ZERO_GYRO_ANGLE)
            .onTrue(commandFactory.getZeroGyro());

        driveStick.button(RobotConstants.DRIVER_STICK.ZERO_ROBOT_ANGLE)
            .onTrue(commandFactory.SnapYawDegreesCommand(0));

        driveStick.pov(RobotConstants.DRIVER_STICK.POV.RIGHT)
            .onTrue(commandFactory.NudgeRightCommand());

        driveStick.pov(RobotConstants.DRIVER_STICK.POV.LEFT)
            .onTrue(commandFactory.NudgeLeftCommand());
            
        subsystemManager.getDriveSubsystem().setDefaultCommand(commandFactory.DriveCommand(driveStick.getHID()));
    }
    
    public TargetNode getTargetNode(){
        return  TargetNode.NONE;
    }

}
