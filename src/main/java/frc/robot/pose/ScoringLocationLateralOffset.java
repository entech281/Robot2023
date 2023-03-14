package.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.RobotConstants; 

public class ScoringLocationLateralOffset {
    enum  LateralOffset {LATERAL_OFFSET_LEFT, LATERAL_OFFSET_RIGHT};
    Optional<Double>  computeLateralOffsetToNextScoringLocation (AprilTagTarget currentTagInView, LateralOffset offset, double offsetFromTag) {
      //return lateral offset FROM where I am TO the next scoring location in the rqeuested direction
      if (offsetFromTag > 0) {
        
      }
      else {

      }
    }
}
