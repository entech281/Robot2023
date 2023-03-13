package frc.robot.logging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public class Pose2dSendable implements Sendable{

	private Pose2d pose;
	private String name;
	public Pose2dSendable(String name,Pose2d pose) {
		this.name = name;
		this.pose = pose;
	}
	
	public String getName() {
		return name;
	}
	public Pose2d getPose() {
		return pose;
	}
	public double getX() {
		return pose.getX();
	}
	public double getY() {
		return pose.getY();
	}
	public double getAngle() {
		return pose.getRotation().getDegrees();
	}
	
	@Override
	public void initSendable(SendableBuilder builder) {
		builder.addStringProperty("name", this::getName,null);
		builder.addDoubleProperty("X",this::getX, null);
		builder.addDoubleProperty("Y",this::getY, null);
		builder.addDoubleProperty("R",this::getAngle, null);
	}
}
