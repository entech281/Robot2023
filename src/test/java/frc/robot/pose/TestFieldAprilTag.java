package frc.robot.pose;

import frc.robot.RobotConstants.TEAM;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 *
 * @author dcowden
 */
public class TestFieldAprilTag {
    
    
    @Test
    public void testGettingRightMatchesFromId(){
        assertEquals( 
                FieldAprilTagCollection.BLUE_LEFT, 
                FieldAprilTagCollection.findFromTag(FieldAprilTagCollection.AprilTagIds.BLUE_LEFT)
        );
    }
    
    @Test
    public void testGetInvalidIdThrowsException(){
        int INVALID=999;
	Assertions.assertThrows(NoSuchElementException.class, () -> {
	FieldAprilTagCollection.findFromTag(INVALID);
	});        
        
    }
    
    @Test
    public void testThatTeamIsPopulatedCorrectly(){
        assertEquals ( TEAM.RED, FieldAprilTagCollection.RED_LEFT.team);
    }
    
    @Test
    public void testFieldTagTeamMethod(){
        assertTrue ( FieldAprilTagCollection.BLUE_LEFT.isBlue());
        assertFalse( FieldAprilTagCollection.BLUE_LEFT.isRed());
    }
    
    @Test
    public void testThatBlueTagsAreAllAtTheSameX(){
        double TOLERANCE = 0.1;
        Assertions.assertEquals(
                FieldAprilTagCollection.BLUE_LEFT.getPositionInches().getX(), 
                FieldAprilTagCollection.BLUE_MIDDLE.getPositionInches().getX(), 
                TOLERANCE);
        Assertions.assertEquals(
                FieldAprilTagCollection.BLUE_LEFT.getPositionInches().getX(), 
                FieldAprilTagCollection.BLUE_RIGHT.getPositionInches().getX(), 
                TOLERANCE);    
        Assertions.assertEquals(
                FieldAprilTagCollection.BLUE_MIDDLE.getPositionInches().getX(), 
                FieldAprilTagCollection.BLUE_RIGHT.getPositionInches().getX(), 
                TOLERANCE);         
    }
    
    @Test
    public void testThatRedTagsAreAllAtTheSameX(){
        double TOLERANCE = 0.1;
        Assertions.assertEquals(
                FieldAprilTagCollection.RED_LEFT.getPositionInches().getX(), 
                FieldAprilTagCollection.RED_MIDDLE.getPositionInches().getX(), 
                TOLERANCE);
        Assertions.assertEquals(
                FieldAprilTagCollection.RED_LEFT.getPositionInches().getX(), 
                FieldAprilTagCollection.RED_RIGHT.getPositionInches().getX(), 
                TOLERANCE);    
        Assertions.assertEquals(
                FieldAprilTagCollection.RED_MIDDLE.getPositionInches().getX(), 
                FieldAprilTagCollection.RED_RIGHT.getPositionInches().getX(), 
                TOLERANCE);         
    }
}
