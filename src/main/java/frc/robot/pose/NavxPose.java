package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.util.SendableUtil;

/**
 *
 * @author dcowden
 */
public class NavxPose implements Sendable {
    
    public NavxPose(double angle, Pose2d newpose){
        this.yawAngleDegrees = angle;
        this.basePose = newpose;
    }

    private double yawAngleDegrees = 0.0;
    private Pose2d basePose;


    public double getYawAngleDegrees(){
        return yawAngleDegrees;
    }

    public Pose2d getBasePose(){
        return basePose;
    }

    @Override
    public void initSendable(SendableBuilder sb) {
        sb.addDoubleProperty("Yaw Angle",this::getYawAngleDegrees, null);
        SendableUtil.initPose2dSendable("Base", this::getBasePose, sb);
    }
    
}
