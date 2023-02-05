
package frc.robot.pose.instructions;

/**
 *
 * @author dcowden
 */
public class RotateInstruction implements AlignmentInstruction{
    public RotateInstruction(double degreesToRotate){
        this.degreesToRotate = degreesToRotate;
    }
    public double getDegressToRotate(){
        return degreesToRotate;
    }
    private double degreesToRotate = 0.0;
    
    @Override
    public String toString(){
        return "Rotate " + degreesToRotate;
    }
}
