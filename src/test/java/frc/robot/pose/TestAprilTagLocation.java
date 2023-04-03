// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.pose;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import frc.robot.RobotConstants;


/** Add your docs here. */
public class TestAprilTagLocation {

    @Test
    public void TestBlueLoading() {
        AprilTagLocation loc = AprilTagLocation.BLUE_LOADING;
        assertEquals(loc.getXMeters(), 16.178784, RobotConstants.TEST.TOLERANCE_DISTANCE);
        assertEquals(loc.getYMeters(), 6.749796, RobotConstants.TEST.TOLERANCE_DISTANCE);
    }

    public void TestRedLeft() {
        AprilTagLocation loc = AprilTagLocation.RED_LEFT;
        assertEquals(loc.getXMeters(), 15.513558, RobotConstants.TEST.TOLERANCE_DISTANCE);
        assertEquals(loc.getYMeters(), 4.424426, RobotConstants.TEST.TOLERANCE_DISTANCE);
    }
}
