package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotConstants;
import frc.robot.pose.LED;
import frc.robot.pose.LateralAlignCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dcowden
 */
public class LEDSubsystem extends EntechSubsystem{

  public LEDSubsystem() {
	  leds = new AddressableLED(RobotConstants.PWM.LED_STRIP);
	  buffer = new AddressableLEDBuffer(NUM_LEDS);	 
	  leds.setLength(buffer.getLength());
	  setNormal();
	  leds.start();
  }
  
  private AddressableLED leds;
  private AddressableLEDBuffer buffer;
  private LateralAlignCalculator lateralAlignCalculator;
  
  public static int NUM_LEDS = 20;
  
  @Override
  public void initialize() {

  }

  public void setAlighnColors(Color c, double xDistance, double yDistance) {
	double lateralOffsetToNodeFromBot = lateralAlignCalculator.findOffsetToNearestTarget(xDistance, yDistance).getLateralOffsetToLocationMeters();
	int lateralOffsetToNode = 22;
	int LEDsPerInch = (int) lateralOffsetToNode / NUM_LEDS;
	int numberOfLEDsToCenterOfNode = (int) (lateralOffsetToNodeFromBot / LEDsPerInch);
	int NUMBER_OF_TOLERANCE_LEDS = 2;
	List<LED> LEDlist = new ArrayList<LED>();
	for (var i = 0; i < buffer.getLength(); i++) {
		if (i == numberOfLEDsToCenterOfNode) {
			setColor(Color.kBlue);
			LEDlist.add(new LED(i, Color.kBlue));
		} if (i < (numberOfLEDsToCenterOfNode - NUMBER_OF_TOLERANCE_LEDS) || i > (numberOfLEDsToCenterOfNode + NUMBER_OF_TOLERANCE_LEDS)) {
			setColor(Color.kYellow);
			LEDlist.add(new LED(i, Color.kYellow));
		} if (i == NUM_LEDS / 2){
			setColor(Color.kWhite);
			LEDlist.add(new LED(i, Color.kWhite));
		} else {
			setColor(Color.kRed);
			LEDlist.add(new LED(i, Color.kRed));
		}
	}
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
