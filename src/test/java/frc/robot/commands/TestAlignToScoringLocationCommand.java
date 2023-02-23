package frc.robot.commands;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.supplier.EstimatedPoseSupplier;
import frc.robot.commands.supplier.ScoringLocationSupplier;
import frc.robot.filters.DriveInput;
import frc.robot.pose.AprilTagLocation;
import frc.robot.pose.ScoringLocation;
import frc.robot.pose.TargetNode;
import frc.robot.subsystems.DriveSubsystem;

public class TestAlignToScoringLocationCommand {

	public static final double TOLERANCE = 0.01;
	@Test
	public void testOneDoesNotSimplySetUpTheCommand() {
		
		
		ScoringLocation testLocation = new ScoringLocation(AprilTagLocation.RED_MIDDLE, TargetNode.B2); //directly red middle, red #2
		DriveInput operatorInput = new DriveInput(0,0,0,0); //this is the operator with no stick input
		DriveSubsystem drive = new DriveSubsystem();
		drive.initialize();
		
		Pose2d robotPose = new Pose2d(10,10,Rotation2d.fromDegrees(0)); //robot pointing directly at B2
		
		AlignToScoringLocationCommand c = new AlignToScoringLocationCommand(
				drive,
				new Supplier<DriveInput>() {
					public DriveInput get() {
						return operatorInput;
					}					
				},
				new ScoringLocationSupplier() {
					@Override
					public Optional<ScoringLocation> getScoringLocation() {
						return Optional.of(testLocation);
					}
				},
				new EstimatedPoseSupplier() {

					@Override
					public Optional<Pose2d> getEstimatedPose() {
						return Optional.of(robotPose);
					}
					
				}
				
		);
		
		c.initialize();
		c.execute();
		
		//in this state, we should have no output-- we're directly facing the target
		assertEquals(0,c.pid.getSetpoint(),TOLERANCE);
		assertTrue(c.pid.atSetpoint());
		assertEquals(0,drive.getLastDriveInput().getRotation(),TOLERANCE);
		
	}
}
