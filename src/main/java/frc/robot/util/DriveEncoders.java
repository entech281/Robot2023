package frc.robot.util;

import com.revrobotics.RelativeEncoder;

public class DriveEncoders {

    private RelativeEncoder frontLeftEncoder;
    private RelativeEncoder rearLeftEncoder;
    private RelativeEncoder frontRightEncoder;
    private RelativeEncoder rearRightEncoder;
    
    private double frontLeftReference = 0.0;
    private double rearLeftReference = 0.0;
    private double frontRightReference = 0.0;
    private double rearRightReference = 0.0;
    
	public DriveEncoders ( RelativeEncoder frontLeftEncoder, RelativeEncoder frontRightEncoder, RelativeEncoder rearLeftEncoder, RelativeEncoder rearRightEncoder) {
		this.frontLeftEncoder = frontLeftEncoder;
		this.rearLeftEncoder = rearLeftEncoder;
		this.frontRightEncoder = frontRightEncoder;
		this.rearRightEncoder = rearRightEncoder;
		
	}
    public void resetEncoders() {
    	frontLeftReference=frontLeftEncoder.getPosition();
    	rearLeftReference=rearLeftEncoder.getPosition();
    	frontRightReference=frontRightEncoder.getPosition();
    	rearRightReference=rearRightEncoder.getPosition();
    }
    

    /**
    *
    * @return average motor revolutions for the 4 motors
    */
   public double getAveragePosition() {
       double position = 0;
       position += getRelativeDistance( frontLeftEncoder, frontLeftReference);
       position += getRelativeDistance( rearLeftEncoder, rearLeftReference);  
       position += getRelativeDistance( frontRightEncoder, frontRightReference);  
       position += getRelativeDistance( rearRightEncoder, rearRightReference);  
       return position / 4;
   }
   
   private  static double getRelativeDistance(RelativeEncoder current, double reference) {
   	return current.getPosition() - reference;
   }   
}
