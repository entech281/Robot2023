package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotYawPIDController;
import frc.robot.commands.supplier.TargetYawSupplier;
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
    private EstimatedPoseSupplier currentPoseSupplier;
    private TargetYawSupplier alignAngleSupplier;
    private AlignmentCalculator alignCalculator = new AlignmentCalculator();

    
    /**
     * Tries to align to the target scoring location, by rotating the robot about its axis
     * (Operator can still drive in the meantime)
     * @param joystick the joystick you controll the robot with
     */
    public AlignToScoringLocationCommand(DriveSubsystem drive,
    		Supplier<DriveInput> operatorInput, 
    		EstimatedPoseSupplier currentPoseSupplier,
    		TargetYawSupplier alignAngleSupplier) {
        super(drive);
        this.drive = drive;
        this.operatorInput = operatorInput;        
        this.alignAngleSupplier = alignAngleSupplier;
        this.currentPoseSupplier = currentPoseSupplier;
        
        pid = new RobotYawPIDController();        
    }

    @Override
    public void execute() {
        DriveInput di = operatorInput.get();
        
    	if ( alignAngleSupplier.getTargetYawAngle().isPresent() && currentPoseSupplier.getEstimatedPose().isPresent()) {
    		
        	Pose2d estimatedPose = currentPoseSupplier.getEstimatedPose().get();
        	
        	double currentRobotAngle = estimatedPose.getRotation().getDegrees();
        	
        	double objectiveAngle = alignAngleSupplier.getTargetYawAngle().get();
        	SmartDashboard.putNumber("ObjectiveAngleFromAlign", objectiveAngle);
        	SmartDashboard.putData(pid);

        	pid.setSetpoint(0);

            double calcValue = pid.calculate(objectiveAngle);

            di.setRotation(calcValue);
            drive.drive(di);    		
    	} else {
            drive.drive(di);
        }

    }
    
    @Override
    public void end(boolean interrupted) {
        drive.brake();
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
