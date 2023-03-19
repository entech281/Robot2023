package frc.robot.pose;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class MovingAveragePose {

	private LinearFilter movingAverageX;
	private LinearFilter movingAverageY;	
	private LinearFilter movingAverageRot;
	private Pose2d lastPose;
 
	public MovingAveragePose(int numSamples) {
		movingAverageX = LinearFilter.movingAverage(numSamples);
		movingAverageY = LinearFilter.movingAverage(numSamples);		
		movingAverageRot = LinearFilter.movingAverage(numSamples);
	}
	
	public double getX() {
		return lastPose.getX();
	}
	public double getY() {
		return lastPose.getY();
	}
	public void update(Pose2d newValue) {
		double x = movingAverageX.calculate(newValue.getX());
		double y = movingAverageY.calculate(newValue.getY());
		double r = movingAverageRot.calculate(newValue.getRotation().getDegrees());
		lastPose = new Pose2d(x,y,Rotation2d.fromDegrees(r));
	}
	
	public Pose2d getMovingAverage() {
		return lastPose;
	}
}
