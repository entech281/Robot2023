package frc.robot.subsystems;


import static frc.robot.RobotConstants.ELBOW;

public class ElbowStatus implements SubsystemStatus{

    private double verticalAngle=0.0; 
    private int counts = 0;
    
    public ElbowStatus(int counts) {
    	this.counts = counts;
    	verticalAngle = ELBOW.MIN_ANGLE_DEGREES + counts / ELBOW.SETTINGS.COUNTS_PER_DEGREE;
    }

    public int getCounts() {
    	return this.counts;
    }
    public double getVerticalAngle(){
        return verticalAngle;
    }

    @Override
	public String toString() {
		return "Elbow Status: [counts:" + counts + ", verticalAngle=" + verticalAngle + "]";
	}
	
}
