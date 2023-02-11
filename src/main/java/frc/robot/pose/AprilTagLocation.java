package frc.robot.pose;

import frc.robot.RobotConstants;
import java.util.NoSuchElementException;

public class AprilTagLocation {
  
  public static enum AprilTagIDLocation {
    BLUE_LOADING,
    BLUE_LEFT,
    BLUE_RIGHT,
    BLUE_MIDDLE,
    RED_LOADING,
    RED_LEFT,
    RED_RIGHT,
    RED_MIDDLE,
    NONE
  }

  private int aprilTagID;
  private AprilTagIDLocation location;
  private double absoluteTargetXIn;
  private double absoluteTargetYIn;
  private double absoluteTargetAngleDegrees;

  public static double TAG_X_BLUE_TARGET = 40.45; //In
  public static double TAG_X_RED_LOADING = 14.25; //In
  public static double TAG_X_RED_TARGET = 610.77; //In
  public static double TAG_X_BLUE_LOADING = 636.96; //In

  public static double TAG_Y_MIDDLE = 108.19; //In
  public static double TAG_Y_LEFT = 174.19; //In
  public static double TAG_Y_RIGHT = 42.19; //In
  public static double TAG_Y_LOADING = 265.74; //In

  public static double BLUE_ANGLE = 180; //In
  public static double RED_ANGLE = 0; //In

  public static AprilTagLocation BLUE_LOADING = new AprilTagLocation(AprilTagIDLocation.BLUE_LOADING, 4, TAG_X_BLUE_LOADING, TAG_Y_LOADING, BLUE_ANGLE);
  public static AprilTagLocation BLUE_LEFT = new AprilTagLocation(AprilTagIDLocation.BLUE_LEFT, 8, TAG_X_BLUE_TARGET, TAG_Y_LEFT, BLUE_ANGLE);
  public static AprilTagLocation BLUE_RIGHT = new AprilTagLocation(AprilTagIDLocation.BLUE_RIGHT, 6, TAG_X_BLUE_TARGET, TAG_Y_RIGHT, BLUE_ANGLE);
  public static AprilTagLocation BLUE_MIDDLE = new AprilTagLocation(AprilTagIDLocation.BLUE_MIDDLE, 7, TAG_X_BLUE_TARGET, TAG_Y_MIDDLE, BLUE_ANGLE);
  public static AprilTagLocation RED_LOADING = new AprilTagLocation(AprilTagIDLocation.RED_LOADING, 5, TAG_X_RED_LOADING, TAG_Y_LOADING, RED_ANGLE);
  public static AprilTagLocation RED_LEFT = new AprilTagLocation(AprilTagIDLocation.RED_LEFT, 3, TAG_X_RED_TARGET, TAG_Y_LEFT, RED_ANGLE);
  public static AprilTagLocation RED_RIGHT = new AprilTagLocation(AprilTagIDLocation.RED_RIGHT, 1, TAG_X_RED_TARGET, TAG_Y_RIGHT, RED_ANGLE);
  public static AprilTagLocation RED_MIDDLE = new AprilTagLocation(AprilTagIDLocation.RED_MIDDLE, 2, TAG_X_RED_TARGET, TAG_Y_MIDDLE, RED_ANGLE);
  
  protected AprilTagLocation(AprilTagIDLocation location, int aprilTagID,double absoluteTargetXIn, double absoluteTargetYIn, double absoluteTargetAngleDegrees) {
    this.location = location;
    this.aprilTagID = aprilTagID;
    this.absoluteTargetXIn = absoluteTargetXIn;
    this.absoluteTargetYIn = absoluteTargetYIn;
    this.absoluteTargetAngleDegrees = absoluteTargetAngleDegrees;
  }

  public double getXIn() {
    return absoluteTargetXIn;
  }

  public double getYIn() {
    return absoluteTargetYIn;
  }

  public double getAngleDegrees() {
    return absoluteTargetAngleDegrees;
  }

  public static AprilTagLocation findFromTag(int tagId ){
    switch(tagId){
        case RobotConstants.VISION.APRILTAGIDS.RED_RIGHT: return RED_RIGHT;
        case RobotConstants.VISION.APRILTAGIDS.RED_MIDDLE: return RED_MIDDLE;
        case RobotConstants.VISION.APRILTAGIDS.RED_LEFT: return RED_LEFT;
        case RobotConstants.VISION.APRILTAGIDS.RED_LOADING: return RED_LOADING;
        case RobotConstants.VISION.APRILTAGIDS.BLUE_LOADING: return BLUE_LOADING;
        case RobotConstants.VISION.APRILTAGIDS.BLUE_RIGHT: return BLUE_RIGHT;
        case RobotConstants.VISION.APRILTAGIDS.BLUE_MIDDLE: return BLUE_MIDDLE;
        case RobotConstants.VISION.APRILTAGIDS.BLUE_LEFT: return BLUE_LEFT;
    }
    throw new NoSuchElementException("'" + tagId + "' is not a valid april tag id");
}
}
