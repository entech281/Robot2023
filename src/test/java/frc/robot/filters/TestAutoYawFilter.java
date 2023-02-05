package frc.robot.filters;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import frc.utils.TestGyro;

public class TestAutoYawFilter {
    private static final double SMALLDIFFERENCE = 0.01;
    
    @Test
    public void testAutoYawA() {
        TestGyro gyro = new TestGyro(90, 7.5);
        AutoYawFilter filter = new AutoYawFilter(gyro);
        DriveInput DI = new DriveInput(0, 1, 0);

        filter.setEnabled(true);

        while (!(gyro.getAngle() < 1 && gyro.getAngle() > -1)) {
            DI = new DriveInput(0, 1, 0);
            filter.filter(DI);
            gyro.simulateMove(DI.getZ());
        }

        assertEquals(gyro.getAngle(), 0, 1);
        assertEquals(DI.getZ(), 0, SMALLDIFFERENCE);
    }

    @Test
    public void testAutoYawB() {
        TestGyro gyro = new TestGyro(-45, 7.5);
        AutoYawFilter filter = new AutoYawFilter(gyro);
        DriveInput DI = new DriveInput(1, -1, 0);

        filter.setEnabled(true);

        while (!(gyro.getAngle() < -44 && gyro.getAngle() > -46)) {
            DI = new DriveInput(1, -1, 0);
            filter.filter(DI);
            gyro.simulateMove(DI.getZ());
        }

        assertEquals(gyro.getAngle(), -45, 1);
        assertEquals(DI.getZ(), 0, SMALLDIFFERENCE);
    }
}
