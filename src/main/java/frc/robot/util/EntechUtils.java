package frc.robot.util;

public final class EntechUtils {

    /**
     * return value that is in the range between limit1 and limit2 capping the value if original value is outside the range
     *   limit1 and limit2 can be specified in either order
     * @param value value to be capped
     * @param limit1 limit value 1
     * @param limit2 limit value 2
     * @return
     */
    public static double capDoubleValue(double value, double limit1, double limit2) {
        return Math.max(Math.min(limit1, limit2), Math.min(Math.max(limit1, limit2), value));
    }
    
    /**
     * Scale up a value if its magnitude is below minMagnitude
     * @param value value to be checked
     * @param minMagnitude minimum value
     * @return value value is greater minMagnitude.  Sign is preserved from the input value.
     */
    public static double ensureMinOutput(double value, double minMagnitude) {
        if (minMagnitude < 0.0) minMagnitude = Math.abs(minMagnitude);
        if ((value < 0.0) && (Math.abs(value) < minMagnitude)) return -minMagnitude;
        if ((value >= 0.0) && (value < minMagnitude)) return minMagnitude;
        return value;
    }

    public static double nearestPerpendicularYawAngle(double inputAngle ) {
        if (Math.abs(inputAngle) < 90.0) {
            return 0.0;
        } else {
            return 180.0;
        }    	
    }
    /**
     * Take an angle and return its value between -180 and +180 degrees
     * @param angle in degrees
     * @return angle in degrees between -180 and +180
     */
    public static double normalizeAngle(double angle) {
        while (angle > 180.0) {
            angle -= 360.0;
        }
        while (angle < -180.0) {
            angle += 360.0;
        }
        return angle;
    }

}
