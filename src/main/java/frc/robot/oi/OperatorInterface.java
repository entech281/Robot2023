package frc.robot.oi;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.adapter.JoystickDriveInputSupplier;

public class OperatorInterface {

	private ShuffleboardDriverControls shuffleboardControls;	
    private CommandJoystick driveStick;
    private CommandJoystick operatorPanel;
    private CommandJoystick operatorStick;
    private CommandFactory commandFactory;
    private JoystickDriveInputSupplier hidJoystickDriveInputSupplier;
    private Supplier<Boolean> gripperStateSupplier;
    
    public OperatorInterface( final CommandFactory cf, final ShuffleboardDriverControls shuffleboard) {
    	this.shuffleboardControls = shuffleboard;
        this.commandFactory = cf;
        this.driveStick = new CommandJoystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);
        this.operatorPanel = new CommandJoystick(RobotConstants.JOYSTICKS.OPERATOR_PANEL);
        this.operatorStick = new CommandJoystick(RobotConstants.JOYSTICKS.OPERATOR_JOYSTICK);
        this.hidJoystickDriveInputSupplier = new JoystickDriveInputSupplier(driveStick.getHID());
        this.gripperStateSupplier = () -> {  return operatorPanel.getHID().getRawButton(RobotConstants.OPERATOR_PANEL.GRIPPER); }; 
        setupButtons();

    }
    
    private void setupButtons() {
        driveStick.button(RobotConstants.DRIVER_STICK.TURN_TOGGLE)
        .onTrue(commandFactory.setDriverYawEnableCommand(shuffleboardControls,true))
        .onFalse(commandFactory.setDriverYawEnableCommand(shuffleboardControls,false));

	    // driveStick.button(RobotConstants.DRIVER_STICK.AUTO_ALIGN_DRIVE)
	    //     .onTrue(commandFactory.alignToScoringLocation(shuffleboardControls ,hidJoystickDriveInputSupplier))
	    //     .onFalse(commandFactory.filteredDriveCommand(hidJoystickDriveInputSupplier,shuffleboardControls));
	    
	    // driveStick.button(RobotConstants.DRIVER_STICK.TOGGLE_FIELD_ABSOLUTE)
	    //     .onTrue(commandFactory.toggleFieldAbsoluteCommand(this.shuffleboardControls));
	    
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

        // *******  Operator Panel  *******
        operatorPanel.button(RobotConstants.OPERATOR_PANEL.GRIPPER)
            .onTrue(commandFactory.closeGripperCommand())
            .onFalse(commandFactory.openGripperCommand());

        if (operatorPanel.getHID().getRawButton(RobotConstants.OPERATOR_PANEL.GRIPPER)) {
            CommandScheduler.getInstance().schedule(commandFactory.closeGripperCommand());
        } else {
            CommandScheduler.getInstance().schedule(commandFactory.openGripperCommand());
        }
        
	    operatorPanel.button(RobotConstants.OPERATOR_PANEL.PIVOT_UP)
	        .whileTrue(commandFactory.nudgeElbowUpCommand());
        operatorPanel.button(RobotConstants.OPERATOR_PANEL.PIVOT_DOWN)
	        .whileTrue(commandFactory.nudgeElbowDownCommand());
        operatorPanel.button(RobotConstants.OPERATOR_PANEL.TELESCOPE_IN)
	        .whileTrue(commandFactory.nudgeArmBackwardCommand());
        operatorPanel.button(RobotConstants.OPERATOR_PANEL.TELESCOPE_OUT)
	        .whileTrue(commandFactory.nudgeArmForwardCommand());

        operatorPanel.button(RobotConstants.OPERATOR_PANEL.LOAD_APRILTAG)
            .onTrue(commandFactory.loadingElbowCommand());
        operatorPanel.button(RobotConstants.OPERATOR_PANEL.OFF)
            .onTrue(commandFactory.carryPosition());

        // ******* Operator Joytick ******* 
        operatorStick.button(RobotConstants.OPERATOR_STICK.GRIPPER)
            .onTrue(commandFactory.openGripperCommand())
            .onFalse(commandFactory.closeGripperCommand());

        operatorStick.button(RobotConstants.OPERATOR_STICK.HOME_ELBOW)
            .onTrue(commandFactory.homeElbowCommand());

        operatorStick.button(RobotConstants.OPERATOR_STICK.HOME_TELESCOPE)
            .onTrue(commandFactory.homeTelescopeCommand());

	    operatorStick.pov(RobotConstants.OPERATOR_STICK.POV.UP)
	        .whileTrue(commandFactory.nudgeElbowUpCommand());
	
	    operatorStick.pov(RobotConstants.OPERATOR_STICK.POV.DOWN)
	        .whileTrue(commandFactory.nudgeElbowDownCommand());
	
	    operatorStick.pov(RobotConstants.OPERATOR_STICK.POV.IN)
	        .whileTrue(commandFactory.nudgeArmBackwardCommand());
	
	    operatorStick.pov(RobotConstants.OPERATOR_STICK.POV.OUT)
	        .whileTrue(commandFactory.nudgeArmForwardCommand());
    }
    
    public void setDefaultCommands() {    	
    	commandFactory.setDefaultDriveCommand(commandFactory.filteredDriveCommand(hidJoystickDriveInputSupplier,shuffleboardControls));
    	commandFactory.setDefaultGripperCommand(commandFactory.gripperPanelSyncCommand(gripperStateSupplier));
    }
    
}
