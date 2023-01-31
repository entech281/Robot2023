package frc.robot.pose;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.filters.DriveInput;


public class TestPoseCalcs {

    @Test
    public void TestAlignCalcZero() {
        var pose2dInit = new Pose2d(0,0,Rotation2d.fromDegrees(0));
        var pose2dFinal = new Pose2d(0,0,Rotation2d.fromDegrees(0));

        DriveInput di = AlignCalc.CalculateDrive(pose2dInit, pose2dFinal);
        assertEquals(new DriveInput(0.,0.,0.), di);
    }

    @Test
    public void TestAlignCalcY() {
        var pose2dInit = new Pose2d(0,0,Rotation2d.fromDegrees(0));
        var pose2dStraight = new Pose2d(0,1000,Rotation2d.fromDegrees(0));

        assertEquals(new DriveInput(0,-1,0), AlignCalc.CalculateDrive(pose2dInit, pose2dStraight));
    }}
