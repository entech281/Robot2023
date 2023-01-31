package frc.robot.filters;

import java.util.Objects;

public class DriveInput {
    private double x;
    private double y;
    private double z;

    public DriveInput(double X, double Y, double Z) {
        x = X;
        y = Y;
        z = Z;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    public void setX(double X) { x = X; }
    public void setY(double Y) { y = Y; }
    public void setZ(double Z) { z = Z; }

    public double[] get() {
        double[] output = new double[3];

        output[0] = (x);
        output[1] = (y);
        output[2] = (z);

        return output;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DriveInput)) {
            return false;
        }
        DriveInput driveInput = (DriveInput) o;
        return x == driveInput.x && y == driveInput.y && z == driveInput.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

}