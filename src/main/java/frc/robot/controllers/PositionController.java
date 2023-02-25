package frc.robot.controllers;


public interface PositionController {

    double getDesiredPosition();
    double getActualPosition();

    void setDesiredPosition(double preset);
	public void home();
	public void update();
	public boolean isHome();
    boolean isInMotion();
    boolean isAtDesiredPosition();

    void resetPosition();
    boolean isReversed();
    boolean isAtLowerLimit();
    boolean isAtUpperLimit();
    boolean isEnabled();
 
    //should detect if the controller is available
    void configure();	
	
}
