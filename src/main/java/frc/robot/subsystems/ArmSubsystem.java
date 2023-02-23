package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import frc.robot.controllers.SparkMaxPositionController;

/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{


  //for production robot
  public ArmSubsystem() {
	  
  }
  public static double ARM_UP_POSITION= 10.0;
  
  
  //this constructor is for unit testing-- this way we can
  //inject mocks for the motors
  public ArmSubsystem( CANSparkMax elbowMotor, CANSparkMax telescopeMotor) {
	  this.elbowMotor=elbowMotor;
	  this.telescopeMotor=telescopeMotor;
	  this.enabled = true;
	  this.elbowController = new SparkMaxPositionController(elbowMotor,false);
	  this.telescopeController = new SparkMaxPositionController(telescopeMotor,false);
  }
  
  private CANSparkMax elbowMotor;
  private CANSparkMax telescopeMotor;
  private RelativeEncoder elbowEncoder;
  private RelativeEncoder telescopeEncoder;
  private SparkMaxPositionController elbowController;
  private SparkMaxPositionController telescopeController;
  
  private boolean enabled = false;
  
  public ArmStatus getStatus(){
      return new ArmStatus();
  }
  
  @Override
  public void initialize() {
	if ( enabled ) {
	    elbowMotor = new CANSparkMax(RobotConstants.ARM.ELBOW_MOTOR_ID, MotorType.kBrushed);
	    telescopeMotor = new CANSparkMax(RobotConstants.ARM.TELESCOPE_MOTOR_ID, MotorType.kBrushed);
	    elbowMotor.setInverted(false);
	    telescopeMotor.setInverted(false);		
	    
		elbowController = new SparkMaxPositionController(elbowMotor,false);
		telescopeController = new SparkMaxPositionController(telescopeMotor,false);	    
	}

  }

  @Override
  public void initSendable(SendableBuilder builder) {
	  if ( enabled ) {
	      builder.setSmartDashboardType(getName());
	      builder.addDoubleProperty("Elbow Motor", () -> { return elbowMotor.get(); }, null);
	      builder.addDoubleProperty("Telescope Motor", () -> { return telescopeMotor.get(); }, null);		  
	      builder.addStringProperty("Telescope Position", () -> { return telescopeController + ""; }, null );
	      builder.addStringProperty("Elbow  Position", () -> { return elbowController + ""; }, null );
	  }
  }

  public void deployArm() {
	  elbowController.setDesiredPosition(ARM_UP_POSITION);
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
