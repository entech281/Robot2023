package frc.robot.Filters;

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

    public void setX(double X) { x = X;}
    public void setY(double Y) { y = Y;}
    public void setZ(double Z) { z = Z;}
}