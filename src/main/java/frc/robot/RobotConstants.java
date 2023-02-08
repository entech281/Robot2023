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
    public interface VISION {
        public static final String PHOTON_HOST = "photonvision";
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
}
