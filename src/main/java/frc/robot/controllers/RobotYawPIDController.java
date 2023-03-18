package frc.robot.controllers;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.util.EntechUtils;
import frc.robot.util.StoppingCounter;

public class RobotYawPIDController extends PIDController {
	
	////THESE ARE DECOYS!!
	//REAL VALUES ARE RE_SET in holdyawfilter
	private static final double P_GAIN = 0.00;
	private static final double I_GAIN = 0.00;
    private static final double D_GAIN = 0.00;
    private static final double ANGLE_TOLERANCE = 1;
    private static final double SPEED_LIMIT = 0.5;
    private static final int STOP_COUNT = 4;
    private StoppingCounter counter;
    
    public RobotYawPIDController() {
        super(P_GAIN, I_GAIN, D_GAIN);
        counter = new StoppingCounter("RobotYawPID", STOP_COUNT);
        setParameters();
    }
    public RobotYawPIDController(double p, double i, double d) {
        super(p, i, d);
        counter = new StoppingCounter("RobotYawPID", STOP_COUNT);
        setParameters();
    }
    public RobotYawPIDController(double p, double i, double d, double period) {
        super(p, i, d, period);
        counter = new StoppingCounter("RobotYawPID", STOP_COUNT);
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
    
    public boolean isStable() {
        //return counter.isFinished(super.atSetpoint());
        return super.atSetpoint();
    }

    private void setParameters() {
        enableContinuousInput(-180.0, 180.0);
        setTolerance(ANGLE_TOLERANCE);
    }
}
