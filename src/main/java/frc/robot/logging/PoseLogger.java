package frc.robot.logging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PoseLogger  {

	public void logPose2d(String name, Pose2d p) {
		Pose2dSendable ps = new Pose2dSendable(name, p);
		SmartDashboard.putData( name,ps );
		Field2d f2d = new Field2d();
		f2d.setRobotPose(p);
		SmartDashboard.putData(name,f2d);
		Shuffleboard.getTab("Poses").add(name, f2d).withWidget(BuiltInWidgets.kField);
	}
}
