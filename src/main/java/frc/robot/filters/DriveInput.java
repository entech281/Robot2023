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
    private double yawAngleDegrees = 0.0; //only for field absolute driving
    
    public double getYawAngleDegrees() {
		return yawAngleDegrees;
	}

	public void setYawAngleDegrees(double yawAngleDegrees) {
		this.yawAngleDegrees = yawAngleDegrees;
	}

	//private boolean overrideYawLock = false;
    //private boolean overrideAutoYaw = false;

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
    }

    //copy constructor
	public DriveInput(DriveInput original) {
		super();
		this.forward = original.forward;
		this.right = original.right;
		this.rotation = original.rotation;
		this.yawAngleDegrees = original.yawAngleDegrees;
		//this.overrideYawLock = original.overrideYawLock;
		//this.overrideAutoYaw = original.overrideAutoYaw;
	}

	public DriveInput(double forward, double right, double rotation, double yawAngleDegrees, boolean overrideYawLock,
			boolean overrideAutoYaw) {
		super();
		this.forward = forward;
		this.right = right;
		this.rotation = rotation;
		this.yawAngleDegrees = yawAngleDegrees;
		//this.overrideYawLock = overrideYawLock;
		//this.overrideAutoYaw = overrideAutoYaw;
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

    //public boolean getOverrideYawLock() { return overrideYawLock; }
    //public void setOverrideYawLock(boolean overrideYawLock) { this.overrideYawLock = overrideYawLock; }

    //public boolean getOverrideAutoYaw() { return this.overrideAutoYaw; }
    //public void setOverrideAutoYaw(boolean overrideAutoYaw) { this.overrideAutoYaw = overrideAutoYaw; }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DriveInput)) {
            return false;
        }
        DriveInput driveInput = (DriveInput) o;
        //return forward == driveInput.forward && right == driveInput.right && rotation == driveInput.rotation && overrideYawLock == driveInput.overrideYawLock;
        return forward == driveInput.forward && right == driveInput.right && rotation == driveInput.rotation && yawAngleDegrees == driveInput.yawAngleDegrees;
    }

    @Override
    public int hashCode() {
        //return Objects.hash(forward, right, rotation, overrideYawLock);
        return Objects.hash(forward, right, rotation,yawAngleDegrees);
    }

//    public DriveInput clone() {
//        DriveInput clone = new DriveInput(forward, right, rotation,yawAngleDegrees);
//        //clone.setOverrideAutoYaw(overrideAutoYaw);
//        //clone.setOverrideYawLock(overrideYawLock);
//        return clone;
//    }
}