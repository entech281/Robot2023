package frc.robot;

import static frc.robot.RobotConstants.SHUFFLEBOARD.DEFAULT_FIELD_ABSOLUTE;
import static frc.robot.RobotConstants.SHUFFLEBOARD.DEFAULT_YAW_LOCK;
import static frc.robot.RobotConstants.SHUFFLEBOARD.OPERATOR_TAB;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.pose.TargetNode;

public class ShuffleboardDriverControls {
	
	private ShuffleboardTab operatorTab;

	private SendableChooser<TargetNode> nodeChooser = new SendableChooser<>();
	private GenericEntry fieldAbsolute;
	private GenericEntry driverYawLock;
	
	public ShuffleboardDriverControls() {
		operatorTab = Shuffleboard.getTab(OPERATOR_TAB);		

		
    	nodeChooser.setDefaultOption("DEFAULT",TargetNode.A1);
    	nodeChooser.addOption("A1", TargetNode.A1); 
    	nodeChooser.addOption("A2", TargetNode.A2);
    	nodeChooser.addOption("A3", TargetNode.A3);
    	nodeChooser.addOption("B1", TargetNode.B1);
    	nodeChooser.addOption("B2", TargetNode.B2);
    	nodeChooser.addOption("B3", TargetNode.B3);

    	operatorTab.add(nodeChooser).withWidget(BuiltInWidgets.kSplitButtonChooser);		
	
    	fieldAbsolute = operatorTab.add("FieldAbsolute",false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    	driverYawLock = operatorTab.add("YawLock",false).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    	fieldAbsolute.setBoolean(DEFAULT_FIELD_ABSOLUTE);
    	driverYawLock.setBoolean(DEFAULT_YAW_LOCK);
	}

	public TargetNode getSelectedTargetNode() {
		return nodeChooser.getSelected();
	}

	
	public void setFieldAbsolute(boolean newValue) {
		fieldAbsolute.setBoolean(newValue);
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

	public boolean isYawLocked() {
		return driverYawLock.getBoolean(DEFAULT_YAW_LOCK);
	}	
	
	public void setYawLock(boolean newValue) {
		driverYawLock.setBoolean(newValue);
	}
}
