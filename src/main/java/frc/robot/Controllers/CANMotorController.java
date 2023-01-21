package frc.robot.Controllers;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class CANMotorController implements MotorController {

    public static enum MotorControllerType {
        TSRX,
        SMAX_BRUSHED,
        SMAX_BRUSHLESS
    }

    private final MotorControllerType type;
    private WPI_TalonSRX motorControllerTSRX;
    private CANSparkMax motorControllerSMAX;

    public CANMotorController(int CANID, MotorControllerType Type) {
        type = Type;

        switch (type) {
            case TSRX:
                motorControllerTSRX = new WPI_TalonSRX(CANID);
            case SMAX_BRUSHED:
                motorControllerSMAX = new CANSparkMax(CANID, MotorType.kBrushed);
            case SMAX_BRUSHLESS:
                motorControllerSMAX = new CANSparkMax(CANID, MotorType.kBrushless);
        }
    }

    @Override
    public void set(double speed) {
        switch (type) {
            case TSRX:
                motorControllerTSRX.set(speed);
            case SMAX_BRUSHED:
                motorControllerSMAX.set(speed);
            case SMAX_BRUSHLESS:
                motorControllerSMAX.set(speed);
        }
    }

    @Override
    public double get() {
        switch (type) {
            case TSRX:
                return motorControllerTSRX.get();
            case SMAX_BRUSHED:
                return motorControllerSMAX.get();
            case SMAX_BRUSHLESS:
                return motorControllerSMAX.get();
            default:
                return 0.0;
        }
    }

    @Override
    public void disable() {
        switch (type) {
            case TSRX:
                motorControllerTSRX.disable();
            case SMAX_BRUSHED:
                motorControllerSMAX.disable();
            case SMAX_BRUSHLESS:
                motorControllerSMAX.disable();
        }
    }

    @Override
    public void stopMotor() {
        switch (type) {
            case TSRX:
                motorControllerTSRX.stopMotor();
            case SMAX_BRUSHED:
                motorControllerSMAX.stopMotor();
            case SMAX_BRUSHLESS:
                motorControllerSMAX.stopMotor();
        }
    }

    @Override
    public void setInverted(boolean inverted) {
        switch (type) {
            case TSRX:
                motorControllerTSRX.setInverted(inverted);
            case SMAX_BRUSHED:
                motorControllerSMAX.setInverted(inverted);
            case SMAX_BRUSHLESS:
                motorControllerSMAX.setInverted(inverted);
        }
    }

    @Override
    public boolean getInverted() {
        switch (type) {
            case TSRX:
                return motorControllerTSRX.getInverted();
            case SMAX_BRUSHED:
                return motorControllerSMAX.getInverted();
            case SMAX_BRUSHLESS:
                return motorControllerSMAX.getInverted();
            default:
                return false;
        }
    }

    public void feed() {
        switch (type) {
            case TSRX:
                motorControllerTSRX.feed();
            case SMAX_BRUSHED:
                motorControllerSMAX.set(get());
            case SMAX_BRUSHLESS:
                motorControllerSMAX.set(get());
        }
    }
}
