// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.pose;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/** Add your docs here. */
public class TestAprilTagLocation {

    @Test
    public void TestBlueLoading() {
        AprilTagLocation loc = AprilTagLocation.BLUE_LOADING;
        assertEquals(loc.getX(), 636.96, 0.01);
        assertEquals(loc.getY(), 265.74, 0.01);
    }
}
