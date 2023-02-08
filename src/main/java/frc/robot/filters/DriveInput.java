package frc.robot.filters;

import java.util.Objects;

/**
 *
 * 
 * @author aheitkamp
 */
public class DriveInput {
    private double forward;
    private double left;
    private double rotation;

    private boolean overrideYawLock = false;
    private boolean overrideAutoYaw = false;

    /**
     *
     * 
     * @param Forward the speed level forward for the drive subsystem
     * @param Left the speed level left for the drive subsystem
     * @param Right the speed level right for the drive subsystem
     */
    public DriveInput(double Forward, double Left, double Rotation) {
        forward = Forward;
        left = Left;
        rotation = Rotation;
    }

    public double getForward() { return forward; }
    public double getLeft() { return left; }
    public double getRotation() { return rotation; }

    public void setForward(double Forward) { forward = Forward; }
    public void setLeft(double Left) { left = Left; }
    public void setRotation(double Rotation) { rotation = Rotation; }

    public double[] get() {
        double[] output = new double[3];

        output[0] = (forward);
        output[1] = (left);
        output[2] = (rotation);

        return output;
    }

    public boolean getOverrideYawLock() { return overrideYawLock; }
    public void setOverrideYawLock(boolean OverrideYawLock) { overrideYawLock = OverrideYawLock; }

    public boolean getOverrideAutoYaw() { return this.overrideAutoYaw; }
    public void setOverrideAutoYaw(boolean OverrideAutoYaw) { overrideAutoYaw = OverrideAutoYaw; }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DriveInput)) {
            return false;
        }
        DriveInput driveInput = (DriveInput) o;
        return forward == driveInput.forward && left == driveInput.left && rotation == driveInput.rotation && overrideYawLock == driveInput.overrideYawLock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(forward, left, rotation, overrideYawLock);
    }

}