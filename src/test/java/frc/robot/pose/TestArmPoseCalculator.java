package frc.robot.pose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.ElbowStatus;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.ELBOW;

public class TestArmPoseCalculator {

    public static final double TOLERANCE_DISTANCE = 0.01;
    
    @Test
    public void testFacingScoringLocation(){
        ScoringLocation sloc = new ScoringLocation(AprilTagLocation.BLUE_LEFT, TargetNode.A2);
        Pose2d robotPose = new Pose2d(1.8, 1.07, new Rotation2d(180));
        ElbowStatus elbowAngle = new ElbowStatus((int)(ELBOW.SETTINGS.COUNTS_PER_DEGREE * (90 - ELBOW.MIN_ANGLE_DEGREES)));
        ArmPoseCalculator armPose = new ArmPoseCalculator();
        ArmPoseResult armPoseAnswer = armPose.getDeploymentStatus(robotPose, sloc, elbowAngle);

        System.out.println(armPoseAnswer);
        assertEquals(1.42, armPoseAnswer.targetDistance, TOLERANCE_DISTANCE);
        assertEquals(true, armPoseAnswer.canDeploy);
    }

    @Test
    public void testTooClose(){
        ScoringLocation sloc = new ScoringLocation(AprilTagLocation.BLUE_LEFT, TargetNode.A2);
        Pose2d robotPose = new Pose2d(0.5, 1.07, new Rotation2d(180));
        ElbowStatus elbowAngle = new ElbowStatus((int)(ELBOW.SETTINGS.COUNTS_PER_DEGREE * (90 - ELBOW.MIN_ANGLE_DEGREES)));
        ArmPoseCalculator armPose = new ArmPoseCalculator();
        ArmPoseResult armPoseAnswer = armPose.getDeploymentStatus(robotPose, sloc, elbowAngle);

        assertEquals(0.12, armPoseAnswer.targetDistance, TOLERANCE_DISTANCE);
        assertEquals(false, armPoseAnswer.canDeploy);
    }

    @Test
    public void testTooFar(){
        ScoringLocation sloc = new ScoringLocation(AprilTagLocation.BLUE_LEFT, TargetNode.A2);
        Pose2d robotPose = new Pose2d(2.0, 1.07, new Rotation2d(180));
        ElbowStatus elbowAngle = new ElbowStatus((int)(ELBOW.SETTINGS.COUNTS_PER_DEGREE * (90 - ELBOW.MIN_ANGLE_DEGREES)));
        ArmPoseCalculator armPose = new ArmPoseCalculator();
        ArmPoseResult armPoseAnswer = armPose.getDeploymentStatus(robotPose, sloc, elbowAngle);

        assertEquals(1.62, armPoseAnswer.targetDistance, TOLERANCE_DISTANCE);
        assertEquals(false, armPoseAnswer.canDeploy);
    }

}
