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
    }
    public interface CAN {
        public static final int FRONT_LEFT_MOTOR = 1;
        public static final int REAR_LEFT_MOTOR = 2;
        public static final int FRONT_RIGHT_MOTOR = 3;
        public static final int REAR_RIGHT_MOTOR = 4;
    }
    public interface DIGITAL_IO {
    }
}
