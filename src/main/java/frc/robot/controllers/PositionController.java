package frc.robot.controllers;

import java.util.List;
import java.util.Map;

public interface PositionController {	
	
	
	//List<PositionPreset> getPositionPresets();
    double getDesiredPosition();
    double getActualPosition();

    //void setDesiredPosition(PositionPreset preset);
    void setDesiredPosition(double preset);
    
    //boolean isInMotion();
    //boolean isAtDesiredPosition();
    
    default void resetPosition(){
        setDesiredPosition(0.0);
    }
    boolean isReversed();
    boolean isAtLowerLimit();
    boolean isAtUpperLimit();
    boolean isEnabled();
    
    //should detect if the controller is available
    void configure();
    
}


class PositionPreset{
	String name;
	double value;
}
