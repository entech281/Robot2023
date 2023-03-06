package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.ARM;
import frc.robot.controllers.PositionControllerConfig;


import static frc.robot.RobotConstants.ARM.*;


public class ArmSubsystem2 extends BaseSparkMaxPositionSubsystem{


  public ArmSubsystem2() {
	enabled = true;
	liveMode = true;
  }

  @Override
  public void initialize() {
	if ( isEnabled() ) {
		CANSparkMax telescopeMotor = new CANSparkMax(22, MotorType.kBrushless);
	    SparkMaxPIDController pid = telescopeMotor.getPIDController();
	    pid.setP(TUNING.P_GAIN);
	    pid.setI(TUNING.I_GAIN);
	    pid.setD(TUNING.D_GAIN);
	    pid.setOutputRange(-1.0,1.0);

	    telescopeMotor.setIdleMode(IdleMode.kBrake);
	    telescopeMotor.clearFaults();
	    telescopeMotor.setInverted(ARM.SETTINGS.MOTOR_REVERSED);
	    telescopeMotor.getEncoder().setPositionConversionFactor( ARM.SETTINGS.COUNTS_PER_METER);
	    telescopeMotor.getEncoder().setVelocityConversionFactor(ARM.SETTINGS.COUNTS_PER_METER);
	    
	    PositionControllerConfig conf = new PositionControllerConfig.Builder("ARM")
	    	.withSoftLimits(ARM.POSITION_PRESETS.MIN_METERS, ARM.POSITION_PRESETS.MAX_METERS)
	    	.withHomingOptions(ARM.HOMING.HOMING_SPEED_PERCENT)
	    	.withPositionTolerance(ARM.SETTINGS.MOVE_TOLERANCE_METERS)
	    	.withInverted(true)
	    	.build();

	    super.configure(telescopeMotor, conf);	 
	    
	}
  }

  public ArmStatus getStatus(){
	  if ( enabled) {
		  return new ArmStatus(getActualPosition());
	  }
	  else {
		  return new ArmStatus(RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN);
	  }
  }
  
	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType(getName());		
		super.initSendable(builder);
	}

  @Override
  public void simulationPeriodic() {

  }

}
