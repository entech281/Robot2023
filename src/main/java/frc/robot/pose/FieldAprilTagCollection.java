package frc.robot.pose;

import frc.robot.RobotConstants;
import java.util.NoSuchElementException;

/**
 *
 * @author dcowden
 */
public class FieldAprilTagCollection {
    
    public interface AprilTagIds {
        public static int RED_RIGHT = 1;
        public static int RED_MIDDLE =2;
        public static int RED_LEFT = 3;
        public static int RED_LOADING=4;
        public static int BLUE_LOADING=5;
        public static int BLUE_RIGHT=6;
        public static int BLUE_MIDDLE=7;
        public static int BLUE_LEFT=8;
    }

    public static final FieldAprilTag RED_RIGHT = new FieldAprilTag(0.0, 0.0, 0.0, AprilTagIds.RED_RIGHT,  RobotConstants.TEAM.RED);
    public static final FieldAprilTag RED_MIDDLE = new FieldAprilTag(0.0, 0.0, 0.0,AprilTagIds.RED_MIDDLE,  RobotConstants.TEAM.RED);
    public static final FieldAprilTag RED_LEFT = new FieldAprilTag(0.0, 0.0, 0.0,AprilTagIds.RED_LEFT,  RobotConstants.TEAM.RED);
    public static final FieldAprilTag RED_LOADING = new FieldAprilTag(0.0, 0.0, 0.0,AprilTagIds.RED_LOADING,  RobotConstants.TEAM.RED);
    public static final FieldAprilTag BLUE_LOADING = new FieldAprilTag(0.0, 0.0, 180.0,AprilTagIds.BLUE_LOADING,  RobotConstants.TEAM.BLUE);
    public static final FieldAprilTag BLUE_RIGHT = new FieldAprilTag(0.0, 0.0, 180.0,AprilTagIds.BLUE_RIGHT,  RobotConstants.TEAM.BLUE);
    public static final FieldAprilTag BLUE_MIDDLE = new FieldAprilTag(0.0, 0.0,180.0, AprilTagIds.BLUE_MIDDLE,  RobotConstants.TEAM.BLUE);
    public static final FieldAprilTag BLUE_LEFT = new FieldAprilTag(21.0, 14.5, 180.0, AprilTagIds.BLUE_LEFT,  RobotConstants.TEAM.BLUE);

    public static FieldAprilTag findFromTag(int tagId ){
        switch(tagId){
            case AprilTagIds.RED_RIGHT: return RED_RIGHT;
            case AprilTagIds.RED_MIDDLE: return RED_MIDDLE;
            case AprilTagIds.RED_LEFT: return RED_LEFT;
            case AprilTagIds.RED_LOADING: return RED_LOADING;
            case AprilTagIds.BLUE_LOADING: return BLUE_LOADING;
            case AprilTagIds.BLUE_RIGHT: return BLUE_RIGHT;
            case AprilTagIds.BLUE_MIDDLE: return BLUE_MIDDLE;
            case AprilTagIds.BLUE_LEFT: return BLUE_LEFT;
        }
        throw new NoSuchElementException("'" + tagId + "' is not a valid april tag id");
    }
    
}
