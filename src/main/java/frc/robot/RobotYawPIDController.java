package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.util.EntechUtils;

import static frc.robot.commands.DriveCommandConstants.*;

public class RobotYawPIDController extends PIDController {
    
    public RobotYawPIDController() {
        super(P_GAIN, I_GAIN, D_GAIN);
        setParameters();
    }
    public RobotYawPIDController(double p, double i, double d) {
        super(p, i, d);
        setParameters();
    }
    public RobotYawPIDController(double p, double i, double d, double period) {
        super(p, i, d, period);
        setParameters();
    }

    @Override
    public double calculate(double measurement) {
        return EntechUtils.capDoubleValue(super.calculate(measurement),-SPEED_LIMIT,SPEED_LIMIT);
    }
    @Override
    public double calculate(double measurement, double setpoint) {
        return EntechUtils.capDoubleValue(super.calculate(measurement,setpoint),-SPEED_LIMIT,SPEED_LIMIT);
    }

    private void setParameters() {
        enableContinuousInput(-180.0, 180.0);
        setTolerance(ANGLE_TOLERANCE);
    }
}
