// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class RobotConstants {
	public static final class DrivetrainConstants {
		// Driving Parameters - Note that these are not the maximum capable speeds of
		// the robot, rather the allowed maximum speeds
		public static final double MAX_SPEED_METERS_PER_SECOND = 4.0; //4.42; //4.8;
		public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND = 2 * Math.PI; // radians per second

		public static final double DIRECTION_SLEW_RATE = 1.2; // radians per second
		public static final double MAGNITUDE_SLEW_RATE = 1.8; // 2.0; //1.8; // percent per second (1 = 100%)
		public static final double ROTATIONAL_SLEW_RATE = 2.0; // 20.0; //2.0; // percent per second (1 = 100%)

		// Chassis configuration
		public static final double TRACK_WIDTH_METERS = Units.inchesToMeters(21.75);
		
		// Distance between centers of right and left wheels on robot
		public static final double WHEEL_BASE_METERS = Units.inchesToMeters(21.75);
		
		// Distance between front and back wheels on robot
		public static final SwerveDriveKinematics DRIVE_KINEMATICS = new SwerveDriveKinematics(
				new Translation2d(WHEEL_BASE_METERS / 2, TRACK_WIDTH_METERS / 2),
				new Translation2d(WHEEL_BASE_METERS / 2, -TRACK_WIDTH_METERS / 2),
				new Translation2d(-WHEEL_BASE_METERS / 2, TRACK_WIDTH_METERS / 2),
				new Translation2d(-WHEEL_BASE_METERS / 2, -TRACK_WIDTH_METERS / 2));

		public static final boolean kGyroReversed = false;
	}

	public static final class SwerveModuleConstants {
		// The MAXSwerve module can be configured with one of three pinion gears: 12T, 13T, or 14T.
		// This changes the drive speed of the module (a pinion gear with more teeth will result in a
		// robot that drives faster).
		public static final int kDrivingMotorPinionTeeth = 14;

		// Invert the turning encoder, since the output shaft rotates in the opposite direction of
		// the steering motor in the MAXSwerve Module.
		public static final boolean kTurningEncoderInverted = true;

		// Calculations required for driving motor conversion factors and feed forward
		public static final double DRIVING_MOTOR_FREE_SPEED_RPS = NeoMotorConstants.FREE_SPEED_RPM / 60;
		public static final double WHEEL_DIAMETER_METERS = 0.1016;
		public static final double WHEEL_CIRCUMFERENCE_METERS = WHEEL_DIAMETER_METERS * Math.PI;
		// 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
		public static final double DRIVING_MOTOR_REDUCTION = (45.0 * 17 * 50) / (kDrivingMotorPinionTeeth * 15 * 27);
		public static final double DRIVE_WHEEL_FREE_SPEED_RPS = (DRIVING_MOTOR_FREE_SPEED_RPS * WHEEL_CIRCUMFERENCE_METERS)	/ DRIVING_MOTOR_REDUCTION;

		public static final double DRIVING_ENCODER_POSITION_FACTOR_METERS_PER_ROTATION = (WHEEL_DIAMETER_METERS * Math.PI) / DRIVING_MOTOR_REDUCTION; // meters, per rotation
		public static final double DRIVING_ENCODER_VELOCITY_FACTOR_METERS_PER_SECOND_PER_RPM = ((WHEEL_DIAMETER_METERS * Math.PI) / DRIVING_MOTOR_REDUCTION) / 60.0; // meters per second, per RPM

		public static final double TURNING_MOTOR_REDUCTION = 150.0 / 7.0; // ratio between internal relative encoder and Through Bore (or Thrifty in our case) absolute encoder - 150.0 / 7.0

		public static final double TURNING_ENCODER_POSITION_FACTOR_RADIANS_PER_ROTATION = (2 * Math.PI) / TURNING_MOTOR_REDUCTION ; // radians, per rotation
		public static final double TURNING_ENCODER_VELOCITY_FACTOR_RADIANS_PER_SECOND_PER_RPM = (2 * Math.PI) / TURNING_MOTOR_REDUCTION / 60.0; // radians per second, per RPM

		public static final double TURNING_ENCODER_POSITION_PID_MIN_INPUT_RADIANS = 0; // radians
		public static final double TURNING_ENCODER_POSITION_PID_MAX_INPUT_RADIANS = (2 * Math.PI); // radians

		public static final double DRIVING_P = 0.04;
		public static final double DRIVING_I = 0;
		public static final double DRIVING_D = 0;
		public static final double DRIVING_FF = 1 / DRIVE_WHEEL_FREE_SPEED_RPS;
		public static final double DRIVING_MIN_OUTPUT_NORMALIZED = -1;
		public static final double DRIVING_MAX_OUTPUT_NORMALIZED = 1;

		public static final double TURNING_P = 1.0; //1.0; // 1.0 might be a bit too much - reduce a bit if needed
		public static final double TURNING_I = 0;
		public static final double TURNING_D = 0;
		public static final double TURNING_FF = 0;
		public static final double TURNING_MIN_OUTPUT_NORMALIZED = -1;
		public static final double TURNING_MAX_OUTPUT_NORMALIZED = 1;

		public static final IdleMode DRIVING_MOTOR_IDLE_MODE = IdleMode.kBrake;
		public static final IdleMode TURNING_MOTOR_IDLE_MODE = IdleMode.kBrake;

		public static final int DRIVING_MOTOR_CURRENT_LIMIT_AMPS = 40; //50; // amps
		public static final int TURNING_MOTOR_CURRENT_LIMIT_AMPS = 20; // amps
	}

	public static final class AutoConstants {
		public static final double MAX_SPEED_METERS_PER_SECOND = 3.0; //4.42; //3.0;
		public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = 3;
		public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND = Math.PI;
		public static final double MAX_ANGULAR_ACCELERATION_RADIANS_PER_SECOND_SQUARED = Math.PI;

		public static final double X_CONTROLLER_P = 1;
		public static final double Y_CONTROLLER_P = 1;
		public static final double THETA_CONTROLLER_P = 1;

		// Constraint for the motion profiled robot angle controller
	    public static final TrapezoidProfile.Constraints THETA_CONTROLLER_CONSTRAINTS = new TrapezoidProfile.Constraints(
			MAX_ANGULAR_SPEED_RADIANS_PER_SECOND, MAX_ANGULAR_ACCELERATION_RADIANS_PER_SECOND_SQUARED);
	}

	public static final class NeoMotorConstants {
		public static final double FREE_SPEED_RPM = 5676;
	}

    public interface PWM {
        public static final int PWM_0 = 0;
        public static final int PWM_1 = 1;
        public static final int LED_STRIP = 8;
    }
    public interface INDICATOR_VALUES{
    	public static final double POSITION_UNKNOWN = -1.0;
    	public static final double POSITION_NOT_SET = -1.1;
    }
    public interface VISION {
        //public static final double CAMERA_TO_BUMPER = 17.785; //don't need?
        
        public interface APRILTAGIDS {
            public static final int BLUE_LOADING = 4;
            public static final int BLUE_MIDDLE = 7;
            public static final int BLUE_RIGHT = 6;
            public static final int BLUE_LEFT = 8;
            public static final int RED_LOADING = 5;
            public static final int RED_MIDDLE = 2;
            public static final int RED_RIGHT = 1;
            public static final int RED_LEFT = 3; 
        }
        public static final String PHOTON_HOST = "Arducam_OV9281_USB_Camera";  
        public static final double MAXIMUM_2NDROW_APPROACH_ANGLE_DEGREES = 40.0;
        public static final double MAXIMUM_3RDROW_APPROACH_ANGLE_DEGREES = 8.0;
        
        public interface CAMERA_POSITION{
        	//public static final double UP_METERS = 0.17875;
        	//public static final double LEFT_OF_CENTER_METERS = 0.215;
        	//public static final double FORWARD_OF_CENTER_METERS = 0.38;
            //public static final double CAMERA_YAW_RADIANS = 0;
            //public static final double CAMERA_PITCH_RADIANS = 0.278986;
        	public static final double UP_METERS = 0.466;
        	public static final double LEFT_OF_CENTER_METERS = -0.0;
        	public static final double FORWARD_OF_CENTER_METERS = 0.05;
            public static final double CAMERA_YAW_RADIANS = 0;
            public static final double CAMERA_PITCH_RADIANS = 0.0;        	
        }
    }
    public interface ARM{
    	public interface SETTINGS{
    		public static boolean MOTOR_REVERSED = true;
    		public static final double MOVE_TOLERANCE_METERS = 0.012; 
    		public static final int CURRENT_LIMIT_AMPS = 8;
    		public static final int MAX_SPIKE_CURRENT = 12;
    		public static final double COUNTS_PER_METER = 0.004826; 
    		
    	} 
    	public interface HOMING{
    		public static final double HOMING_SPEED_PERCENT = 0.35;
    		public static final double HOMING_SPEED_VELOCITY = 5.0; //we think this is 'arm meters per minute'
    		public static final int HOMING_CURRENT_AMPS=7;
    	}
    	public interface TUNING{
    		public static final double P_GAIN = 16.0;
    		public static final double I_GAIN = 0.0;
    		public static final double D_GAIN = 0.0;
    	}
    	public interface POSITION_PRESETS{
    		  public static final double MIN_METERS = 0.005;
    		  public static final double CARRY_METERS = 0.1;
    		  public static final double SCORE_MIDDLE_METERS = 0.17;
    		  public static final double SCORE_HIGH_METERS = 0.49;
    		  public static final double SAFE = 0.1;
              public static final double LOAD = 0.005;
    		  public static final double MAX_METERS = 0.50; //0.52 max extension
              //public static final double MIN_ARM_LENGTH_M = 0.87155; 
              //public static final double MAX_ARM_LENGTH_M = 1.45;
    	}
        
        public static final double MIN_EXTENSION_METERS = 0.9906; //39 inches from center of robot to center of claw
        public static final double MAX_EXTENSION_METERS = 1.4478; //57 inches from center of robot to center of claw
        //public static final double ARM_MAX_REACH_METERS = 0.8128; //from center of robot base!!!
    }
    public interface ELBOW{
        public static final double MIN_ANGLE_DEGREES = 12.0; //78 degrees below horizontal (90)
        public static final double MAX_ANGLE_DEGREES = 107.0; //17 degrees above horizontal (90)
    	public interface SETTINGS{
    		public static boolean MOTOR_REVERSED = false;

    		public static final double MOVE_TOLERANCE_DEGREES= 1.0; 
    		public static final int CURRENT_LIMIT_AMPS=30;
    		public static final int MAX_SPIKE_CURRENT=50;
    		public static final double COUNTS_PER_DEGREE=1.95; 
    		public static final double ELBOW_SLOWDOWN_SPEED= 0.2;
    		public static final double MIDDLE_HIGH_CONE_DEPLOY_THRESHOLD = 83.0;
    	} 
    	public interface HOMING{
    		public static final double HOMING_SPEED_PERCENT = 0.2;
    		public static final double HOMING_SPEED_VELOCITY = 5.0; //we think this is 'elbow degrees per minute'
    		public static final int HOMING_CURRENT_AMPS=7;    		
    	}
    	public interface TUNING{

    		public static final double P_GAIN=0.08;
    		public static final double FF_GAIN_GOING_UP=0.00;
    		public static final double FF_GAIN_GOING_DOWN=0.0;
    		public static final double I_GAIN=0.000;
    		public static final double D_GAIN=0.0;

    	}
    	public interface POSITION_PRESETS{
    		  public static final double MIN_POSITION_DEGREES = 18.0;    		
    		  public static double CARRY_DEGREES = 20.0;
    		  public static double SAFE_ANGLE = 35.0;  
    		  public static double SCORE_LOW_DEGREES = 43.0;
    		  public static double SCORE_MIDDLE_DEGREES = 79.0;
    		  public static double LOAD_STATION_DEGREES = 75.0;
    		  public static double SCORE_HIGH_DEGREES = 94.0;
    		  public static double SCORE_HIGH_RELEASE_DEGREES = 83.0;
    		  public static double SCORE_MID_RELEASE_DEGREES = 71;
     		  public static final double MAX_POSITION_DEGREES = 104.8; 
    	}
    }
    
    public interface GRIPPER{
    	public static final boolean INIT_CLAW_STATE = false;
    }
    public interface ALIGNMENT{
    	
        public static final double ANGLE_TOLERANCE_DEGREES = 2.0;
        public static final double DISTANCE_TOLERANCE_METERS = 0.0508;
        //public static final double ALIGN_TOLERANCE_METERS = 0.02;
        public static final double ALIGN_CLOSE_WINDOW_METERS = Units.inchesToMeters(5.0); //gets added outside the tolerance window
        //public static final double ALIGN_KINDA_CLOSE_METERS = 0.15;
        
        //tolerances are vs nominal, so the 'windows' is 2x this value
        public static final double CUBE_TOLERANCE_METERS = Units.inchesToMeters(5.0);
        public static final double CONE_TOLERANCE_METERS = Units.inchesToMeters(2.0);
        public static final double LOADING_TOLERANCE_METERS = Units.inchesToMeters(8.0);
        public static final double TAG_DISTANCE_TO_REDUCE_SPEED = Units.inchesToMeters(24);
    }
    public interface JOYSTICKS {
        public static final int DRIVER_JOYSTICK = 0;
        public static final int OPERATOR_PANEL = 1;
        public static final int OPERATOR_JOYSTICK = 2;
    }
    public interface DRIVE {
    	public static final int CURRENT_LIMIT_AMPS = 30;
        public static final double GEAR_BOX_RATIO = 9.92;
        public static final double METERS_PER_GEARBOX_REVOLTION = 3.14*6.0/39.37;
    	public static final boolean DEFAULT_FIELD_ABSOLUTE = false;
        public static final double PRECISION_DRIVE_FACTOR = 0.4;
        public static final double ROTATION_DAMPING_FACTOR = 0.5;
        public static final double SPEED_LIMIT_WITH_ARM_OUT = 0.7;
        public static final double YAW_NUDGE_DEGREES = 3.5;
        public static final double WHEELBASE_INCHES = 24.5;
        public interface PID {
        	public static final double kP = 0.01;
        	public static final double kI = 0.0;
        	public static final double kD = 0.0;
        	public static final double kMAX_SPEED = 100.0;
        	public static final double kMAX_ACCEL = 50.0;
        	public static final double kTURN_TOLLERENCE = 2.0;
        	public static final double kTURN_TOLLERENCE_RATE = 0.0;
        }
    }
    public interface BALANCE_PARAMETERS {
        public static final double CHARGESTATION_APPROACH_SPEED = 0.30;
        public static final double TIP_PITCH_THRESHOLD = 15.0;
        public static final double DOCK_DISTANCE = 0.5*1.22 + 0.25*DRIVE.WHEELBASE_INCHES/39.37;  // charge station platform depth = 4ft (1.22m)
        public static final double DOCK_INITIAL_SPEED = 0.30;
        public static final double DOCK_FINAL_SPEED = 0.22;
        public static final double BALANCE_SPEED = 0.12;
        public static final double BALANCE_PITCH_THRESHOLD = 12.0;
        public static final int    BALANCE_STABLE_COUNT = 100;
        public static final double DEPLOY_BRAKE_AUTO_TIME_REMAINING = 0.25;
    }
    public interface DRIVER_STICK {
        public static final int TURN_TOGGLE = 1;
        public static final int AUTO_ALIGN_DRIVE = 2;
        public static final int NUDGE_YAW_LEFT = 3;
        public static final int NUDGE_YAW_RIGHT = 4;
        public static final int SNAP_YAW = 5;
        public static final int FROG_GRAB = 6;
        public static final int AUTO_BALANCE_FORWARD = 7;
        public static final int DEPLOY_BRAKE = 8;
        public static final int ZERO_GYRO_ANGLE = 9;
        public static final int BRAKE_COAST = 10;
        public static final int AUTO_YAW_TOGGLE = 11;
        public static final int TOGGLE_FIELD_ABSOLUTE = 12;
        public interface POV {
            public static final int FORWARD = 0;
            public static final int RIGHT = 90;
            public static final int BACKWARD = 180;
            public static final int LEFT = 270;
        }
    }
    public interface OPERATOR_STICK {
        public static final int GRIPPER = 1;
        public static final int ARM_100 = 7;
        public static final int HOME_TELESCOPE = 11;
        public static final int HOME_ELBOW = 12;
        public interface POV {
            public static final int UP = 90;
            public static final int IN = 180;
            public static final int DOWN = 270;
            public static final int OUT = 0;
        }
    }
    public interface OPERATOR_PANEL {
        public static final int GRIPPER = 1;
        public static final int PIVOT_UP = 2;
        public static final int PIVOT_DOWN = 3;
        public static final int AUTO = 4;
        public static final int TELESCOPE_OUT = 5;
        public static final int TELESCOPE_IN = 6;
        public static final int ARM_OFF = 10;
        public static final int ARM_LOAD = 8;
        public static final int ARM_HIGH = 9;
        public static final int ARM_MIDDLE = 11;
    }
    public interface CAN {
    	public static final int FRONT_LEFT_MOTOR = 1;
    	public static final int FRONT_RIGHT_MOTOR = 2;
    	public static final int REAR_LEFT_MOTOR = 3;
    	public static final int REAR_RIGHT_MOTOR = 4;    	
        public static final int ELBOW_MOTOR_ID = 5;
        public static final int TELESCOPE_MOTOR_ID = 6;

        // SPARK MAX CAN IDs
		public static final int FRONT_LEFT_DRIVING = 10;
		public static final int REAR_LEFT_DRIVING = 6;
		public static final int FRONT_RIGHT_DRIVING = 21;
		public static final int REAR_RIGHT_DRIVING = 4;

		public static final int FRONT_LEFT_TURNING = 12;
		public static final int REAR_LEFT_TURNING = 5;
		public static final int FRONT_RIGHT_TURNING = 22;
		public static final int REAR_RIGHT_TURNING = 3;
    }

    public interface DIGITAL_IO {

    }

    public static class ANALOG_IO {
        // 2023 Off-season
        // SPARK MAX Absolute encoders
        public static final int FRONT_LEFT_TURNING_ABSOLUTE_ENCODER = 1;
        public static final int REAR_LEFT_TURNING_ABSOLUTE_ENCODER = 2;
        public static final int FRONT_RIGHT_TURNING_ABSOLUTE_ENCODER = 0;
        public static final int REAR_RIGHT_TURNING_ABSOLUTE_ENCODER = 3;
    }

    public interface PNEUMATICS {
        public static final int RIGHT_GRIPPER_OPEN = 4;
        public static final int RIGHT_GRIPPER_CLOSE = 5;  
        public static final int BRAKE_SOLENOID_RETRACTED = 6;
        public static final int BRAKE_SOLENOID_DEPLOYED = 7;
    }    
    public interface SHUFFLEBOARD {
    	public interface TABS{
        	public static final String PREMATCH = "PreMatch";
        	public static final String MATCH = "Match";
        	public static final String DEBUG = "Testing";    		
    	}

    }

    public interface MAST{
        public static final double MAST_DISTANCE_OVER_MID_NODE = 19.25;
    }
}
