package frc.robot.filters;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestJoystickDeadband {
    private static final double SMALLDIFFERENCE = 0.01;
    
    @Test
    public void testNoFiltering() {
        JoystickDeadbandFilter filter = new JoystickDeadbandFilter();
        DriveInput di = new DriveInput (0.5, -0.75, 0.6);

        filter.setEnabled(true);

        DriveInput r = filter.filter(di);

        assertEquals(0.5, r.getForward(), SMALLDIFFERENCE);
        assertEquals(-0.75, r.getRight(), SMALLDIFFERENCE);
        assertEquals(0.6, r.getRotation(), SMALLDIFFERENCE);
    }

    @Test
    public void testFilterForward() {
        JoystickDeadbandFilter filter = new JoystickDeadbandFilter();
        DriveInput di = new DriveInput (0.05, 0.75, 0.6);

        filter.setEnabled(true);

        DriveInput r = filter.filter(di);

        assertEquals(0.0, r.getForward(), SMALLDIFFERENCE);
        assertEquals(0.75, r.getRight(), SMALLDIFFERENCE);
        assertEquals(0.6, r.getRotation(), SMALLDIFFERENCE);
    }

    @Test
    public void testFilterRightNegative() {
        JoystickDeadbandFilter filter = new JoystickDeadbandFilter();
        DriveInput di = new DriveInput (0.5, -0.075, 0.6);

        filter.setEnabled(true);

        DriveInput r = filter.filter(di);

        assertEquals(0.5, r.getForward(), SMALLDIFFERENCE);
        assertEquals(0.0, r.getRight(), SMALLDIFFERENCE);
        assertEquals(0.6, r.getRotation(), SMALLDIFFERENCE);
    }

    @Test
    public void testFilterMinMagnitude() {
        JoystickDeadbandFilter filter = new JoystickDeadbandFilter(0.2);
        DriveInput di = new DriveInput (0.5, -0.75, 0.15);

        filter.setEnabled(true);

        DriveInput r = filter.filter(di);

        assertEquals(0.5, r.getForward(), SMALLDIFFERENCE);
        assertEquals(-0.75, r.getRight(), SMALLDIFFERENCE);
        assertEquals(0.0, r.getRotation(), SMALLDIFFERENCE);
    }
}
