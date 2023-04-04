// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
        public static final String PHOTON_HOST = "OV5647";  
        public static final double MAXIMUM_2NDROW_APPROACH_ANGLE_DEGREES = 40.0;
        public static final double MAXIMUM_3RDROW_APPROACH_ANGLE_DEGREES = 8.0;
        
        public interface CAMERA_POSITION{
        	public static final double UP_METERS = 0.15875;
        	public static final double LEFT_OF_CENTER_METERS = 0.215;
        	public static final double FORWARD_OF_CENTER_METERS = 0.38;
            public static final double CAMERA_YAW_RADIANS = 0;
            public static final double CAMERA_PITCH_RADIANS = 0.278986;
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
    		public static final double MIDDLE_HIGH_CONE_DEPLOY_THRESHOLD = 82.0;
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
    		  public static double SAFE_ANGLE = 30.0;  
    		  public static double SCORE_LOW_DEGREES = 43.0;
    		  public static double SCORE_MIDDLE_DEGREES = 79.0;
    		  public static double LOAD_STATION_DEGREES = 74.0;
    		  public static double SCORE_HIGH_DEGREES = 94.0;
    		  public static double SCORE_HIGH_RELEASE_DEGREES = 83.0;
    		  public static double SCORE_MID_RELEASE_DEGREES = 73;
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
        public static final double CUBE_TOLERANCE_METERS = Units.inchesToMeters(4.0);
        public static final double CONE_TOLERANCE_METERS = Units.inchesToMeters(1.0);
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
    }
    public interface BALANCE_PARAMETERS {
        public static final double CHARGESTATION_APPROACH_SPEED = 0.33;
        public static final double TIP_PITCH_THRESHOLD = 7.0; // probably should be closer to 15.0
        public static final double DOCK_DISTANCE = 0.5*1.22 + 0.25*DRIVE.WHEELBASE_INCHES/39.37;  // charge station platform depth = 4ft (1.22m)
        public static final double DOCK_INITIAL_SPEED = 0.33;
        public static final double DOCK_FINAL_SPEED = 0.25;
        public static final double BALANCE_SPEED = 0.20;
        public static final double BALANCE_PITCH_THRESHOLD = 5.0;  // probably closer to 12.0
        public static final int    BALANCE_STABLE_COUNT = 100;
        public static final double DEPLOY_BRAKE_AUTO_TIME_REMAINING = 0.25;
    }
    public interface DRIVER_STICK {
        public static final int TURN_TOGGLE = 1;
        public static final int AUTO_ALIGN_DRIVE = 2;
        public static final int NUDGE_YAW_LEFT = 3;
        public static final int NUDGE_YAW_RIGHT = 4;
        public static final int SNAP_YAW = 5;
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
    }
    public interface DIGITAL_IO {

    }
    public interface PNEUMATICS {
        public static final int LEFT_GRIPPER_OPEN = 3;
        public static final int LEFT_GRIPPER_CLOSE = 2;
        public static final int RIGHT_GRIPPER_OPEN = 4;
        public static final int RIGHT_GRIPPER_CLOSE = 5;  
        public static final int BRAKE_SOLENOID = 7;    

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
