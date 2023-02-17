package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.commands.CommandFactory;
import frc.robot.pose.TargetNode;

public class OperatorInterface {

    private CommandJoystick driveStick;
    private CommandFactory commandFactory;
    private SendableChooser<TargetNode> nodeChooser;
    
    public OperatorInterface( final CommandFactory cf) {
        this.commandFactory = cf;
        this.driveStick = new CommandJoystick(RobotConstants.JOYSTICKS.DRIVER_JOYSTICK);

        driveStick.button(RobotConstants.DRIVER_STICK.TURN_TOGGLE)
            .onTrue(commandFactory.turnToggleFilter(false))
            .onFalse(commandFactory.turnToggleFilter(true));

        driveStick.button(RobotConstants.DRIVER_STICK.AUTO_ALIGN_DRIVE)
            .onTrue(commandFactory.alignToScoringLocation(nodeChooser.getSelected(),driveStick.getHID()))
            .onFalse(commandFactory.filteredDriveCommand(driveStick.getHID()));
        
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
            
        setupShuffleboard();
    }

    private void setupShuffleboard() {
    	ShuffleboardTab scoringTab = Shuffleboard.getTab("Scoring");
    	//ShuffleboardLayout scoringOptions = scoringTab.getLayout("ScoringLocations", BuiltInLayouts.kGrid).withSize(3, 3);
    	nodeChooser.setDefaultOption("DEFAULT",TargetNode.A1);
    	nodeChooser.addOption("A1", TargetNode.A1); 
    	nodeChooser.addOption("A2", TargetNode.A1);
    	nodeChooser.addOption("A3", TargetNode.A1);
    	nodeChooser.addOption("B1", TargetNode.A1);
    	nodeChooser.addOption("B2", TargetNode.A1);
    	nodeChooser.addOption("B3", TargetNode.A1);
    	SmartDashboard.putData("TargetNodes",nodeChooser);
    	scoringTab.add(nodeChooser).withWidget(BuiltInWidgets.kSplitButtonChooser);
    }
    public void setDefaultDriveCommand() {
    	commandFactory.setDefaultDriveCommand(commandFactory.filteredDriveCommand(driveStick.getHID()));
    }
    
}
