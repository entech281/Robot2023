package frc.robot.pose;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;

public class TestOurAprilTagsMatchFirstOnes {

	
	protected AprilTagFieldLayout WPI_2023_LAYOUT;
	
	
	protected void loadWpiLayout() {
		try {
			WPI_2023_LAYOUT = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3 , 4, 5, 6, 7, 8})
	public void testOurTagMatchesFRCTag(int input_tag) {
		loadWpiLayout();
		Pose2d wpi_tag = WPI_2023_LAYOUT.getTagPose(input_tag).get().toPose2d();

		Pose2d our_tag_meters = AprilTagLocation.findFromTag(input_tag).asPose2d();
		assertEquals(wpi_tag, our_tag_meters);
	}
}
