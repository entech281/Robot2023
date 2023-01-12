package frc.robot.subsystems;

import frc.robot.RobotConstants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DriveSubsystem extends EntechSubsystem {

    private CANSparkMax frontLeftSpark;
    private CANSparkMax frontRightSpark;
    private CANSparkMax rearLeftSpark;
    private CANSparkMax rearRightSpark;

    private RelativeEncoder frontLeftEncoder;
    private RelativeEncoder frontRightEncoder;
    private RelativeEncoder rearLeftEncoder;
    private RelativeEncoder rearRightEncoder;

    private MotorControllerGroup leftMotorController;
    private MotorControllerGroup rightMotorController;
    private DifferentialDrive robotDrive;

    @Override
    public void initialize() {
        frontLeftSpark = new CANSparkMax(RobotConstants.CAN.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        rearLeftSpark = new CANSparkMax(RobotConstants.CAN.REAR_LEFT_MOTOR, MotorType.kBrushless);
        leftMotorController = new MotorControllerGroup(frontLeftSpark, rearLeftSpark);

        frontRightSpark = new CANSparkMax(RobotConstants.CAN.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rearRightSpark = new CANSparkMax(RobotConstants.CAN.REAR_RIGHT_MOTOR, MotorType.kBrushless);
        rightMotorController = new MotorControllerGroup(frontRightSpark, rearRightSpark);
        frontLeftEncoder = frontLeftSpark.getEncoder();
        frontRightEncoder = frontRightSpark.getEncoder();
        rearLeftEncoder = rearLeftSpark.getEncoder();
        rearRightEncoder = rearRightSpark.getEncoder();
        robotDrive = new DifferentialDrive(leftMotorController, rightMotorController);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType(getName());
        builder.addDoubleProperty("FL Encoder Ticks", () -> { return frontLeftEncoder.getPosition(); }, null);
        builder.addDoubleProperty("FR Encoder Ticks", () -> { return frontRightEncoder.getPosition(); }, null);
        builder.addDoubleProperty("RL Encoder Ticks", () -> { return rearLeftEncoder.getPosition(); }, null);
        builder.addDoubleProperty("RR Encoder Ticks", () -> { return rearRightEncoder.getPosition(); }, null);
    }

    public void feedWatchDog(){
        robotDrive.feed();
    }

    @Override
    public void periodic() {
        feedWatchDog();
    }

    public void arcadeDrive(double forward, double rotation) {
        robotDrive.arcadeDrive(forward, rotation);
        feedWatchDog();
    }
}
