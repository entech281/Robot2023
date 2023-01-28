package frc.robot.filters;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 *
 * @author aheitkamp
 */
public class TestDriveInput {
    private static final double SMALLDIFFERENCE = 0.001;

    @Test
    public void testDriveInput() {
        DriveInput driveInputA = new DriveInput(0.0, 0.0, 0.0);
        DriveInput driveInputB = new DriveInput(0, 0, 0);
        assertEquals(driveInputA, driveInputB);
    }

    @Test
    public void testDriveInputGetters() {
        DriveInput DI = new DriveInput(0.0, 2.0, 3.0);

        assertEquals(DI.getX(), 0, SMALLDIFFERENCE);
        assertEquals(DI.getY(), 2, SMALLDIFFERENCE);
        assertEquals(DI.getZ(), 3, SMALLDIFFERENCE);
    }

    @Test
    public void testDriveInputSetters() {
        DriveInput DI = new DriveInput(0.0, 2.0, 3.0);

        DI.setX(5);
        DI.setY(0);
        DI.setZ(-1);

        assertEquals(DI.getX(), 5, SMALLDIFFERENCE);
        assertEquals(DI.getY(), 0, SMALLDIFFERENCE);
        assertEquals(DI.getZ(), -1, SMALLDIFFERENCE);
    }
}
