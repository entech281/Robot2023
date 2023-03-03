package frc.robot.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.util.EntechUtils;

/**
 * Captures Exceptions,and logs a copy of each unique one to the shuffleboard testing tab
 * @author davec
 *
 */
public class ExceptionHandler {

	private ShuffleboardTab testingTab;
	private Map<String,Throwable> errorMap = new HashMap<>();
	public ExceptionHandler() {
		testingTab = Shuffleboard.getTab("Testing");
		testingTab.addString("Robot Exception Text", this::getUniqueExceptions).withSize(5, 2).withPosition(0, 5);
		testingTab.addInteger("Robot Exception Count", this::getExceptionCount).withWidget(BuiltInWidgets.kDial).withSize(2, 1).withPosition(0, 4);
	}
	
	public void handleException(Throwable error) {
		String traceKey = getKeyForThrowable(error);
		errorMap.put(traceKey, error);
	}
	
	private String getKeyForThrowable(Throwable t) {
		return exceptionToString(t);
	}

	private String exceptionToString(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}	
	private Integer getExceptionCount() {
		return errorMap.size();
	}
	
	private String getUniqueExceptions() {
		StringBuilder sb = new StringBuilder();
		errorMap.keySet().forEach((k)->{
			sb.append(k);
		});
		return sb.toString();
	}
	
}
