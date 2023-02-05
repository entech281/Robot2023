package frc.robot.learning;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dcowden
 */
public class TestPose2d {
    
    @Test
    public void testAddingTwoPosesTogether(){
        double X = 1.0;
        double Y = 2.0;
        double TOLERANCE = 0.001;
        Pose2d firstOne = new Pose2d(X, Y, new Rotation2d(0.0));
        Pose2d zero = new Pose2d(0,0,new Rotation2d(0.0));
        Transform2d t = new Transform2d (zero,firstOne);
        assertEquals(X,t.getX(),TOLERANCE);
        assertEquals(Y,t.getY(),TOLERANCE);
    }
    
}
