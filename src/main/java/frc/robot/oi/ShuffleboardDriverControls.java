package frc.robot.oi;

import static frc.robot.RobotConstants.SHUFFLEBOARD.DEFAULT_FIELD_ABSOLUTE;
import static frc.robot.RobotConstants.SHUFFLEBOARD.DEFAULT_PRECISION_DRIVE;
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
	private GenericEntry precisionDrive;
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

    	operatorTab.add("TargetNode",nodeChooser).withWidget(BuiltInWidgets.kSplitButtonChooser).withSize(3, 1).withPosition(1, 3);	
    	prematchTab.add("AutoCommand",autoCommandChooser).withWidget(BuiltInWidgets.kSplitButtonChooser).withSize(7, 1).withPosition(0, 3);
    	fieldAbsolute = prematchTab.add("FieldAbsolute",DEFAULT_FIELD_ABSOLUTE).withWidget(BuiltInWidgets.kToggleButton).withPosition(0, 1).getEntry();
		precisionDrive = operatorTab.add("PrecisionDrive",DEFAULT_PRECISION_DRIVE).withWidget(BuiltInWidgets.kToggleButton).withPosition(0, 5).getEntry();
    	driverYawEnabled = operatorTab.add("YawEnabled",DEFAULT_YAW_LOCK).withWidget(BuiltInWidgets.kToggleButton).withPosition(0,3).getEntry();
    	fieldAbsolute.setBoolean(DEFAULT_FIELD_ABSOLUTE);
		precisionDrive.setBoolean(DEFAULT_PRECISION_DRIVE);
    	driverYawEnabled.setBoolean(DEFAULT_YAW_LOCK);
	}
	
	public void setFieldAbsolute(boolean newValue) {
		fieldAbsolute.setBoolean(newValue);
	}
	
	public void addAutoCommandChoice(Command newCommand, boolean isDefault) {
       if (isDefault) {
            autoCommandChooser.setDefaultOption(newCommand.getName(), newCommand);
        } else {
            autoCommandChooser.addOption(newCommand.getName(), newCommand);
        }
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

	public boolean isPrecisionDrive() {
		return precisionDrive.getBoolean(DEFAULT_PRECISION_DRIVE);
	}

	public void setPrecisionDrive(boolean newValue) {
		precisionDrive.setBoolean(newValue);
	}

	public void togglePrecisionDrive() {
		setPrecisionDrive(!(isPrecisionDrive()));
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
