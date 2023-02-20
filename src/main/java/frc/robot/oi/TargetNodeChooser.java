package frc.robot.oi;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.pose.TargetNode;

public class TargetNodeChooser {
	private ShuffleboardTab operatorTab;
	private ShuffleboardLayout buttonGrid;
	private List<GridButton> allButtons = new ArrayList<>();
	private TargetNode selectedTargetNode = TargetNode.NONE;
	
	public TargetNodeChooser() {
		operatorTab = Shuffleboard.getTab("Testing");		
		//buttonGrid = operatorTab.getLayout("Grid Target Node",BuiltInLayouts.kGrid).withSize(3, 2).withPosition(0, 0);
		//we need 6 buttons...
		addButton(TargetNode.A1,0, 0);
		addButton(TargetNode.A2,1, 0);
		addButton(TargetNode.A3,2, 0);
		addButton(TargetNode.B1,0, 1);
		addButton(TargetNode.B2,1, 1);
		addButton(TargetNode.B3,2, 1);
		
	} 
	
	public void handleSelection(TargetNode node ) {
		selectedTargetNode = node;
		allButtons.forEach((b)->{
			b.setValue(false);
		});	
		System.out.println("Node" + node.getNodeID() + " selected");
	}
	
	private void addButton(TargetNode node, int row, int col) {		
		GridButton gb = new GridButton(node,this);
		allButtons.add(gb);
		String btnName =node.getNodeID()+"";
		operatorTab.add(btnName,gb).withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(row,col);
	}
}
