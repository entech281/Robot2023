package frc.robot.pose;

public class ArmPoseResult {
    //target distance ius distance from middle of robot to selected target
    //can deploy states if robot can deploy to a target
    double targetDistance;
    boolean canDeploy;

    public void setTargetDistance(double targetDistance){
        this.targetDistance = targetDistance;
    }

    public void setCanDeploy(boolean canDeploy){
        this.canDeploy = canDeploy;
    }

    public String toString() {
        return "ArmPoseResult: [target distance = " + targetDistance + ", canDeploy = " + canDeploy + "]";
    }

}
