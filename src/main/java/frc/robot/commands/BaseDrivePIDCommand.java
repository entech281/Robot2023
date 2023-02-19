package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.StoppingCounter;

/**
 * Base class for PID Drive commands
 * @author davec
 *
 */
public abstract class BaseDrivePIDCommand extends EntechCommandBase {
	public static final double P_GAIN = 0.0865;
	public static final double I_GAIN = 0.5;
    public static final double D_GAIN = 0.0075;
    public static final double ANGLE_TOLERANCE = 1;
    public static final double SPEED_LIMIT = 0.75;
    public static final int STOP_COUNT = 4;
    protected final DriveSubsystem drive;
    protected final PIDController pid;
    protected final Supplier<DriveInput> operatorInput;
    protected StoppingCounter counter;
    /**
     * Creates a new snap yaw degrees command that will snap the robot to the specified angle
     * 
     *
     * @param drive The drive subsystem on which this command will run
     * @param latestPose  The supplier of latest pose of the robot to get the current yaw
     * @param joystick the joystick you controll the robot with
     */
    public BaseDrivePIDCommand(DriveSubsystem drive,  Supplier<DriveInput> operatorInput) {
        super(drive);
        this.drive = drive;
        this.operatorInput = operatorInput;

        pid = new PIDController(P_GAIN, I_GAIN, D_GAIN);
        pid.enableContinuousInput(-180, 180);
        pid.setTolerance(ANGLE_TOLERANCE);
        counter = new StoppingCounter("PIDDriveCommand",STOP_COUNT);
    }

    @Override
    public abstract void execute();
    
    @Override
    public void end(boolean interrupted) {
        drive.brake();
    }

    @Override
    public boolean isFinished() {
    	return counter.isFinished(pid.atSetpoint());    	
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
