package frc.robot.util;

public final class EntechUtils {

    public static double CapDoubleValue(double value, double limit1, double limit2) {
        return Math.max(Math.min(limit1, limit2), Math.min(Math.max(limit1, limit2), value));
    }
    
    public static double EnsureMinOutput(double value, double minMagnitude) {
        if (minMagnitude < 0.0) minMagnitude = Math.abs(minMagnitude);
        if ((value < 0.0) && (Math.abs(value) < minMagnitude)) return -minMagnitude;
        if ((value >= 0.0) && (value < minMagnitude)) return minMagnitude;
        return value;
    }

    public static double ApplyDeadband(double value, double min) {
        if (Math.abs(value) < Math.abs(min)) return 0.0;
        return value;
    }
}
