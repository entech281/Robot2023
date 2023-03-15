package frc.robot.filters;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class TestNoRotationToggle {
    private static final double SMALLDIFFERENCE = 0.01;
    
    @Test
    public void testNoRotationOn() {
        NoRotationFilter filter = new NoRotationFilter();
        DriveInput di = new DriveInput (0.5, 0.75, 0.6);

        filter.enable(true);

        DriveInput r = filter.filter(di);

        assertEquals(0, r.getRotation(), SMALLDIFFERENCE);
        assertEquals(0.5, r.getForward(), SMALLDIFFERENCE);
        assertEquals(0.75, r.getRight(), SMALLDIFFERENCE);
    }

    @Test
    public void testNoRotationOff() {
        NoRotationFilter filter = new NoRotationFilter();
        DriveInput di = new DriveInput (0.5, 0.75, 0.6);

        filter.enable(false);

        DriveInput r = filter.filter(di);

        assertEquals(0.6, r.getRotation(), SMALLDIFFERENCE);
        assertEquals(0.5, r.getForward(), SMALLDIFFERENCE);
        assertEquals(0.75, r.getRight(), SMALLDIFFERENCE);
    }
}
