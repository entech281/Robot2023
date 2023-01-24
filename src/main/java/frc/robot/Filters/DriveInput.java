package frc.robot.filters;

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
}