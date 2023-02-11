package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.RobotConstants.TEAM;

/**
 *
 * @author dcowden
 */
public class FieldAprilTag {

    /**
     * Important: absolute angle is relative to field coordinates, and points TOWARD the target, not away from it
     * @param absoluteXLocationInches
     * @param absoluteYLocationInches
     * @param absoluteAngleDegrees
     * @param tagId
     * @param team 
     */
    public FieldAprilTag(double absoluteXLocationInches, double absoluteYLocationInches, double absoluteAngleDegrees, int tagId, TEAM team) {
        positionInches = new Pose2d(new Translation2d(absoluteXLocationInches,absoluteYLocationInches), Rotation2d.fromDegrees(absoluteAngleDegrees));
        this.tagId = tagId;
        this.team = team;

    }

    public int getTagId() {
        return tagId;
    }

    public boolean isRed() {
        return this.team == TEAM.RED;
    }

    public boolean isBlue() {
        return this.team == TEAM.BLUE;
    }

    private Pose2d positionInches;

    public Pose2d getPositionInches() {
        return positionInches;
    }

    public TEAM getTeam() {
        return team;
    }
    private int tagId = 0;
    TEAM team;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.tagId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FieldAprilTag other = (FieldAprilTag) obj;
        return this.tagId == other.tagId;
    }

}