package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{

  private CANSparkMax elbowMotor;
  private CANSparkMax telescopeMotor;

  public ArmStatus getStatus(){
      return new ArmStatus();
  }
  
  @Override
  public void initialize() {
    elbowMotor = new CANSparkMax(RobotConstants.ARM.ELBOW_MOTOR_ID, MotorType.kBrushed);
    telescopeMotor = new CANSparkMax(RobotConstants.ARM.TELESCOPE_MOTOR_ID, MotorType.kBrushed);

    elbowMotor.setInverted(false);
    telescopeMotor.setInverted(false);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType(getName());
      builder.addDoubleProperty("Elbow Motor", () -> { return elbowMotor.get(); }, null);
      builder.addDoubleProperty("Telescope Motor", () -> { return telescopeMotor.get(); }, null);
  }

  @Override
  public void periodic() {
    
  }

  @Override
  public void simulationPeriodic() {
    
  }

  private boolean isAtLowerElbowLimit() {
    return elbowMotor.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
  }

  private boolean isAtFarTelescopeLimit() {
    return telescopeMotor.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
  }
}
