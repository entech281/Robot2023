package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.commands.supplier.LateralOffsetSupplier;
import frc.robot.controllers.RobotLateralPIDController;
import frc.robot.controllers.RobotYawPIDController;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LEDSubsystem;

/**
 * Tries to align to the target scoring location, by rotating the robot about its axis
 * (Operator can still drive in the meantime)
 * The scoring location is set the start of the command. To align to a new target, make a new command instance
 * @param joystick the joystick you controll the robot with
 *
 *
 * @author dcowden
 */
public class HorizontalAlignWithTagCommand extends EntechCommandBase {

    private static final double YAW_P_GAIN = 0.02;
    private static final double YAW_I_GAIN = 0.001;	

    private static final double LATERAL_P_GAIN = 0.02;
    private static final double LATERAL_I_GAIN = 0.001;	

    public static final double TOLERANCE_METERS = 0.06;
    
    protected final DriveSubsystem drive;
    protected final LEDSubsystem led;
    protected final LateralOffsetSupplier lateralOffsetSupplier;
    protected final Supplier<DriveInput> operatorInput;
    private RobotLateralPIDController lateralPid;
    private RobotYawPIDController yawPid;
    


    /**
     * Tries to align to the target scoring location, by rotating the robot about its axis
     * (Operator can still drive in the meantime)
     * @param joystick the joystick you controll the robot with
     */
    public HorizontalAlignWithTagCommand(DriveSubsystem drive, LEDSubsystem led, Supplier<DriveInput> operatorInput, LateralOffsetSupplier lateralOffsetSupplier ) {
        super(drive,led);
        this.drive = drive;
        this.led = led;
        this.lateralOffsetSupplier = lateralOffsetSupplier;
        this.operatorInput = operatorInput;
        
        lateralPid = new RobotLateralPIDController();
        lateralPid.setP(LATERAL_P_GAIN);
        lateralPid.setI(LATERAL_I_GAIN);
        lateralPid.setSetpoint(0);        
        lateralPid.reset();
        
        yawPid = new RobotYawPIDController();
        yawPid.setP(YAW_P_GAIN);
        yawPid.setI(YAW_I_GAIN);

        yawPid.reset();
                
    }
    
    @Override
    public void initialize() {
    	//lets hold it whereever we started
    	yawPid.setSetpoint( operatorInput.get().getYawAngleDegrees());    	
    }

    @Override
    public void execute() {
        DriveInput di = operatorInput.get();        
        DriveInput newDi = new DriveInput(di);
        
        double rot = yawPid.calculate(di.getYawAngleDegrees());
        newDi.setRotation(rot);
        
        if ( lateralOffsetSupplier.getLateralOffset().isPresent()) {
        	double lateralOffset = lateralOffsetSupplier.getLateralOffset().get();        	
        	double calcValue = lateralPid.calculate(lateralOffset);      	
        	newDi.setRight(calcValue);
        	if ( Math.abs(lateralOffset) < TOLERANCE_METERS) {
        		led.setAligned();
        	}
        	else {
        		led.setAligning();
        	}
        }
        
        
        drive.drive(newDi);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();     
        led.setNormal();
    }

    @Override
    public boolean isFinished() {
    	return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
