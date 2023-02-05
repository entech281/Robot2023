package frc.robot.filters;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestTurnToggle {
    private static final double SMALLDIFFERENCE = 0.01;
    
    @Test
    public void testTurnToggleOff() {
        TurnToggleFilter filter = new TurnToggleFilter();
        DriveInput DI = new DriveInput (0.5, 0.75, 0.6);

        filter.setEnabled(false);

        filter.filter(DI);

        assertEquals(0, DI.getZ(), SMALLDIFFERENCE);
        assertEquals(0.5, DI.getX(), SMALLDIFFERENCE);
        assertEquals(0.75, DI.getY(), SMALLDIFFERENCE);
    }

    @Test
    public void testTurnToggleOn() {
        TurnToggleFilter filter = new TurnToggleFilter();
        DriveInput DI = new DriveInput (0.5, 0.75, 0.6);

        filter.setEnabled(true);

        filter.filter(DI);

        assertEquals(0.6, DI.getZ(), SMALLDIFFERENCE);
        assertEquals(0.5, DI.getX(), SMALLDIFFERENCE);
        assertEquals(0.75, DI.getY(), SMALLDIFFERENCE);
    }
}
