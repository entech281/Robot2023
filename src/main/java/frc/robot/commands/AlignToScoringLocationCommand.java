package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.filters.DriveInput;
import frc.robot.pose.AlignmentCalculator;
import frc.robot.pose.ScoringLocation;
import frc.robot.subsystems.DriveSubsystem;
import java.util.function.Supplier;
import frc.robot.filters.DriveInput;


/**
 * Tries to align to the target scoring location, by rotating the robot about its axis
 * (Operator can still drive in the meantime)
 * The scoring location is set the start of the command. To align to a new target, make a new command instance
 * @param joystick the joystick you controll the robot with
 * 
 * 
 * @author dcowden
 */
public class AlignToScoringLocationCommand extends BaseDrivePIDCommand {

    private Supplier<ScoringLocation> scoringLocationSupplier;
    private Supplier<Pose2d> currentPoseSupplier;
    private AlignmentCalculator alignCalculator = new AlignmentCalculator();
 
    
    /**
     * Tries to align to the target scoring location, by rotating the robot about its axis
     * (Operator can still drive in the meantime)
     * @param joystick the joystick you controll the robot with
     */
    public AlignToScoringLocationCommand(DriveSubsystem drive,
    		Supplier<DriveInput> operatorInput, 
    		Supplier<ScoringLocation> scoringLocationSupplier, 
    		Supplier<Pose2d> currentPoseSupplier) {
        super(drive,operatorInput);
        this.scoringLocationSupplier = scoringLocationSupplier;
        this.currentPoseSupplier = currentPoseSupplier;
    }

    @Override
    public void execute() {
    	ScoringLocation currentScoringLocation = scoringLocationSupplier.get();
    	double angleToTargetDegrees = alignCalculator.calculateAngleToScoringLocation(currentScoringLocation, currentPoseSupplier.get());
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
        DriveInput di = operatorInput.get();
        di.setRotation(calcValue);
        drive.drive(di );
    }
}
