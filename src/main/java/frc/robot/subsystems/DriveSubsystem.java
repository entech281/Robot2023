/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;
import frc.robot.filters.DriveInput;
import frc.robot.filters.FieldPoseToFieldAbsoluteDriveFilter;
import frc.robot.filters.RobotRelativeDriveFilter;
import frc.robot.filters.ForwardSpeedLimitFilter;
import frc.robot.filters.SquareInputsFilter;
import frc.robot.filters.HoldYawFilter;
import frc.robot.filters.JoystickDeadbandFilter;
import frc.robot.filters.NoRotationFilter;

/**
 *
 *
 * @author aheitkamp
 */
public class DriveSubsystem extends EntechSubsystem {

    public enum DriveMode {
        BRAKE,
        COAST
    }
    // DriveFilters used
    private JoystickDeadbandFilter jsDeadbandFilter;
    private RobotRelativeDriveFilter robotRelativeFilter;
    private NoRotationFilter noRotationFilter;
    private FieldPoseToFieldAbsoluteDriveFilter yawAngleCorrectionFilter;
    private HoldYawFilter yawHoldFilter;
    private SquareInputsFilter jsSquareInputsFilter;
    private ForwardSpeedLimitFilter speedLimitFilter;
    private RelativeEncoder frontLeftEncoder;
    private RelativeEncoder rearLeftEncoder;
    private RelativeEncoder frontRightEncoder;
    private RelativeEncoder rearRightEncoder;
    private CANSparkMax frontLeftSparkMax;
    private CANSparkMax rearLeftSparkMax;
    private CANSparkMax frontRightSparkMax;
    private CANSparkMax rearRightSparkMax;
    private MecanumDrive robotDrive;
    private double referenceAvgPosition = 0.0;
    private DriveMode currentDriveMode;

    private static final int COUNTER_RESET = 6;
    private int holdYawSetPointCounter = COUNTER_RESET;

    public DriveSubsystem() {
    }

    public DriveStatus getStatus(){
        return new DriveStatus();
    }

