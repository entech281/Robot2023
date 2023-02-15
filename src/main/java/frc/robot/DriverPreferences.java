package frc.robot;

import frc.robot.pose.TargetNode;

/**
 * Driver preferences.
 * implementing Sendable including setters would allow this to be viewed and even edited 
 * on the shuffleboard
 * @author davec
 *
 */
public class DriverPreferences {
	
	public boolean isFieldAbsoluteDriving() {
		return fieldAbsoluteDriving;
	}
	public boolean isFieldRelativeDriving() {
		return ! isFieldAbsoluteDriving();
	}
	public void setFieldAbsoluteDriving(boolean fieldAbsoluteDriving) {
		this.fieldAbsoluteDriving = fieldAbsoluteDriving;
	}
	public boolean isTwistLocked() {
		return twistLock;
	}
	public boolean isTwistEnabled() {
		return ! twistLock;
	}
	public void setTwistLock(boolean twistLock) {
		this.twistLock = twistLock;
	}
	private boolean fieldAbsoluteDriving = false;
	private boolean twistLock = false;
	private TargetNode selectedNode = TargetNode.NONE;
	public TargetNode getSelectedNode() {
		return selectedNode;
	}
	public void setSelectedNode(TargetNode selectedNode) {
		this.selectedNode = selectedNode;
	}
	public boolean hasSelectedTarget() {
		return this.selectedNode != TargetNode.NONE;
	}
	
}
