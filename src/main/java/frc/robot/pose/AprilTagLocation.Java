package frc.robot.pose;



public class AprilTagLocation {

    public static AprilTagLocation BLUE_LOADING = new TagLocation(4);
    public static AprilTagLocation BLUE_MIDDLE = new TagLocation(2);
    public static AprilTagLocation BLUE_RIGHT = new TagLocation(1);
    public static AprilTagLocation BLUE_LEFT = new TagLocation(3);
    public static AprilTagLocation RED_LOADING = new TagLocation(5);
    public static AprilTagLocation RED_MIDDLE = new TagLocation(7);
    public static AprilTagLocation RED_RIGHT = new TagLocation(6);
    public static AprilTagLocation RED_LEFT = new TagLocation(8); 
      
    public static enum AprilTagIDLocation {
        BLUE_LOADING,
        BLUE_LEFT,
        BLUE_RIGHT,
        BLUE_MIDDLE,
        RED_LOADING,
        RED_LEFT,
        RED_RIGHT,
        RED_MIDDLE,
      }
      
    private final AprilTagIDLocation Location;
      
    protected TagLocation(AprilTagIDLocation LOcation) {
    int Location = new TagIDLocation(new TagLocation(LOcation));
    AprilTagIDLocation Location = LOcation; 
    
  } 
}