package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotYawPIDController;
import frc.robot.commands.supplier.EstimatedPoseSupplier;
import frc.robot.commands.supplier.ScoringLocationSupplier;
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

    protected final DriveSubsystem drive;
    protected final RobotYawPIDController pid;
    protected final Supplier<DriveInput> operatorInput;
    private ScoringLocationSupplier scoringLocationSupplier;
    private EstimatedPoseSupplier currentPoseSupplier;
    private AlignmentCalculator alignCalculator = new AlignmentCalculator();
 
    
    /**
     * Tries to align to the target scoring location, by rotating the robot about its axis
     * (Operator can still drive in the meantime)
     * @param joystick the joystick you controll the robot with
     */
    public AlignToScoringLocationCommand(DriveSubsystem drive,
    		Supplier<DriveInput> operatorInput, 
    		ScoringLocationSupplier scoringLocationSupplier, 
    		EstimatedPoseSupplier currentPoseSupplier) {
        super(drive);
        this.drive = drive;
        this.operatorInput = operatorInput;        
        this.scoringLocationSupplier = scoringLocationSupplier;
        this.currentPoseSupplier = currentPoseSupplier;
        
        pid = new RobotYawPIDController();        
    }

    @Override
    public void execute() {
    	if ( scoringLocationSupplier.getScoringLocation().isPresent()) {
        	ScoringLocation currentScoringLocation = scoringLocationSupplier.getScoringLocation().get();
        	Pose2d estimatedPose = currentPoseSupplier.getEstimatedPose().get();
        	
        	/**
        	 * TODO:the above is too basic. We need to handle what happens if:
        	 *   -- the command is started without a scoringlocation or an estimated pose
        	 *   -- we lose either one during one execution loop
        	 *   -- the scoring location changes from the originally provided one
        	 */
        	
        	double angleToTargetDegrees = alignCalculator.calculateAngleToScoringLocation(currentScoringLocation, estimatedPose);
        	double currentYawAngleDegrees = operatorInput.get().getYawAngleDegrees();
        	
        	pid.setSetpoint(angleToTargetDegrees);
            SmartDashboard.putNumber("Auto Align Angle", angleToTargetDegrees);
        	
            double calcValue = pid.calculate(currentYawAngleDegrees, angleToTargetDegrees);
            DriveInput di = operatorInput.get();
            di.setRotation(calcValue);
            drive.drive(di);    		
    	} else {
            DriveInput di = new DriveInput(0, 0, 0, 0);
            drive.drive(di);
        }

    }
    
    @Override
    public void end(boolean interrupted) {
        drive.brake();
    }

    @Override
    public boolean isFinished() {
    	return pid.atSetpoint();    	
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }    
}
