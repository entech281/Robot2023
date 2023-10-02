package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.commands.supplier.LateralOffsetSupplier;
import frc.robot.controllers.RobotLateralPIDController;
import frc.robot.filters.DriveInput;
import frc.robot.filters.JoystickDeadbandFilter;
import frc.robot.filters.SquareInputsFilter;
import frc.robot.subsystems.Drivetrain;
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

    private static final double LATERAL_P_GAIN = 1.25;
    private static final double LATERAL_I_GAIN = 0.001;	
    private static final double LATERAL_D_GAIN = 0.00;


    
    protected final Drivetrain drive;
    protected final LEDSubsystem led;
    protected final LateralOffsetSupplier lateralOffsetSupplier;
    protected final Supplier<DriveInput> operatorInput;
    private RobotLateralPIDController lateralPid;
    private JoystickDeadbandFilter jsDeadbandFilter;
    private SquareInputsFilter squareInputsFilter;
    


    /**
     * Tries to align to the target scoring location, by rotating the robot about its axis
     * (Operator can still drive in the meantime)
     * @param joystick the joystick you controll the robot with
     */
    public HorizontalAlignWithTagCommand(Drivetrain drive, LEDSubsystem led, Supplier<DriveInput> operatorInput, LateralOffsetSupplier lateralOffsetSupplier ) {
        super(drive,led);
        this.drive = drive;
        this.led = led;
        this.lateralOffsetSupplier = lateralOffsetSupplier;
        this.operatorInput = operatorInput;
        
        jsDeadbandFilter = new JoystickDeadbandFilter();
        jsDeadbandFilter.enable(true);
        jsDeadbandFilter.setDeadband(0.15);

        squareInputsFilter = new SquareInputsFilter();
        squareInputsFilter.enable(true);
    }
    
    @Override
    public void initialize() {
        lateralPid = new RobotLateralPIDController();
        lateralPid.setP(LATERAL_P_GAIN);
        lateralPid.setI(LATERAL_I_GAIN);
        lateralPid.setD(LATERAL_D_GAIN);
        lateralPid.setSetpoint(0);        
        lateralPid.reset();

        //enable after making sure lateral works
        //double nearestPerpendicularYawAngle = EntechUtils.nearestPerpendicularYawAngle(operatorInput.get().getYawAngleDegrees());
        //drive.setHoldYawAngle(nearestPerpendicularYawAngle);
    }

    @Override
    public void execute() {
        DriveInput di = operatorInput.get();        
        DriveInput newDi = new DriveInput(di);

        newDi = jsDeadbandFilter.filter(newDi);
        newDi = squareInputsFilter.filter(newDi);
        
        double lateralOffsetInput = 0.0;
        
        if (lateralOffsetSupplier.getLateralOffset().isPresent()) {
        	lateralOffsetInput = lateralOffsetSupplier.getLateralOffset().get();        	        	
        }
        
        //must calculate in every loop with built-in PID controller
        double lateralOutput = lateralPid.calculate(lateralOffsetInput);
        newDi.setRight(lateralOutput);
        drive.driveFilterYawOnly(newDi);
    }

    @Override
    public void end(boolean interrupted) {
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
