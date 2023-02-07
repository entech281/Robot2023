package frc.robot.pose;

import frc.robot.util.PoseUtil;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dcowden
 */
public class TestAlignCalc {
    
    protected AlignCalc calc = new AlignCalc();

//    protected static Pose2d makePose2dFromCoorindates(double x, double y, double angleDegrees){
//        return new Pose2d(x, y, Rotation2d.fromDegrees(angleDegrees));
//    }
    //created Poseutil.poseFromDoubles instead of having it here!
    
    @Test
    public void testThatRightInFrontOfNodeSuggestsScoring(){
       
        //act like we are directly in front of the A1 node of BlUE_LEFT
        
        //FIELD DETAILS
        //blue left april tag ( tag 8 ) is about x=40, y=42 inches ( bottom left corner of the field)
        //the center of A1 is left of the tag by 21 inches, 
        //and about 25.5 inches behind the tag
        //thus, the center of A1 near tag 8 is at
        // x = 42 - 21 = 21 inches
        // y = 40 - 25.5  = 14.5 inches
        
        //THE ROBOT, if its sitting RIGHT OVER the scoring location
        //our arm is about 48 inches lon
        //the robot is about 30 inches deep, and lets assume the camera is on the middle
        //so the arm sticks out 48 - ( 30/2 ) = 33 inches
        //so our robot would be positioned to score at:
        //x = 21 + 33 = 54 inches
        //y = 14.5 inches   THE ROBOT IS RIGHT NEXT TO THE WALL!
        //robot angle pointed right at that wall is 180 degrees! 
        RobotPose rp = new RobotPose();        
        rp.setCalculatedPose( PoseUtil.poseFromDoubles(54.0,14.5,180.0));
        
        ScoringLocation s = new ScoringLocation(FieldAprilTagCollection.BLUE_LEFT, TargetNode.A1);        
        AlignmentSolution as = calc.calculateSolution(s , rp);
        
        //does it say we should be able to score IMMEDIATELY?
        assertEquals(AlignmentSolution.AlignmentStrategy.DEPLOY_NOW, as.getStrategy());
        
    }
}
