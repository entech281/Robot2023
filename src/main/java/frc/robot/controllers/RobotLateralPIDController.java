package frc.robot.controllers;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.util.EntechUtils;
import frc.robot.util.StoppingCounter;

public class RobotLateralPIDController extends PIDController {
	
	private static final double P_GAIN = 0.5;
	private static final double I_GAIN = 0.01;
    private static final double D_GAIN = 0.00;
    private static final double TOLERANCE_METERS = 0.05;
    private static final double SPEED_LIMIT = 0.75;
    private static final int STOP_COUNT = 4;
    private StoppingCounter counter;
    
    public RobotLateralPIDController() {
        super(P_GAIN, I_GAIN, D_GAIN);
        counter = new StoppingCounter("RobotLateralPIDController", STOP_COUNT);
        setParameters();
    }
    public RobotLateralPIDController(double p, double i, double d) {
        super(p, i, d);
        counter = new StoppingCounter("RobotLateralPIDController", STOP_COUNT);
        setParameters();
    }
    public RobotLateralPIDController(double p, double i, double d, double period) {
        super(p, i, d, period);
        counter = new StoppingCounter("RobotLateralPIDController", STOP_COUNT);
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
        return counter.isFinished(super.atSetpoint());
    }

    private void setParameters() {
        setTolerance(TOLERANCE_METERS);
    }
}
