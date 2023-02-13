package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.commands.CommandFactory;
import frc.robot.pose.TargetNode;


public class OperatorInterface {

    private CommandJoystick driveStick;
    private CommandFactory commandFactory;

    public OperatorInterface( final CommandFactory cf) {
        this.commandFactory = cf;
        this.driveStick = new CommandJoystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);

        driveStick.button(RobotConstants.DRIVER_STICK.TURN_TOGGLE)
            .onTrue(commandFactory.turnToggleFilter(false))
            .onFalse(commandFactory.turnToggleFilter(true));

        driveStick.button(RobotConstants.DRIVER_STICK.AUTO_ALIGN_DRIVE)
            .onTrue(commandFactory.alignToScoringLocation(getSelectedTargetNode(),driveStick.getHID()))
            .onFalse(commandFactory.driveCommand(driveStick.getHID()));
        
        driveStick.button(RobotConstants.DRIVER_STICK.TOGGLE_FIELD_ABSOLUTE)
            .onTrue(commandFactory.toggleFieldAbsolute());

        driveStick.button(RobotConstants.DRIVER_STICK.ZERO_GYRO_ANGLE)
            .onTrue(commandFactory.getZeroGyro());

        driveStick.button(RobotConstants.DRIVER_STICK.ZERO_ROBOT_ANGLE)
            .onTrue(commandFactory.snapYawDegreesCommand(0));

        driveStick.pov(RobotConstants.DRIVER_STICK.POV.RIGHT)
            .onTrue(commandFactory.nudgeRightCommand());

        driveStick.pov(RobotConstants.DRIVER_STICK.POV.LEFT)
            .onTrue(commandFactory.nudgeLeftCommand());

        driveStick.pov(RobotConstants.DRIVER_STICK.POV.FORWARD)
            .onTrue(commandFactory.nudgeForwardCommand());

        driveStick.pov(RobotConstants.DRIVER_STICK.POV.BACKWARD)
            .onTrue(commandFactory.nudgeBackwardCommand());

        driveStick.button(RobotConstants.DRIVER_STICK.NUDGE_YAW_LEFT)
            .onTrue(commandFactory.nudgeYawLeftCommand());

        driveStick.button(RobotConstants.DRIVER_STICK.NUDGE_YAW_RIGHT)
            .onTrue(commandFactory.nudgeYawRightCommand());
            

    }

    public void setDefaultDriveCommand() {
    	commandFactory.setDefaultDriveCommand(commandFactory.filteredDriveComamnd(driveStick.getHID()));
    }
    
    public boolean hasSelectedTarget() {
    	return true;
    }
    public TargetNode getSelectedTargetNode(){
    	//TODO: replace with code that gets the selected one
        return  TargetNode.A3;
    }

}
