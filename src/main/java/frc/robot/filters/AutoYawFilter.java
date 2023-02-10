package frc.robot.filters;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.pose.RobotPose;

/**
 *
 * 
 * @author aheitkamp
 */
public class AutoYawFilter extends Filter {
    private PIDController PID;

    public static final double P_GAIN = 0.095;
    public static final double I_GAIN = 0.15;
    public static final double D_GAIN = 0.0085;
    public static final double MOVE_TOLERANCE_DEGREES = 2.0;
    public static final double ROTATION_TOLERANCE_DEGREES = 0.1;
    public static final double ROTATION_THRESHOLD = 0.75;
    
    public AutoYawFilter() {

        PID = new PIDController(P_GAIN, I_GAIN, D_GAIN);
        PID.setTolerance(MOVE_TOLERANCE_DEGREES);
        PID.enableContinuousInput(-180, 180);
    }

    public void filter(DriveInput di, RobotPose currentPose) {
    	double currentAngle = currentPose.getCalculatedPose().getRotation().getDegrees();
        if (!enable) {
            PID.reset();
            return;
        }

        if (Math.abs(di.getForward()) < ROTATION_TOLERANCE_DEGREES && Math.abs(di.getRight()) < ROTATION_TOLERANCE_DEGREES) {
            PID.reset();
            return;
        }

        if (di.getOverrideAutoYaw()) {
            PID.reset();
            return;
        }

        double setPoint = Math.toDegrees(Math.atan2(di.getRight(), di.getForward()));

        if (Math.abs(setPoint - currentAngle) > 90) {
            setPoint += 180;
        }

        double calcValue = PID.calculate(currentAngle, setPoint);
        calcValue = Math.round(calcValue/4) * 4;  //what?

        di.setOverrideYawLock(true);
        di.setRotation(Math.max(-ROTATION_THRESHOLD, Math.min(calcValue, ROTATION_THRESHOLD)));
    }
}
