package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;


public class ShuffleboardFieldDisplay {

	private ShuffleboardTab visionTab = Shuffleboard.getTab(RobotConstants.SHUFFLEBOARD.VISION_TAB);
	private Field2d fieldDisplay = new Field2d();

	
	public ShuffleboardFieldDisplay() {
		visionTab.add("Estimated Location",fieldDisplay).withWidget(BuiltInWidgets.kField);	
	}
	
	public void setRobotPose(Pose2d estimatedPose) {
		fieldDisplay.setRobotPose(estimatedPose);
	}
	
		
}
