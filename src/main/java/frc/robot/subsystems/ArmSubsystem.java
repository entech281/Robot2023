package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;
import com.revrobotics.SparkMaxPIDController.AccelStrategy;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;

/**
 *
 * @author dcowden
 */
public class ArmSubsystem extends EntechSubsystem{

  private CANSparkMax elbowMotor;
  private CANSparkMax telescopeMotor;

  private RelativeEncoder elbowEncoder;
  private RelativeEncoder telescopeEncoder;

  private SparkMaxPIDController elbowPidController;
  private SparkMaxPIDController telescopePidController;

  private interface PID {
    public static final double P = 0;
    public static final double I = 0;
    public static final double D = 0;
    public static final double FF = 0;
    public static final double IZ = 0;
    public static final double MAX_VELOCITY = 0;
    public static final double MIN_VELOCITY = 0;
    public static final double MAX_ACCEL = 0;
    public static final double ALLOWED_ERROR = 0;
  }

  private static final int SLOT = 0;

  public ArmStatus getStatus(){
      return new ArmStatus();
  }
  
  @Override
  public void initialize() {
    elbowMotor = new CANSparkMax(RobotConstants.ARM.ELBOW_MOTOR, MotorType.kBrushed);
    telescopeMotor = new CANSparkMax(RobotConstants.ARM.TELESCOPE_MOTOR, MotorType.kBrushed);

    elbowEncoder = elbowMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 150);
    telescopeEncoder = telescopeMotor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 150);

    elbowPidController = elbowMotor.getPIDController();
    telescopePidController = telescopeMotor.getPIDController();

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
    if (atLowerElbowLimit()) {
      elbowMotor.stopMotor();
    }

    if (atFarTelescopeLimit()) {
      telescopeMotor.stopMotor();
    }
  }

  @Override
  public void simulationPeriodic() {
    
  }

  public boolean atLowerElbowLimit() {
    return elbowMotor.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
  }

  public boolean atFarTelescopeLimit() {
    return telescopeMotor.getReverseLimitSwitch(Type.kNormallyOpen).isPressed();
  }

  public double getArmDistance() {
    return telescopeEncoder.getPosition();
  }

  public double getElbowAngle() {
    return elbowEncoder.getPosition() * 360;
  }

  public void setTargetElbowAngle(double targetElbowAngle) {
    elbowPidController.setP(PID.P);
    elbowPidController.setI(PID.I);
    elbowPidController.setD(PID.D);
    elbowPidController.setFF(PID.FF);
    elbowPidController.setIZone(PID.IZ);
    
    elbowPidController.setSmartMotionMaxAccel(PID.MAX_VELOCITY, SLOT);
    elbowPidController.setSmartMotionMinOutputVelocity(PID.MIN_VELOCITY, SLOT);
    elbowPidController.setSmartMotionMaxAccel(PID.MAX_ACCEL, SLOT);
    elbowPidController.setSmartMotionAllowedClosedLoopError(PID.ALLOWED_ERROR, SLOT);

    elbowPidController.setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, SLOT);

    elbowPidController.setReference(targetElbowAngle/360, ControlType.kPosition);
  }
}
