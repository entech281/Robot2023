package frc.robot.oi;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.pose.TargetNode;


public class GridButton implements Sendable{
	private boolean value = false;
	private String name;
	private TargetNode node;
	TargetNodeChooser parent;
	public GridButton( TargetNode node,TargetNodeChooser parent) {
		this.name =node.getNodeID().toString();
		this.node = node;
		this.parent=parent;
	}
	public boolean getValue() {
		return value;
	}
	public void setValue(boolean newValue) {
		if ( newValue ) {
			parent.handleSelection(this.node);
		}
		this.value = newValue;
	}
	@Override
	public void initSendable(SendableBuilder sb) {
		sb.addBooleanProperty(this.name, this::getValue, this::setValue);
	}
}

