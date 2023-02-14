package frc.robot.filters;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestTurnToggle {
    private static final double SMALLDIFFERENCE = 0.01;
    
    @Test
    public void testTurnToggleOn() {
        TurnToggleFilter filter = new TurnToggleFilter();
        DriveInput di = new DriveInput (0.5, 0.75, 0.6);

        filter.setEnabled(true);

        DriveInput r = filter.filter(di);

        assertEquals(0, r.getRotation(), SMALLDIFFERENCE);
        assertEquals(0.5, r.getForward(), SMALLDIFFERENCE);
        assertEquals(0.75, r.getRight(), SMALLDIFFERENCE);
    }

    @Test
    public void testTurnToggleOff() {
        TurnToggleFilter filter = new TurnToggleFilter();
        DriveInput di = new DriveInput (0.5, 0.75, 0.6);

        filter.setEnabled(false);

        DriveInput r = filter.filter(di);

        assertEquals(0.6, r.getRotation(), SMALLDIFFERENCE);
        assertEquals(0.5, r.getForward(), SMALLDIFFERENCE);
        assertEquals(0.75, r.getRight(), SMALLDIFFERENCE);
    }
}
