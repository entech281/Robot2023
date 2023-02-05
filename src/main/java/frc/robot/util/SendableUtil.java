package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.SendableBuilder;
import java.util.function.Supplier;

/**
 * Some utilities to make sending data to smart dashboard easier
 * @author dcowden
 */
public class SendableUtil {
    
    public static void initPose2dSendable(String rootName, Supplier<Pose2d> pose2dGetter, SendableBuilder sb){
        sb.addDoubleProperty( rootName + "_x", () -> { return pose2dGetter.get().getX(); },null );
        sb.addDoubleProperty( rootName + "_y", () -> { return pose2dGetter.get().getY(); },null );
    }
}
