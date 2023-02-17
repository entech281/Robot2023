package frc.robot.logging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PoseLogger  {

	public void logPose2d(String name, Pose2d p) {
		Pose2dSendable ps = new Pose2dSendable(name, p);
		SmartDashboard.putData( name,ps );
		Shuffleboard.getTab("Poses").add(name, ps);
	}
}
