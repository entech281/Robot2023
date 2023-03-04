// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
    }
    public interface INDICATOR_VALUES{
    	public static final double POSITION_UNKNOWN=-1.0;
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
    		public static final double MOVE_TOLERANCE_METERS=0.005; 
    		public static final int CURRENT_LIMIT_AMPS=8;
    		public static final int MAX_SPIKE_CURRENT=20;
    		public static final double COUNTS_PER_METER=0.004826; //TODO: check. 42 counts/ rev. 48:1 gearbox. approx 1.5" pulley
    	} 
    	public interface HOMING{
    		public static final double HOMING_SPEED_PERCENT=0.1;
    		public static final double HOME_POSITION_BACKOFF_METERS=0.1;
    		public static final double HOME_POSITION_METERS=0.1;
    	}
    	public interface TUNING{
    		public static final double P_GAIN=30.0;
    		public static final double I_GAIN=0.0;
    		
    		public static final double D_GAIN=0.0;
    	}
    	public interface POSITION_PRESETS{
    		  public static double MIN_METERS= 0.0;
    		  public static double CARRY_METERS = 0.1;
    		  public static double SCORE_MIDDLE_METERS = 0.3;
    		  public static double SCORE_HIGH_METERS = 0.4;
    		  public static double MAX_METERS = 0.5;
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
    		public static final double MOVE_TOLERANCE_DEGREES=0.5; 
    		public static final int CURRENT_LIMIT_AMPS=4;
    		public static final int MAX_SPIKE_CURRENT=10;
    		public static final double COUNTS_PER_DEGREE=2.1383; 
    	} 
    	public interface HOMING{
    		public static final double HOMING_SPEED_PERCENT=0.1;
    		public static final double HOME_POSITION_DEGREES=3.0;
    		public static final double MAX_POSITION_DEGREES=100.0;
    		public static final double MIN_POSITION_DEGREES=3.0;
    	}
    	public interface TUNING{
    		public static final double P_GAIN=0.022;
    		public static final double I_GAIN=0.0001;
    		public static final double D_GAIN=0.0;
    	}
    	public interface POSITION_PRESETS{
    		  public static int CARRY_DEGREES = 20;
    		  public static double SCORE_MIDDLE_DEGREES = 60;
    		  public static double SCORE_HIGH_DEGREES = 90;
    		  public static double SAFE_ANGLE=30.0;
    	}    	
    }
    public interface GRIPPER{
    	public static final boolean INIT_CLAW_STATE = false;
    }
    public interface ALIGNMENT{
        public static final double ANGLE_TOLERANCE_DEGREES=2.0;
        public static final double DISTANCE_TOLERANCE_METERS=0.0508;
    }
    public interface JOYSTICKS {
        public static final int DRIVER_JOYSTICK = 0;
    }
    public interface DRIVER_STICK {
        public static final int TURN_TOGGLE = 1;
        public static final int AUTO_ALIGN_DRIVE = 2;
        public static final int NUDGE_YAW_LEFT = 3;
        public static final int NUDGE_YAW_RIGHT = 4;
        public static final int ZERO_GYRO_ANGLE = 9;
        public static final int ZERO_ROBOT_ANGLE = 10;
        public static final int AUTO_YAW_TOGGLE = 11;
        public static final int TOGGLE_FIELD_ABSOLUTE = 12;
        public interface POV {
            public static final int FORWARD = 0;
            public static final int RIGHT = 90;
            public static final int BACKWARD = 180;
            public static final int LEFT = 270;
        }
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
        public static final int GRIPPER_OPEN = 1;
        public static final int GRIPPER_CLOSE = 2;
    }    
    public interface SHUFFLEBOARD {
    	public interface TABS{
        	public static final String PREMATCH="PreMatch";
        	public static final String MATCH="Match";
        	public static final String DEBUG="Testing";    		
    	}

    	public static boolean DEFAULT_YAW_LOCK = false;
    	public static boolean DEFAULT_FIELD_ABSOLUTE = true;
    }
}
