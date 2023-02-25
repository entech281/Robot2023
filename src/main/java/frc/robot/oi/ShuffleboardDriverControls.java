package frc.robot.oi;

import static frc.robot.RobotConstants.SHUFFLEBOARD.DEFAULT_FIELD_ABSOLUTE;
import static frc.robot.RobotConstants.SHUFFLEBOARD.DEFAULT_YAW_LOCK;
import static frc.robot.RobotConstants.SHUFFLEBOARD.TABS.MATCH;

import java.util.Optional;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants;
import frc.robot.commands.supplier.TargetNodeSupplier;
import frc.robot.pose.TargetNode;

public class ShuffleboardDriverControls implements TargetNodeSupplier {
	
	private ShuffleboardTab operatorTab;
	private ShuffleboardTab prematchTab;
	private SendableChooser<TargetNode> nodeChooser = new SendableChooser<>();
	private SendableChooser<Command> autoCommandChooser = new SendableChooser<>();
	private GenericEntry fieldAbsolute;
	private GenericEntry driverYawEnabled;
	
	public ShuffleboardDriverControls() {
		operatorTab = Shuffleboard.getTab(MATCH);		
		prematchTab = Shuffleboard.getTab(RobotConstants.SHUFFLEBOARD.TABS.PREMATCH);
		
    	nodeChooser.setDefaultOption("NONE",TargetNode.NONE);
    	nodeChooser.addOption("A1", TargetNode.A1); 
    	nodeChooser.addOption("A2", TargetNode.A2);
    	nodeChooser.addOption("A3", TargetNode.A3);
    	nodeChooser.addOption("B1", TargetNode.B1);
    	nodeChooser.addOption("B2", TargetNode.B2);
    	nodeChooser.addOption("B3", TargetNode.B3);
//Driver Station is 0-4 by 0-9 (squares)
    	operatorTab.add("TargetNode",nodeChooser).withWidget(BuiltInWidgets.kSplitButtonChooser).withSize(3, 1).withPosition(4, 2);	
    	prematchTab.add("AutoCommand",autoCommandChooser).withWidget(BuiltInWidgets.kComboBoxChooser).withSize(5, 1).withPosition(0, 0);
    	fieldAbsolute = prematchTab.add("FieldAbsolute",DEFAULT_FIELD_ABSOLUTE).withWidget(BuiltInWidgets.kToggleButton).withPosition(0, 1).getEntry();
    	driverYawEnabled = operatorTab.add("YawEnabled",DEFAULT_YAW_LOCK).withWidget(BuiltInWidgets.kToggleButton).withPosition(0,5).getEntry();
    	fieldAbsolute.setBoolean(DEFAULT_FIELD_ABSOLUTE);
    	driverYawEnabled.setBoolean(DEFAULT_YAW_LOCK);
	}
	
	public void setFieldAbsolute(boolean newValue) {
		fieldAbsolute.setBoolean(newValue);
	}
	
	public void addAutoCommandChoice(Command newCommand) {
		autoCommandChooser.addOption(newCommand.getName(), newCommand);
	}
	public boolean isFieldAbsolute() {
		return fieldAbsolute.getBoolean(DEFAULT_FIELD_ABSOLUTE);
	}
	public boolean isFieldRelative() {
		return ! fieldAbsolute.getBoolean(DEFAULT_FIELD_ABSOLUTE);
	}	
	
	public void toggleFieldAbsolute() {
		setFieldAbsolute( ! isFieldAbsolute() );
	}

	public boolean isYawEnabled() {
		return driverYawEnabled.getBoolean(DEFAULT_YAW_LOCK);
	}	
	public boolean isYawLocked() {
		return ! isYawEnabled();
	}
	
	public void setYawLock(boolean newValue) {
		driverYawEnabled.setBoolean(newValue);
	}

	public Command getSelectedAutoCommand() {
		return autoCommandChooser.getSelected();
	}

	@Override
	public Optional<TargetNode> getSelectedTarget() {
		if ( nodeChooser.getSelected() == TargetNode.NONE) {
			return Optional.empty();
		}
		else {
			return Optional.of(nodeChooser.getSelected());
		}
	}
}
