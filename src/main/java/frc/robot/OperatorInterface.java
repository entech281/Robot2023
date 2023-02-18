package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.commands.CommandFactory;
import frc.robot.commands.JoystickDriveInputSupplier;

public class OperatorInterface {

	private ShuffleboardDriverControls shuffleboardControls;	
    private CommandJoystick driveStick;
    private CommandFactory commandFactory;
    private JoystickDriveInputSupplier hidJoystickDriveInputSupplier;
    
    public OperatorInterface( final CommandFactory cf, final ShuffleboardDriverControls shuffleboard) {
    	this.shuffleboardControls = shuffleboard;
        this.commandFactory = cf;
        this.driveStick = new CommandJoystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);
        this.hidJoystickDriveInputSupplier = new JoystickDriveInputSupplier(driveStick.getHID());
        setupButtons();

    }
    private void setupButtons() {
        driveStick.button(RobotConstants.DRIVER_STICK.TURN_TOGGLE)
        .onTrue(commandFactory.setDriverYawEnableCommand(shuffleboardControls,true))
        .onFalse(commandFactory.setDriverYawEnableCommand(shuffleboardControls,false));

	    driveStick.button(RobotConstants.DRIVER_STICK.AUTO_ALIGN_DRIVE)
	    /**TODO: this is broken!!! the challenge is to find out who can be a ScoringLocatin provider**/
	        .onTrue(commandFactory.alignToScoringLocation(null ,hidJoystickDriveInputSupplier))
	        .onFalse(commandFactory.filteredDriveCommand(hidJoystickDriveInputSupplier,shuffleboardControls));
	    
	    driveStick.button(RobotConstants.DRIVER_STICK.TOGGLE_FIELD_ABSOLUTE)
	        .onTrue(commandFactory.toggleFieldAbsoluteCommand(this.shuffleboardControls));
	    
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
    	commandFactory.setDefaultDriveCommand(commandFactory.filteredDriveCommand(hidJoystickDriveInputSupplier,shuffleboardControls));
    }
    
}
