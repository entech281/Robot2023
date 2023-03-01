package frc.robot.subsystems;



public class ElbowStatus implements SubsystemStatus{

    private double verticalAngle=0.0; 
    
    public ElbowStatus(double verticalAngle) {
    	this.verticalAngle = verticalAngle;
    }

    public double getVerticalAngle(){
        return verticalAngle;
    }
	
}
