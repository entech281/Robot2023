package frc.robot.pose;

/**
 *
 * @author dcowden
 */
public class NavxOutput {
    
    public NavxOutput(double angle){
        this.angle = angle;
    }
    private double angle = 0.0;
    
    
    public double getAngle(){
        return angle;
    }
    
}
