package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotConstants;

/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{

  public enum ArmPreset { 
    kHome, kCarry, kLowScore, kMidScore, kHighScore, kFloorPickup, kLoad, kStop
  }
    
  public enum GripperState { kClose, kOpen }

  private boolean enabled = false;
  private CANSparkMax elbowMotor;
  private CANSparkMax telescopeMotor;
  private DoubleSolenoid gripperSolenoid;
  private GripperState gripperState;
  private int gripperSolenoidCounter;

  private final int SOLENOID_HIT_COUNT = 10;
  private final double EXTEND_INCREMENT = 0.01;
  private final double RAISE_INCREMENT = 0.01;
  private final double ELBOW_MOVE_SPEED = 0.25;
  private final double TELESCOPE_MOVE_SPEED = 0.25;

  private final double POSITION_TOLERANCE = 1.0;

  private ArmPreset requestedPreset;
  private double elbowDesiredPosition;
  private double telescopeDesiredPosition;
  
  public ArmStatus getStatus(){
      return new ArmStatus();
  }
  
  @Override
  public void initialize() {
	if ( enabled ) {
	    elbowMotor = new CANSparkMax(RobotConstants.CAN.ELBOW_MOTOR_ID, MotorType.kBrushed);
	    telescopeMotor = new CANSparkMax(RobotConstants.CAN.TELESCOPE_MOTOR_ID, MotorType.kBrushed);

	    elbowMotor.setInverted(false);
	    telescopeMotor.setInverted(false);

        elbowMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        telescopeMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        gripperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotConstants.PNEUMATICS.GRIPPER_OPEN, RobotConstants.PNEUMATICS.GRIPPER_CLOSE);
 	}
    requestedPreset = ArmPreset.kHome;
    gripperState = GripperState.kClose;
    gripperSolenoidCounter = 0;

  }

  @Override
  public void initSendable(SendableBuilder builder) {
	  if ( enabled ) {
	      builder.setSmartDashboardType(getName());
	      builder.addDoubleProperty("Elbow Motor", () -> { return elbowMotor.get(); }, null);
	      builder.addDoubleProperty("Telescope Motor", () -> { return telescopeMotor.get(); }, null);		  
	  }
  }

  @Override
  public void periodic() {
     if (enabled ) {
       double elbowMotorSpeed = 0.0;
       double telescopeMotorSpeed = 0.0;

       // This is WAY to simple.  Use a PID?  
       // Also telecope must be at minimum length for the elbow to be between lower limit and specific angle
       if (elbowDesiredPosition > getElbowPosition()) {
         elbowMotorSpeed = ELBOW_MOVE_SPEED;
       } else if (elbowDesiredPosition < getElbowPosition()) {
         elbowMotorSpeed = -ELBOW_MOVE_SPEED;
       }
       if (isAtUpperElbowLimit() || isAtLowerElbowLimit()) {
        elbowDesiredPosition = getElbowPosition();
        elbowMotorSpeed = 0.0;
       }

       if (telescopeDesiredPosition > getTelescopePosition()) {
        telescopeMotorSpeed = TELESCOPE_MOVE_SPEED;
      } else if (telescopeDesiredPosition < getTelescopePosition()) {
        telescopeMotorSpeed = -TELESCOPE_MOVE_SPEED;
      }
      if (isAtFarTelescopeLimit() || isAtNearTelescopeLimit()) {
        telescopeDesiredPosition = getTelescopePosition();
        telescopeMotorSpeed = 0.0;
      }

      // keep moving if requested to be at home and we aren't
      if (requestedPreset == ArmPreset.kHome) {
        if (! isAtLowerElbowLimit()) {
          elbowMotorSpeed = -ELBOW_MOVE_SPEED;
        }
        if (! isAtNearTelescopeLimit()) {
          telescopeMotorSpeed = -TELESCOPE_MOVE_SPEED;
        }
      }
      if (requestedPreset == ArmPreset.kStop) {
        elbowMotorSpeed = 0.0;
        telescopeMotorSpeed = 0.0;
      }
      elbowMotor.set(elbowMotorSpeed);
      telescopeMotor.set(telescopeMotorSpeed);

      if (gripperSolenoidCounter < SOLENOID_HIT_COUNT) {
        gripperSolenoidCounter += 1;
        if (gripperState == GripperState.kOpen) {
            gripperSolenoid.set(DoubleSolenoid.Value.kForward);
        } else if (gripperState == GripperState.kClose) {
            gripperSolenoid.set(DoubleSolenoid.Value.kReverse);
        } else {
            gripperSolenoid.set(DoubleSolenoid.Value.kOff);
        }
      }
    }
  }

  public boolean isAtDesiredPosition() {
    if (enabled) {
    if ((Math.abs(getElbowPosition() - elbowDesiredPosition) < POSITION_TOLERANCE) && 
        (Math.abs(getTelescopePosition() - telescopeDesiredPosition) < POSITION_TOLERANCE) ) {
          return true;
    }
    }
    return false;
  }

  public void setGripperState(GripperState state) {
    if (state != gripperState) {
      gripperSolenoidCounter = 0;
      gripperState = state;
    }
  }
  public void openGripper() {
    if (enabled)
    setGripperState(GripperState.kOpen);
  }
  public void closeGripper() {
    if (enabled)
    setGripperState(GripperState.kClose);
  }

  public void stop() {
    if (enabled)
    setDesiredPosition(ArmPreset.kStop);
  }

  public void extendArm() {
    if (enabled)
    setDesiredPosition(getElbowPosition(),getTelescopePosition()+EXTEND_INCREMENT);
}

  public void retractArm() {
    if (enabled)
    setDesiredPosition(getElbowPosition(),getTelescopePosition()-EXTEND_INCREMENT);
  }

  public void raiseArm() {
    if (enabled)
    setDesiredPosition(getElbowPosition()+RAISE_INCREMENT,getTelescopePosition());
  }

  public void lowerArm() {
    if (enabled)
    setDesiredPosition(getElbowPosition()-RAISE_INCREMENT,getTelescopePosition());
  }

  public void setDesiredPosition(ArmPreset position) {
    requestedPreset = position;
    switch (position) {
      case kHome:
        setDesiredPosition(0.0, 0.0);
        break;
      case kCarry:
        setDesiredPosition(0.0, 0.0);
        break;
      case kLowScore:
        setDesiredPosition(0.0, 0.0);
        break;
      case kMidScore:
        setDesiredPosition(0.0, 0.0);
        break;
      case kHighScore:
        setDesiredPosition(0.0, 0.0);
        break;
      case kFloorPickup:
        setDesiredPosition(0.0, 0.0);
        break;
      case kLoad:
        setDesiredPosition(0.0, 0.0);
        break;
      case kStop:
        setDesiredPosition(getElbowPosition(), getTelescopePosition());
        break;
    }
   }
  private void setDesiredPosition(double elbowAngleEncoderPosition, double telescopeEncoderPosition) {
    elbowDesiredPosition = elbowAngleEncoderPosition;
    telescopeDesiredPosition = telescopeEncoderPosition;
  }

  @Override
  public void simulationPeriodic() {
    
  }

  private double getTelescopePosition() {
    if (enabled) {
    return telescopeMotor.getEncoder().getPosition();
    } else {
        return 0.0;
    }
  }

  private double getElbowPosition() {
    if (enabled) {
    return elbowMotor.getEncoder().getPosition();
    } else {
        return 0.0;
    }
  }
  private boolean isAtLowerElbowLimit() {
	  if ( enabled ) {
		  if (elbowMotor.getReverseLimitSwitch(Type.kNormallyOpen).isPressed()) {
            elbowMotor.getEncoder().setPosition(0.0);
            return true;
          }
          return false;
	  }
	  else {
		  return false;
	  }
    
  }

  private boolean isAtUpperElbowLimit() {
    if ( enabled ) {
        return elbowMotor.getForwardLimitSwitch(Type.kNormallyOpen).isPressed();
    }
    else {
        return false;
    }
  
}
  private boolean isAtFarTelescopeLimit() {
	  if (enabled) {
		  return telescopeMotor.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
	  }
	  else {
		  return false;
	  }
  }

  private boolean isAtNearTelescopeLimit() {
    if (enabled) {
        if (telescopeMotor.getForwardLimitSwitch(Type.kNormallyOpen).isPressed()) {
          telescopeMotor.getEncoder().setPosition(0.0);
          return true;
        }
        return false;
    }
    else {
        return false;
    }
}}
