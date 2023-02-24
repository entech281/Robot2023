package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import frc.robot.controllers.PositionController;
import frc.robot.controllers.PositionPreset;
import frc.robot.controllers.SparkMaxPositionController;

/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{

  private CANSparkMax telescopeMotor;
  private PositionController controller;
  
  public static PositionPreset HOME = new PositionPreset("HOME", 0);
  private boolean enabled = false;
  
  
  public ArmStatus getStatus(){
      return new ArmStatus();
  }
  
  @Override
  public void initialize() {
	if ( enabled ) {
	    telescopeMotor = new CANSparkMax(RobotConstants.ARM.TELESCOPE_MOTOR_ID, MotorType.kBrushed);
	    controller = new SparkMaxPositionController(telescopeMotor,false);
	}

  }

  @Override
  public void initSendable(SendableBuilder builder) {
	  if ( enabled ) {
	      builder.setSmartDashboardType(getName());
	      builder.addDoubleProperty("Telescope Motor", () -> { return telescopeMotor.get(); }, null);		  
	  }
  }

  @Override
  public void periodic() {
     if (enabled ) {
    	 
     }
  }

  @Override
  public void simulationPeriodic() {
    
  }

}
