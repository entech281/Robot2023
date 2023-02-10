package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.subsystems.SubsystemStatus;
import frc.robot.util.SendableUtil;

/**
 *
 * @author dcowden
 */
public class NavxStatus implements Sendable , SubsystemStatus{
    
    private double yawAngleDegrees = 0.0;
    private Pose2d basePose;


    public void setYawAngleDegrees(double yawAngleDegrees){
        this.yawAngleDegrees = yawAngleDegrees;
    }
    
    public double getYawAngleDegrees(){
        return yawAngleDegrees;
    }

    public void setBasePose(Pose2d basePose){
        this.basePose = basePose;
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