    @Override
    public void initialize() {
        frontLeftSparkMax  = new CANSparkMax(RobotConstants.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        rearLeftSparkMax   = new CANSparkMax(RobotConstants.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
        frontRightSparkMax = new CANSparkMax(RobotConstants.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rearRightSparkMax  = new CANSparkMax(RobotConstants.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
        robotDrive         = new MecanumDrive(frontLeftSparkMax, rearLeftSparkMax, frontRightSparkMax, rearRightSparkMax);

        
        frontLeftSparkMax.setInverted(false);
        rearLeftSparkMax.setInverted(false);
        frontRightSparkMax.setInverted(true);
        rearRightSparkMax.setInverted(true);

        // frontLeftSparkMax.setSmartCurrentLimit(RobotConstants.DRIVE.CURRENT_LIMIT_AMPS);
        // rearLeftSparkMax.setSmartCurrentLimit(RobotConstants.DRIVE.CURRENT_LIMIT_AMPS);
        // frontRightSparkMax.setSmartCurrentLimit(RobotConstants.DRIVE.CURRENT_LIMIT_AMPS);
        // rearRightSparkMax.setSmartCurrentLimit(RobotConstants.DRIVE.CURRENT_LIMIT_AMPS);


        frontLeftEncoder = frontLeftSparkMax.getEncoder();
        rearLeftEncoder = rearLeftSparkMax.getEncoder();
        frontRightEncoder = frontRightSparkMax.getEncoder();
        rearRightEncoder = rearRightSparkMax.getEncoder();
        
        currentDriveMode = DriveMode.BRAKE;
        setBrakeMode();

        jsDeadbandFilter = new JoystickDeadbandFilter();
        jsDeadbandFilter.enable(true);
        jsDeadbandFilter.setDeadband(0.15);

        jsSquareInputsFilter = new SquareInputsFilter();
        jsSquareInputsFilter.enable(true);

        robotRelativeFilter = new RobotRelativeDriveFilter();
        yawAngleCorrectionFilter = new FieldPoseToFieldAbsoluteDriveFilter();
        setFieldAbsolute(RobotConstants.DRIVE.DEFAULT_FIELD_ABSOLUTE);

        noRotationFilter = new NoRotationFilter();
        noRotationFilter.enable(true);

        yawHoldFilter = new HoldYawFilter();
        yawHoldFilter.enable(true);

        speedLimitFilter = new ForwardSpeedLimitFilter();
        speedLimitFilter.enable(true);
        this.initEncoders();
        
    }

    @Override
    public void periodic() {
//        SmartDashboard.putNumber("Front Left SparkMax", frontLeftSparkMax.getEncoder().getPosition());
//        SmartDashboard.putNumber("Front Right SparkMax", frontRightSparkMax.getEncoder().getPosition());
//        SmartDashboard.putNumber("Back Left SparkMax", rearLeftSparkMax.getEncoder().getPosition());
//        SmartDashboard.putNumber("Back Right SparkMax", rearRightSparkMax.getEncoder().getPosition());
//        
//        SmartDashboard.putNumber("Average Position", getAverageDistanceMeters());
//        SmartDashboard.putNumber("Average Position Offset", referenceAvgPosition);
//        SmartDashboard.putBoolean("Field Absolute", isFieldAbsolute());
//        SmartDashboard.putBoolean("Rotation Allowed", isRotationEnabled());
//        SmartDashboard.putBoolean("Brake Mode", isBrakeMode());

        robotDrive.feed();
        robotDrive.feedWatchdog();
    }
    public void setMaxSpeedPercent( double maxSpeed) {
    	this.speedLimitFilter.setMaxSpeedPercent(maxSpeed);
    }
    public void clearSpeedLimit() {
    	speedLimitFilter.setMaxSpeedPercent(1.0);
    }

    public void drive(DriveInput di) {
        robotDrive.driveCartesian(di.getForward(), di.getRight(), di.getRotation(), Rotation2d.fromDegrees(di.getYawAngleDegrees()));
    }

    public void driveFilterYawOnly(DriveInput di) {

        if ( ! yawHoldFilter.isSetpointValid() ) {
            yawHoldFilter.setSetpoint(di.getYawAngleDegrees());
        }
        DriveInput filtered = yawHoldFilter.filter(di);
        drive(filtered);
    }

    public void driveFilterYawRobotRelative(DriveInput di) {
        if (!yawHoldFilter.isSetpointValid()) {
            yawHoldFilter.setSetpoint(di.getYawAngleDegrees());
        }
        DriveInput filtered = robotRelativeFilter.filter(yawHoldFilter.filter(di));
        drive(filtered);
    }
    public void filteredDrive(DriveInput di) {

        // Special case: set the setpoint for the HoldYawFilter if nothing has until now
        if ( ! yawHoldFilter.isSetpointValid() ) {
            yawHoldFilter.setSetpoint(di.getYawAngleDegrees());
        }

        // printDI("DI(0):",di);
    	DriveInput filtered = di;
        filtered = jsDeadbandFilter.filter(filtered);
        // printDI("DI(1):",filtered);
        filtered = jsSquareInputsFilter.filter(filtered);
        // printDI("DI(2):",filtered);

    	if (isRotationEnabled()) {
            // Drive holding trigger and is allowed to twist, update the hold yaw filter setpoint to current value
            // We run the holdyaw filter just to get the dashboard updated.
            yawHoldFilter.setSetpoint(di.getYawAngleDegrees());
            holdYawSetPointCounter = COUNTER_RESET;
        } else {
        	if (holdYawSetPointCounter > 0) {
        		yawHoldFilter.setSetpoint(di.getYawAngleDegrees());
                holdYawSetPointCounter--;
        	}
            if (yawHoldFilter.isEnabled()) {
                filtered = yawHoldFilter.filter(filtered);
                // printDI("DI(3)",filtered);
            } else {
    		    filtered = noRotationFilter.filter(filtered);
                // printDI("DI(4)",filtered);
            }
    	}

    	if (isFieldAbsolute()) {
            filtered = yawAngleCorrectionFilter.filter(filtered);
            // printDI("DI(5)",filtered);
            } else {
    		filtered = robotRelativeFilter.filter(filtered);
            // printDI("DI(6)",filtered);
        }

    	filtered = speedLimitFilter.filter(filtered);

        // printDI("DI(7)",filtered);
        drive(filtered);
    }

    private void printDI(String id,DriveInput di) {
        DriverStation.reportWarning(id+di.getForward()+","+di.getRight()+","+di.getRotation(), false);
    }

    public void stop() {
        robotDrive.stopMotor();
    }

    public void setHoldYawAngle(double angle) {
        yawHoldFilter.setSetpoint(angle);
    }

    public void nudgeYawLeft() {
        if ( yawHoldFilter.isSetpointValid() ) {
            yawHoldFilter.setSetpoint(yawHoldFilter.getSetpoint() + RobotConstants.DRIVE.YAW_NUDGE_DEGREES);
        }
    }

    public void nudgeYawRight() {
        if ( yawHoldFilter.isSetpointValid() ) {
            yawHoldFilter.setSetpoint(yawHoldFilter.getSetpoint() - RobotConstants.DRIVE.YAW_NUDGE_DEGREES);
        }
    }

    public void toggleBrakeCoastMode() {
        switch (currentDriveMode) {
        case BRAKE:
            setCoastMode();
            currentDriveMode = DriveMode.COAST;
            break;
        case COAST:
            setBrakeMode();
            currentDriveMode = DriveMode.BRAKE;
            break;
        }
    }

  public void setDriveMode(DriveMode mode) {
    if (mode != currentDriveMode) {
      switch (mode) {
        case BRAKE:
          setBrakeMode();
          break;
        case COAST:
          setCoastMode();
          break;
      }
      currentDriveMode = mode;
    }
  }

  private void setCoastMode() {
    frontLeftSparkMax.setIdleMode(IdleMode.kCoast);
    frontRightSparkMax.setIdleMode(IdleMode.kCoast);
    rearLeftSparkMax.setIdleMode(IdleMode.kCoast);
    rearRightSparkMax.setIdleMode(IdleMode.kCoast);
  }

  private void setBrakeMode() {
    frontLeftSparkMax.setIdleMode(IdleMode.kBrake);
    frontRightSparkMax.setIdleMode(IdleMode.kBrake);
    rearLeftSparkMax.setIdleMode(IdleMode.kBrake);
    rearRightSparkMax.setIdleMode(IdleMode.kBrake);
  }

  public boolean isBrakeMode() {
    return currentDriveMode == DriveMode.BRAKE;
  }

	@Override
    public void initSendable(SendableBuilder builder) {
  	    builder.setSmartDashboardType(getName());
  	    builder.addDoubleProperty("FrontLeft", () -> { return frontLeftEncoder.getPosition();} , null);
  	    builder.addDoubleProperty("FrontRight", () -> { return frontRightEncoder.getPosition();} , null);
  	    builder.addDoubleProperty("RearLeft", () -> { return rearLeftEncoder.getPosition();} , null);
  	    builder.addDoubleProperty("RearRight", () -> { return rearRightEncoder.getPosition();} , null);
        builder.addBooleanProperty("Field Absolute", this::isFieldAbsolute, null);
        builder.addBooleanProperty("Rotation Allowed", this::isRotationEnabled, null);
        builder.addBooleanProperty("Brake Mode", this::isBrakeMode, null);
        builder.addStringProperty("Command", this::getCurrentCommandName, null);
    }

	public String getCurrentCommandName() {
		if ( this.getCurrentCommand() != null ) {
			return this.getCurrentCommand().getName();
		}
		else {
			return "NONE";
		}
	}
    @Override
    public boolean isEnabled() {
	    return true;
    }
    public void initEncoders() {
    	trySetSparkMaxPosition(frontLeftEncoder,0);
    	trySetSparkMaxPosition(frontLeftEncoder,0);
    	trySetSparkMaxPosition(rearLeftEncoder,0);
    	trySetSparkMaxPosition(frontRightEncoder,0);
    	trySetSparkMaxPosition(rearRightEncoder,0);  
        referenceAvgPosition = getAveragePosition();
            	
    }
    public void resetEncoders() {
        //This approach is simpler than referencing each individual encoder.
        //normally that's not ok, but in this case, it is safe because we dont expose the individual encoders anywhere
    	referenceAvgPosition = getAveragePosition();        
  	
    }
    
    private void trySetSparkMaxPosition(RelativeEncoder encoder, double pos) {
    	REVLibError re = encoder.setPosition(pos);
    	if ( re != REVLibError.kOk) {
    		RuntimeException ex = new RuntimeException("Error setting Spark Posittion: " + re);
    		ex.printStackTrace();
    	}
    }

    /**
     *
     * @return average motor revolutions for the 4 motors
     */
    private double getAveragePosition() {
        double position = 0;

        position += frontLeftEncoder.getPosition();
        position += rearLeftEncoder.getPosition();
        position += frontRightEncoder.getPosition();
        position += rearRightEncoder.getPosition();
        //double p =  (position / 4.0) - referenceAvgPosition;

        double avgPos = position/4.0;
        return (avgPos - referenceAvgPosition);
        
        //DriverStation.reportWarning("AveragePosition:" + p, false);        
        //return p;
    }

    public double getAverageDistanceMeters() {
        double distance = (getAveragePosition() / RobotConstants.DRIVE.GEAR_BOX_RATIO) * RobotConstants.DRIVE.METERS_PER_GEARBOX_REVOLTION;
        return distance;
    }

    // Store drive state in the DriveSubsysten rather than the Shuffleboard
    public void setFieldAbsolute(boolean newValue) {
        if (newValue) {
            robotRelativeFilter.enable(false);
            yawAngleCorrectionFilter.enable(true);
        } else {
            robotRelativeFilter.enable(true);
            yawAngleCorrectionFilter.enable(false);
        }
	}
	public boolean isFieldAbsolute() {
		return ! isFieldRelative();
	}
	public boolean isFieldRelative() {
		return robotRelativeFilter.isEnabled();
	}
	public void toggleFieldAbsolute() {
		setFieldAbsolute( ! isFieldAbsolute() );
	}

	public void setRotationAllowed(boolean newValue) {
		noRotationFilter.enable(! newValue);
	}
	public boolean isRotationEnabled() {
		return ! isRotationLocked();
	}
	public boolean isRotationLocked() {
		return noRotationFilter.isEnabled();
	}

}
