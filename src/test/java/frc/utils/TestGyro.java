package frc.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.pose.RobotPose;

public class TestGyro implements Gyro {
    private double anglePose;
    private final double changeRateConstant;
    public TestGyro(double startingPose, double changeRateConstant) {
        anglePose = startingPose;
        this.changeRateConstant = changeRateConstant;
    }

    public void setAnglePose(double anglePose) {
        this.anglePose = anglePose;
    }

    @Override
    public void close() {}

    @Override
    public void reset() { anglePose = 0; }

    @Override
    public double getRate() { return changeRateConstant; }

    @Override
    public void calibrate() {}

    @Override
    public double getAngle() { return anglePose; }

    public void simulateMove( double turnPower) {
        anglePose = turnPower * changeRateConstant;
    }
    
    public RobotPose computePose() {
    	RobotPose rp = new RobotPose();
    	rp.setCalculatedPose(new Pose2d( 0, 0, Rotation2d.fromDegrees(getAngle())));
    	return rp;
    }
}
