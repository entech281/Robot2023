package frc.robot.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class TestHoldYawFilter {
    private static final double SMALLDIFFERENCE = 0.01;
    
    @Test
    public void testDirection() {
        HoldYawFilter filter = new HoldYawFilter();
        DriveInput di = new DriveInput (0.5, -0.75, 0.0, 175.0);

        filter.enable(true);
        filter.setApplyCalculations(true);
        filter.updateSetpoint(179.0);
        DriveInput r = filter.filter(di);
        assertTrue(r.getRotation() < 0.0);

        di.setYawAngleDegrees(179.0);
        filter.updateSetpoint(175.0);
        r = filter.filter(di);
        assertTrue(r.getRotation() > 0.0);
    }

    @Test
    public void testContinuous() {
        HoldYawFilter filter = new HoldYawFilter();
        DriveInput di = new DriveInput (0.5, -0.75, 0.0, 175.0);

        filter.enable(true);
        filter.setApplyCalculations(true);
        filter.updateSetpoint(179.0);
        DriveInput ref = filter.filter(di);

        di.setYawAngleDegrees(179.0);
        filter.updateSetpoint(-177.0);
        DriveInput r = filter.filter(di);
        assertEquals(ref.getRotation(), r.getRotation(), SMALLDIFFERENCE);

        di.setYawAngleDegrees(179.0);
        filter.updateSetpoint(175.0);
        ref = filter.filter(di);

        filter.updateSetpoint(179.0);
        di.setYawAngleDegrees(-177.0);
        r = filter.filter(di);
        assertEquals(ref.getRotation(), r.getRotation(), SMALLDIFFERENCE);
    }
}
