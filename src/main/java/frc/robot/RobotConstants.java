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
    public enum TEAM{
        RED,
        BLUE
    }
    public interface VISION {
        public static final double CAMERA_TO_BUMPER = 17.785;
        
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
        	public static final double UP_INCHES = 6.25;
        	public static final double LEFT_OF_CENTER_INCHES = 10;
        	public static final double FORWARD_OF_CENTER_INCHES = 14.25;
            public static final double CAMERA_YAW_DEGREES = 11.0;
            public static final double CAMERA_PITCH_DEGREES = 11.0;
            
        }
    }
    public interface ARM{
        public static final double MIN_ANGLE_DEGREES = 0.0;
        public static final double MAX_ANGLE_DEGREES = 90.0;
        public static final boolean INIT_CLAW_STATE = false;
        public static final double MIN_EXTENSION_INCHES = 0.0;
        public static final double MAX_EXTENSION_INCHES = 200.0;
        public static final double ARM_MAX_REACH_INCHES = 32; //from center of robot base!!!
        public static final int ELBOW_MOTOR_ID = 0;
        public static final int TELESCOPE_MOTOR_ID = 0;
    }
    public interface ALIGNMENT{
        public static final double ANGLE_TOLERANCE_DEGREES=2.0;
        public static final double DISTANCE_TOLERANCE_INCHES=2.0;
    }
    public interface JOYSTICKS {
        public static final int DRIVER_JOYSTICK = 0;
    }
    public interface DRIVER_STICK {
        public static final int TURN_TOGGLE = 1;
        public static final int TOGGLE_FIELD_ABSOLUTE = 12;
        public static final int ZERO_GYRO_ANGLE = 9;
        public static final int ZERO_ROBOT_ANGLE = 10;
        public static final int AUTO_YAW_TOGGLE = 11;
        public static final int NUDGE_YAW_LEFT = 3;
        public static final int NUDGE_YAW_RIGHT = 4;
        public static final int AUTO_ALIGN_DRIVE = 2;
        public interface POV {
            public static final int FORWARD = 0;
            public static final int RIGHT = 90;
            public static final int BACKWARD = 180;
            public static final int LEFT = 270;
        }
    }
    public interface CAN {
        public static final int FRONT_LEFT_MOTOR = 5;
        public static final int REAR_LEFT_MOTOR = 8;
        public static final int FRONT_RIGHT_MOTOR = 7;
        public static final int REAR_RIGHT_MOTOR = 6;
    }
    public interface DIGITAL_IO {

    }
    public interface SHUFFLEBOARD {
    	public interface TABS{
        	public static final String PREMATCH="PreMatch";
        	public static final String MATCH="Match";
        	public static final String DEBUG="Debug";    		
    	}

    	public static boolean DEFAULT_YAW_LOCK = false;
    	public static boolean DEFAULT_FIELD_ABSOLUTE = true;
    }
}
