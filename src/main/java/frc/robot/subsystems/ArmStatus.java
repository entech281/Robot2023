package frc.robot.subsystems;

import static frc.robot.RobotConstants.ARM.*;

/**
 *
 * @author dcowden
 */
public class ArmStatus  implements SubsystemStatus{

    private double actualPostion;
    private int armCounts;
    
    /**reality note:  
    * 42 counts per ref
    * gearbox: 48 to 1
    * sprocket diameter approx. 1.5" 
    * -- 4.71 inch/ref --> ABOUT 0.002 inches per count    
    **/
    public ArmStatus ( int armCounts) {
    	this.armCounts = armCounts;
    	this.actualPostion = MIN_EXTENSION_METERS + armCounts/ SETTINGS.COUNTS_PER_METER;
    }

    public int getArmExtensionCounts() {
    	return armCounts;
    }
    
    public double getArmExtensionMeters(){
        return actualPostion;
    }


}
