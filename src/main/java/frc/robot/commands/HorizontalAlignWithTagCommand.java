package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.commands.supplier.LateralOffsetSupplier;
import frc.robot.controllers.RobotLateralPIDController;
import frc.robot.filters.DriveInput;
import frc.robot.filters.JoystickDeadbandFilter;
import frc.robot.filters.SquareInputsFilter;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.RobotConstants;

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

    private static final double LATERAL_P_GAIN = 2.1;
    private static final double LATERAL_I_GAIN = 0.1;	
    private static final double LATERAL_D_GAIN = 0.215;


    
    protected final DriveSubsystem drive;
    protected final LEDSubsystem led;
    protected final LateralOffsetSupplier lateralOffsetSupplier;
    protected final Supplier<DriveInput> operatorInput;
    private RobotLateralPIDController lateralPid;
    private double yawSetPoint;
    private JoystickDeadbandFilter jsDeadbandFilter;
    private SquareInputsFilter squareInputsFilter;
    


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
        lateralPid.setD(LATERAL_D_GAIN);
        lateralPid.setSetpoint(0);        
        lateralPid.reset();

        jsDeadbandFilter = new JoystickDeadbandFilter();
        jsDeadbandFilter.enable(true);
        jsDeadbandFilter.setDeadband(0.15);

        squareInputsFilter = new SquareInputsFilter();
        squareInputsFilter.setDampingFactor(RobotConstants.DRIVE.ROTATION_DAMPING_FACTOR);
        squareInputsFilter.enable(true);
    }
    
    @Override
    public void initialize() {
    	//lets hold it whereever we started
    	yawSetPoint	= operatorInput.get().getYawAngleDegrees();
        lateralPid.reset();
    }

    @Override
    public void execute() {
        DriveInput di = operatorInput.get();        
        DriveInput newDi = new DriveInput(di);

        newDi = jsDeadbandFilter.filter(newDi);
        newDi = squareInputsFilter.filter(newDi);
        
        double rot = YAW_P_GAIN*(di.getYawAngleDegrees() - yawSetPoint);
        newDi.setRotation(rot);
        
        if (lateralOffsetSupplier.getLateralOffset().isPresent()) {
        	double lateralOffset = lateralOffsetSupplier.getLateralOffset().get();        	
        	double calcValue = lateralPid.calculate(lateralOffset);
        	newDi.setRight(-calcValue);
        }
        drive.drive(newDi);
    }

    @Override
    public void end(boolean interrupted) {
    	drive.setHoldYawAngle(yawSetPoint);
        drive.stop();     
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
