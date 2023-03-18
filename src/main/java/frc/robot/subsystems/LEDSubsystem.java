package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotConstants;

/**
 *
 * @author dcowden
 */
public class LEDSubsystem extends EntechSubsystem{

  private AddressableLED leds;
  private AddressableLEDBuffer buffer;
  
  public static int NUM_LEDS = 20;
  
  @Override
  public void initialize() {
	  leds = new AddressableLED(RobotConstants.PWM.LED_STRIP);
	  buffer = new AddressableLEDBuffer(NUM_LEDS);
	  leds.setLength(buffer.getLength());
	  setNormal();
  }


  public void setNormal() {
	  setColor(Color.kWhite);
  }
  
  public void setAligning() {
	  setColor(Color.kOrange);
  }
  
  public void setAligned() {
	  setColor(Color.kGreen);
  }
  
  public void setColor(Color c) {
	  for ( var i=0;i< buffer.getLength();i++) {
		  buffer.setLED(i, c);
	  }
	  leds.setData(buffer);
  }
  
  
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public SubsystemStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
}



}
