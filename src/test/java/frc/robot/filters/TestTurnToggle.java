package frc.robot.filters;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 *
 * @author aheitkamp
 */
public class TestTurnToggle {
    private static final double SMALLDIFFERENCE = 0.001;

    @Test
    public void testTurnToggleOff() {
        TurnToggleFilter ttf = new TurnToggleFilter();
        DriveInput DI = new DriveInput(5, 6, 7);

        ttf.filter(DI);

        assertEquals(DI.getX(), 5, SMALLDIFFERENCE);
        assertEquals(DI.getY(), 6, SMALLDIFFERENCE);
        assertEquals(DI.getZ(), 0, SMALLDIFFERENCE);
    }   

    @Test
    public void testTurnToggleOn() {
        TurnToggleFilter ttf = new TurnToggleFilter();
        DriveInput DI = new DriveInput(5, 6, 7);

        ttf.setEnabled(true);
        ttf.filter(DI);

        assertEquals(DI.getX(), 0, SMALLDIFFERENCE);
        assertEquals(DI.getY(), 6, SMALLDIFFERENCE);
        assertEquals(DI.getZ(), 7, SMALLDIFFERENCE);
    }   
}
