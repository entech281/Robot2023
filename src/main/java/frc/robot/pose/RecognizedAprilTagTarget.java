
package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;

/**
 * Value object that represents a target we've recognized via vision.
 * @author dcowden
 */
public class RecognizedAprilTagTarget {
    
    public RecognizedAprilTagTarget(Pose2d relativeCamera, int tagId) {
        this.tagId = tagId;
        this.offsetFromCamera = relativeCamera;
    }
    
    Pose2d offsetFromCamera;    
    private int tagId = 0;

    public int getTagId() {
        return tagId;
    }

    public Pose2d getOffsetFromCamera() {
        return offsetFromCamera;
    }

    

}
