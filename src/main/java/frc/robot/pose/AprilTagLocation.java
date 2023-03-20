package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.RobotConstants;
import edu.wpi.first.math.util.Units;

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
  private double absoluteTargetXMeters;
  private double absoluteTargetYMeters;
  private double absoluteTargetAngleDegrees;

  
  public static double TAG_X_BLUE_TARGET = Units.inchesToMeters(40.45);
  public static double TAG_X_RED_LOADING = Units.inchesToMeters(14.25); 
  public static double TAG_X_RED_TARGET = Units.inchesToMeters(610.77); 
  public static double TAG_X_BLUE_LOADING = Units.inchesToMeters(636.96); 

  public static double TAG_Y_MIDDLE = Units.inchesToMeters(108.19); 
  public static double TAG_Y_LEFT = Units.inchesToMeters(174.19); 
  public static double TAG_Y_RIGHT = Units.inchesToMeters(42.19); 
  public static double TAG_Y_LOADING = Units.inchesToMeters(265.74);

  public static double BLUE_ANGLE = 0; //Degrees
  public static double RED_ANGLE = 180; //Degrees

  public static AprilTagLocation BLUE_LOADING = new AprilTagLocation(AprilTagIDLocation.BLUE_LOADING, 4, TAG_X_BLUE_LOADING, TAG_Y_LOADING, RED_ANGLE);
  public static AprilTagLocation BLUE_LEFT = new AprilTagLocation(AprilTagIDLocation.BLUE_LEFT, 8, TAG_X_BLUE_TARGET, TAG_Y_RIGHT, BLUE_ANGLE);
  public static AprilTagLocation BLUE_RIGHT = new AprilTagLocation(AprilTagIDLocation.BLUE_RIGHT, 6, TAG_X_BLUE_TARGET, TAG_Y_LEFT, BLUE_ANGLE);
  public static AprilTagLocation BLUE_MIDDLE = new AprilTagLocation(AprilTagIDLocation.BLUE_MIDDLE, 7, TAG_X_BLUE_TARGET, TAG_Y_MIDDLE, BLUE_ANGLE);
  public static AprilTagLocation RED_LOADING = new AprilTagLocation(AprilTagIDLocation.RED_LOADING, 5, TAG_X_RED_LOADING, TAG_Y_LOADING, BLUE_ANGLE);
  public static AprilTagLocation RED_LEFT = new AprilTagLocation(AprilTagIDLocation.RED_LEFT, 3, TAG_X_RED_TARGET, TAG_Y_LEFT, RED_ANGLE);
  public static AprilTagLocation RED_RIGHT = new AprilTagLocation(AprilTagIDLocation.RED_RIGHT, 1, TAG_X_RED_TARGET, TAG_Y_RIGHT, RED_ANGLE);
  public static AprilTagLocation RED_MIDDLE = new AprilTagLocation(AprilTagIDLocation.RED_MIDDLE, 2, TAG_X_RED_TARGET, TAG_Y_MIDDLE, RED_ANGLE);
  
  protected AprilTagLocation(AprilTagIDLocation location, int aprilTagID,double absoluteTargetXMeters, double absoluteTargetYMeters, double absoluteTargetAngleDegrees) {
    this.location = location;
    this.aprilTagID = aprilTagID;
    this.absoluteTargetXMeters = absoluteTargetXMeters;
    this.absoluteTargetYMeters = absoluteTargetYMeters;
    this.absoluteTargetAngleDegrees = absoluteTargetAngleDegrees;
  }

  public Pose2d asPose2d() {
	  return new Pose2d(getXMeters(), getYMeters(), Rotation2d.fromDegrees(absoluteTargetAngleDegrees));
  }
  
  public Pose3d asPose3d() {
	  return new Pose3d(asPose2d());
  }
  public boolean isLoading() {
	  return this.aprilTagID == 4 || this.aprilTagID == 5;
  }
  
  public int getId() {
	  return this.aprilTagID;
  }
  public AprilTagIDLocation getLocation() {
	  return this.location;
  }
  public double getXMeters() {
    return absoluteTargetXMeters;
  }

  public double getYMeters() {
    return absoluteTargetYMeters;
  }

  public double getAngleDegrees() {
    return absoluteTargetAngleDegrees;
  }
  
  
  @Override
public String toString() {
	return "AprilTagLocation [aprilTagID=" + aprilTagID + ", location=" + location + ", absoluteTargetXMeters="
			+ absoluteTargetXMeters + ", absoluteTargetYMeters=" + absoluteTargetYMeters + ", absoluteTargetAngleDegrees="
			+ absoluteTargetAngleDegrees + "]";
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
    return null;
}
}
