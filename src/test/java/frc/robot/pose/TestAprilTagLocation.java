// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.pose;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;


/** Add your docs here. */
public class TestAprilTagLocation {

    public static double comparisonTolerance = 0.01;

    @Test
    public void TestBlueLoading() {
        AprilTagLocation loc = AprilTagLocation.BLUE_LOADING;
        assertEquals(loc.getXIn(), 636.96, comparisonTolerance);
        assertEquals(loc.getYIn(), 265.74, comparisonTolerance);
    }

    public void TestRedLeft() {
        AprilTagLocation loc = AprilTagLocation.RED_LEFT;
        assertEquals(loc.getXIn(), 610.77, comparisonTolerance);
        assertEquals(loc.getYIn(), 174.19, comparisonTolerance);
    }
}
