package frc.robot.pose;

/**
 *
 * @author dcowden
 */
public class ArmPose {
    
    public ArmPose(boolean clawOpen, double armExtension, double verticalAngle){
        this.clawOpen = clawOpen;
        this.armExtension = armExtension;
        this.verticalAngle = verticalAngle;
    }

    private boolean clawOpen;
    private double armExtension;
    private double verticalAngle;
    
    
    public boolean getClawOpen(){
        return clawOpen;
    }

    public double getArmExtension(){
        return armExtension;
    }
    
    public double getVerticalAngle(){
        return verticalAngle;
    }

}
