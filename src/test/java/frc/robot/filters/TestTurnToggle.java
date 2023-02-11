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

        filter.filter(di, null);

        assertEquals(0, di.getRotation(), SMALLDIFFERENCE);
        assertEquals(0.5, di.getForward(), SMALLDIFFERENCE);
        assertEquals(0.75, di.getRight(), SMALLDIFFERENCE);
    }

    @Test
    public void testTurnToggleOff() {
        TurnToggleFilter filter = new TurnToggleFilter();
        DriveInput di = new DriveInput (0.5, 0.75, 0.6);

        filter.setEnabled(false);

        filter.filter(di, null);

        assertEquals(0.6, di.getRotation(), SMALLDIFFERENCE);
        assertEquals(0.5, di.getForward(), SMALLDIFFERENCE);
        assertEquals(0.75, di.getRight(), SMALLDIFFERENCE);
    }
}
