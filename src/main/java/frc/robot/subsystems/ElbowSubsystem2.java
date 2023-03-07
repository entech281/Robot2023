package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotConstants;
import frc.robot.RobotConstants.ELBOW;
import frc.robot.controllers.PositionControllerConfig;


public class ElbowSubsystem2 extends BaseSparkMaxPositionSubsystem{


  public ElbowSubsystem2() {
	enabled = true;
  }

  @Override
  public void initialize() {
	if ( enabled ) {
		CANSparkMax elbowMotor = new CANSparkMax(RobotConstants.CAN.ELBOW_MOTOR_ID, MotorType.kBrushless);
	    elbowMotor.getPIDController().setP(ELBOW.TUNING.P_GAIN);
	    elbowMotor.getPIDController().setI(ELBOW.TUNING.I_GAIN);
	    elbowMotor.getPIDController().setD(ELBOW.TUNING.D_GAIN);
	    elbowMotor.setSmartCurrentLimit(ELBOW.SETTINGS.MAX_SPIKE_CURRENT);

	    elbowMotor.set(0);
	    elbowMotor.setIdleMode(IdleMode.kBrake);
	    elbowMotor.clearFaults();
	    elbowMotor.setInverted(ELBOW.SETTINGS.MOTOR_REVERSED);
	    elbowMotor.getEncoder().setPositionConversionFactor(ELBOW.SETTINGS.COUNTS_PER_DEGREE);
	    elbowMotor.getEncoder().setVelocityConversionFactor(ELBOW.SETTINGS.COUNTS_PER_DEGREE);
		PositionControllerConfig conf =  new PositionControllerConfig.Builder("ELBOW")
		    	.withHomingOptions(ELBOW.HOMING.HOMING_SPEED_PERCENT  )
		    	.withPositionTolerance(ELBOW.SETTINGS.MOVE_TOLERANCE_DEGREES)
		    	.withSoftLimits(ELBOW.POSITION_PRESETS.MIN_POSITION_DEGREES, ELBOW.POSITION_PRESETS.MAX_POSITION_DEGREES)
		    	.withInverted(true)
		    	.build();
		
		super.configure(elbowMotor,conf);
	}
  }

  @Override
  public ElbowStatus getStatus() {
	  if ( enabled) {
		  return new ElbowStatus(getActualPosition());
	  }
	  else {
		  return new ElbowStatus(RobotConstants.INDICATOR_VALUES.POSITION_UNKNOWN);
	  }
  }

  @Override
  public void simulationPeriodic() {

  }

}
