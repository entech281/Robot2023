package frc.robot.subsystems;

import static frc.robot.RobotConstants.ARM.*;

/**
 *
 * @author dcowden
 */
public class ArmStatus  implements SubsystemStatus{

    private double actualPostion;
    
    /**reality note:  
    * 42 counts per ref
    * gearbox: 48 to 1
    * sprocket diameter approx. 1.5" 
    * -- 4.71 inch/ref --> ABOUT 0.002 inches per count    
    **/
    public ArmStatus ( double actualPostion) {
    	this.actualPostion = actualPostion;
    }
    
    public double getArmExtensionMeters(){
        return actualPostion;
    }


}
