package frc.robot.subsystems;

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
    * -- 4.71 inch/rev --> ABOUT 0.002 inches per count, .12 m per rev, 0.00006 m/count
    **/
    public ArmStatus ( double actualPostion) {
    	this.actualPostion = actualPostion;
    }
    
    public double getArmExtensionMeters(){
        return actualPostion;
    }


}
