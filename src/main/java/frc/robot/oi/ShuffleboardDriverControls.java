package frc.robot.oi;

import static frc.robot.RobotConstants.SHUFFLEBOARD.TABS.MATCH;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotConstants;

public class ShuffleboardDriverControls {
	
	private ShuffleboardTab operatorTab;
	private ShuffleboardTab prematchTab;
	//private SendableChooser<TargetNode> nodeChooser = new SendableChooser<>();
	private SendableChooser<Command> autoCommandChooser = new SendableChooser<>();
	
	public ShuffleboardDriverControls() {
		operatorTab = Shuffleboard.getTab(MATCH);		
		prematchTab = Shuffleboard.getTab(RobotConstants.SHUFFLEBOARD.TABS.PREMATCH);
		
//    	nodeChooser.setDefaultOption("NONE",TargetNode.NONE);
//    	nodeChooser.addOption("A1", TargetNode.A1); 
//    	nodeChooser.addOption("A2", TargetNode.A2);
//    	nodeChooser.addOption("A3", TargetNode.A3);
//    	nodeChooser.addOption("B1", TargetNode.B1);
//    	nodeChooser.addOption("B2", TargetNode.B2);
//    	nodeChooser.addOption("B3", TargetNode.B3);

    	//operatorTab.add("TargetNode",nodeChooser).withWidget(BuiltInWidgets.kSplitButtonChooser).withSize(3, 1).withPosition(1, 3);	
    	prematchTab.add("AutoCommand",autoCommandChooser).withWidget(BuiltInWidgets.kSplitButtonChooser).withSize(7, 1).withPosition(0, 3);
	}
		
	public void addAutoCommandChoice(Command newCommand, boolean isDefault) {
       if (isDefault) {
            autoCommandChooser.setDefaultOption(newCommand.getName(), newCommand);
        } else {
            autoCommandChooser.addOption(newCommand.getName(), newCommand);
        }
	}

	public Command getSelectedAutoCommand() {
		return autoCommandChooser.getSelected();
	}

//	@Override
//	public Optional<TargetNode> getSelectedTarget() {
//		if ( nodeChooser.getSelected() == TargetNode.NONE) {
//			return Optional.empty();
//		}
//		else {
//			return Optional.of(nodeChooser.getSelected());
//		}
//	}
}
