package frc.robot.learning;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.MathUtil;

public class TestInputModulus {

    @Test
    public void testInputModulus() {
        
    double RobotYaw = 200; //What the angle of the robot

    double inputModulus = MathUtil.inputModulus(RobotYaw, -180.0, 180.0);

    assertEquals(inputModulus, -160, 0.01);
    }
}
