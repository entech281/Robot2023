package frc.robot.pose;

import java.util.List;

import frc.robot.RobotConstants;
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
  private double absoluteTargetZIn;

  public static double TargetXBlueTag = 40.45; //In
  public static double TargetXRedLoading = 14.25; //In
  public static double TargetXRedTag = 610.77; //In
  public static double TargetXBlueLoading = 636.96; //In

  public static double TargetYMiddleTag = 42.19; //In
  public static double TargetYLeftTag = 108.19; //In
  public static double TargetYRightTag = 174.19; //In
  public static double TargetYLoading = 265.74; //In

  public static double TargetZTag = 18.22; //In
  public static double TargetZLoading = 27.38; //In  

  public static AprilTagLocation BLUE_LOADING = new AprilTagLocation(AprilTagIDLocation.BLUE_LOADING, 4, TargetXBlueLoading, TargetYLoading, TargetZLoading);
  public static AprilTagLocation BLUE_LEFT = new AprilTagLocation(AprilTagIDLocation.BLUE_LEFT, 8, TargetXBlueTag, TargetYLeftTag, TargetZTag);
  public static AprilTagLocation BLUE_RIGHT = new AprilTagLocation(AprilTagIDLocation.BLUE_RIGHT, 6, TargetXBlueTag, TargetYRightTag, TargetZTag);
  public static AprilTagLocation BLUE_MIDDLE = new AprilTagLocation(AprilTagIDLocation.BLUE_MIDDLE, 7, TargetXBlueTag, TargetYLeftTag, TargetZTag);
  public static AprilTagLocation RED_LOADING = new AprilTagLocation(AprilTagIDLocation.RED_LOADING, 5, TargetXRedLoading, TargetYLoading, TargetZLoading);
  public static AprilTagLocation RED_LEFT = new AprilTagLocation(AprilTagIDLocation.RED_LEFT, 3, TargetXRedTag, TargetYLeftTag, TargetZTag);
  public static AprilTagLocation RED_RIGHT = new AprilTagLocation(AprilTagIDLocation.RED_RIGHT, 1, TargetXRedTag, TargetYRightTag, TargetZTag);
  public static AprilTagLocation RED_MIDDLE = new AprilTagLocation(AprilTagIDLocation.RED_MIDDLE, 2, TargetXRedTag, TargetYMiddleTag, TargetZTag);
  
  protected AprilTagLocation(AprilTagIDLocation location, int aprilTagID,double absoluteTargetXIn, double absoluteTargetYIn, double absoluteTargetZIn) {
    this.location = location;
    this.aprilTagID = aprilTagID;
    this.absoluteTargetXIn = absoluteTargetXIn;
    this.absoluteTargetYIn = absoluteTargetYIn;
    this.absoluteTargetZIn = absoluteTargetZIn;
  }

  public double getX() {
    return absoluteTargetXIn;
  }

  public double getY() {
    return absoluteTargetYIn;
  }

  public double getZ() {
    return absoluteTargetZIn;
  }  
}