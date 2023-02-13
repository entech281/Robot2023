
package frc.robot;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


/**
 * 
 *
 * @author dcowden
 */
public class TestThatTestsRun {
    
    @Test
    public void testThatThisIsExecuted(){
    }
    
    @Test
    public void testThatThisBreaks(){
    	
    	assertThrows(RuntimeException.class, () -> {
    		throw new RuntimeException("Error");
    	});
        
    }        
}
