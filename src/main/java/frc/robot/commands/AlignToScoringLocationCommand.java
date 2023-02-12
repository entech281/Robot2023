package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.filters.DriveInput;
import frc.robot.pose.AlignmentCalculator;
import frc.robot.pose.ScoringLocation;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Tries to align to the target scoring location, by rotating the robot about its axis
 * (Operator can still drive in the meantime)
 * The scoring location is set the start of the command. To align to a new target, make a new command instance
 * @param joystick the joystick you controll the robot with
 * 
 * 
 * @author dcowden
 */
public class AlignToScoringLocationCommand extends EntechCommandBase {

	public static final double P_GAIN = 0.0865;
	public static final double I_GAIN = 0.5;
    public static final double D_GAIN = 0.0075;
    public static final double ANGLE_TOLERANCE = 1;
    public static final double SPEED_LIMIT = 0.75;
    public static final int STOP_COUNT = 4;
    private final DriveSubsystem drive;
    private final PIDController pid;
    private final Joystick joystick;
    ScoringLocation scoringLocation;
    private Supplier<Pose2d> currentPoseSupplier;
    private StoppingCounter counter;
    private AlignmentCalculator alignCalculator = new AlignmentCalculator();
    
    
    /**
     * Tries to align to the target scoring location, by rotating the robot about its axis
     * (Operator can still drive in the meantime)
     * @param joystick the joystick you controll the robot with
     */
    public AlignToScoringLocationCommand(DriveSubsystem drive,  Joystick joystick, ScoringLocation scoringLocation, Supplier<Pose2d> currentPoseSupplier) {
        super(drive);
        this.drive = drive;
        this.joystick = joystick;
        this.scoringLocation = scoringLocation;
        this.currentPoseSupplier = currentPoseSupplier;
        pid = new PIDController(P_GAIN, I_GAIN, D_GAIN);
        pid.enableContinuousInput(-180, 180);
        pid.setTolerance(ANGLE_TOLERANCE);
        counter = new StoppingCounter("AlignToScoringLocationCommand",STOP_COUNT);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    	double angleToTargetDegrees = alignCalculator.calculateAngleToScoringLocation(scoringLocation, currentPoseSupplier.get());
    	pid.setSetpoint(angleToTargetDegrees);
    	double currentYawAngleDegrees = currentPoseSupplier.get().getRotation().getDegrees();
    	
        double calcValue = Math.max(
            -SPEED_LIMIT, 
            Math.min(
                pid.calculate(
                    MathUtil.inputModulus(currentYawAngleDegrees, -180.0, 180.0), 
                    angleToTargetDegrees
                ), 
                SPEED_LIMIT
            )
        );
        DriveInput di = new DriveInput(-joystick.getY(), joystick.getX(), calcValue);
        di.setOverrideYawLock(true);
        di.setOverrideAutoYaw(true);
        drive.drive(di );
    }

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
