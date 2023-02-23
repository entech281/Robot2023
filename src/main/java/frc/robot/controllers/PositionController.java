package frc.robot.controllers;

public interface PositionController {
    double getDesiredPosition();
    double getActualPosition();

    void setDesiredPosition(double desiredPosition);
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
