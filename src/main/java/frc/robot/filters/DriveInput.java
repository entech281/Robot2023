package frc.robot.filters;

import java.util.Objects;

/**
 *
 * 
 * @author aheitkamp
 */
public class DriveInput {
    private double forward;
    private double right;
    private double rotation;
    private double yawAngleDegrees = 0.0; //only for field abs
    private double rawYawAngle = 0.0; 
    
    public double getYawAngleDegrees() {
		return yawAngleDegrees;
	}

	public void setYawAngleDegrees(double yawAngleDegrees) {
		this.yawAngleDegrees = yawAngleDegrees;
	}

    public void setRawYawAngleDegrees(double yawAngle) {
        rawYawAngle = yawAngle;
    }

    public double getRawYawAngleDegrees() {
        return rawYawAngle;
    }

    /**
     *
     * 
     * @param forward the speed level forward for the drive subsystem
     * @param left the speed level right for the drive subsystem
     * @param right the speed level right for the drive subsystem
     */
    public DriveInput(double forward, double right, double rotation) {
        this.forward = forward;
        this.right = right;
        this.rotation = rotation;
    }
    
    /**
    *
    * 
    * @param forward the speed level forward for the drive subsystem
    * @param left the speed level right for the drive subsystem
    * @param right the speed level right for the drive subsystem
    * @param current yaw angle, for field absolute driving
    */    
    public DriveInput(double forward, double right, double rotation, double yawAngleDegrees) {
    	this(forward,right,rotation);
    	this.yawAngleDegrees = yawAngleDegrees;
        this.rawYawAngle = yawAngleDegrees;
    }

    //copy constructor
	public DriveInput(DriveInput original) {
		super();
		this.forward = original.forward;
		this.right = original.right;
		this.rotation = original.rotation;
		this.yawAngleDegrees = original.yawAngleDegrees;
        this.rawYawAngle = original.rawYawAngle;
	}

	public double getForward() {
		return forward;
	}

	public double getRight() {
		return right;
	}

	public double getRotation() {
		return rotation;
	}

	public void setForward(double forward) {
		this.forward = forward;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

    public double[] get() {
        double[] output = new double[3];

        output[0] = (forward);
        output[1] = (right);
        output[2] = (rotation);

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
        return forward == driveInput.forward && right == driveInput.right && rotation == driveInput.rotation 
           && yawAngleDegrees == driveInput.yawAngleDegrees && rawYawAngle == driveInput.rawYawAngle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(forward, right, rotation,yawAngleDegrees,rawYawAngle);
    }

}