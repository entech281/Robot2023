package frc.robot;

import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.adapter.DriveInputYawMixer;
import frc.robot.commands.ConeDeployCommand;
import frc.robot.commands.DefaultGripperCommand;
import frc.robot.commands.DeployBrakeCommand;
import frc.robot.commands.DriveBrakeForSeconds;
import frc.robot.commands.DriveCrosshairBalence;
import frc.robot.commands.DriveDirectionCommand;
import frc.robot.commands.DriveDistanceCommand;
import frc.robot.commands.DriveForwardToBalanceCommand;
import frc.robot.commands.DriveSetBrakeMode;
import frc.robot.commands.DriveSetRotationEnableCommand;
import frc.robot.commands.DriveToggleBrakeMode;
import frc.robot.commands.FilteredDriveCommand;
import frc.robot.commands.FlipDirectionCommand;
import frc.robot.commands.ArmElbowForgetHomesCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.HorizontalAlignWithTagCommand;
import frc.robot.commands.PositionElbowCommand;
import frc.robot.commands.PositionTelescopeCommand;
import frc.robot.commands.RetractBrakeCommand;
import frc.robot.commands.SimpleDriveCommand;
import frc.robot.commands.DriveYawToNearestPerpendicular;
import frc.robot.commands.ElbowPositionModeCommand;
import frc.robot.commands.ToggleFieldAbsoluteCommand;
import frc.robot.commands.ToggleGripperCommand;
import frc.robot.commands.TurnRobotRelitiveCommand;
import frc.robot.commands.ZeroGyroCommand;
import frc.robot.commands.nudge.NudgeDirectionCommand;
import frc.robot.commands.nudge.NudgeElbowDownCommand;
import frc.robot.commands.nudge.NudgeElbowUpCommand;
import frc.robot.commands.nudge.NudgeTelescopeBackwardsCommand;
import frc.robot.commands.nudge.NudgeTelescopeForwardCommand;
import frc.robot.commands.nudge.NudgeYawCommand;
import frc.robot.commands.DriveDistanceStraightCommand;
import frc.robot.commands.DriveDistanceStraightWhileAligningCommand;
import frc.robot.filters.DriveInput;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.BrakeSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElbowSubsystem;
import frc.robot.subsystems.GripperSubsystem;
import frc.robot.subsystems.GripperSubsystem.GripperState;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.NavXSubSystem;
import frc.robot.subsystems.SubsystemHolder;
import frc.robot.subsystems.VisionSubsystem;
/**
 *
 * @author dcowden
 * @author aheitkamp
 */
public class CommandFactory {

	private RobotState robotState;
	private DriveSubsystem driveSubsystem;
	private VisionSubsystem visionSubsystem;
	private NavXSubSystem navxSubsystem;
	private ArmSubsystem armSubsystem;
	private ElbowSubsystem elbowSubsystem;
	private GripperSubsystem gripperSubsystem;
	private LEDSubsystem ledSubsystem;
    private BrakeSubsystem brakeSubsystem;

    public CommandFactory(RobotState robotState, SubsystemHolder allSubsystems ){
    	this.driveSubsystem = allSubsystems.getDrive();
    	this.navxSubsystem = allSubsystems.getNavx();
    	this.visionSubsystem = allSubsystems.getVision();
    	this.armSubsystem = allSubsystems.getArm();
        this.elbowSubsystem = allSubsystems.getElbow();
        this.gripperSubsystem = allSubsystems.getGripper();
        this.brakeSubsystem = allSubsystems.getBrake();
        this.robotState = robotState;
        this.ledSubsystem = allSubsystems.getLED();
    }

    public List<Command> getAutoCommandChoices(){
    	//these commands will be available for autonomous mode on the PREMATCH tab
        // First option in list will be the default choice


    	//"RIGHT" means from the driver perspective looking towards the field
    	//ALSO: the robot is facing opposite that way
    	//both autonomous right and autonomous left should move the robout slightly OUTwards
    	return List.of(
                autonomousBalanceCrosshairCommand(),
    			autonomousAutoBalanceCommand(true),
    			autonomousAutoBalanceCommand(false),    			
    			autonomousBalanceDeadRecCommand(true),
    			autonomousBalanceDeadRecCommand(false),
    			autonomousRightCommand(),
    			autonomousLeftCommand(),
    			autonomousConeCommand(),
                autonomousTwoScoreLaneCommand(),
                autonomousTwoScoreBumpCommand()
    	);

    }

    public List<Command> getPrematchCommands(){
    	return List.of(
    		homeTelescopeAndElbow()
    	);
    }

    public Command autonomousTwoScoreLaneCommand() {
        double MOVE_DISTANCE_METERS = -4.2;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            autonomousSetup()
            , autonomousArmHigh()
    		, new ConeDeployCommand(elbowSubsystem, gripperSubsystem)
            , autonomousArmSafe()
            , new DriveDistanceStraightCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.6, 0.2, 0.35, navxSubsystem)
            , new TurnRobotRelitiveCommand(driveSubsystem, navxSubsystem, -131)
            , autofrogGrabCommand()
            , new FlipDirectionCommand(driveSubsystem, navxSubsystem)
            , new ParallelCommandGroup(
                new DriveDistanceStraightCommand(driveSubsystem, 4.35, 0.6, 0.2, 0.35, navxSubsystem)
                , new SequentialCommandGroup(
                    new PositionElbowCommand(elbowSubsystem, 82, true),
                    new PositionTelescopeCommand(armSubsystem, 0.175, true)
                )
            )
            , new DriveYawToNearestPerpendicular(driveSubsystem, navxSubsystem)
            //, new DriveDistanceStraightWhileAligningCommand(driveSubsystem, 0.3, 0.25, navxSubsystem, robotState, 
            //		DriveDistanceStraightWhileAligningCommand.getScoringLocationForWideAuto())
            , new DriveDistanceStraightCommand(driveSubsystem, 0.3, 0.25, navxSubsystem)
            , new ConeDeployCommand(elbowSubsystem, gripperSubsystem)
            , autonomousArmSafe()
            // , new WaitCommand(0.25)
            // , new DriveDistanceStraightCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.76, 0.35, 0.2, navxSubsystem)
        );
        sg.setName("Lane Cone wide + Cone");
        return sg;
    }

    public Command autonomousTwoScoreBumpCommand() {
        double MOVE_DISTANCE_METERS = -3.98;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            autonomousSetup()
            , autonomousArmHigh()
    		, new ConeDeployCommand(elbowSubsystem, gripperSubsystem)
            , autonomousArmSafe()
            , new DriveDistanceStraightCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.4, 0.3, 0.2, navxSubsystem)
            , new TurnRobotRelitiveCommand(driveSubsystem, navxSubsystem, 140)
            , autofrogGrabCommand()
            // , new DriveDistanceStraightCommand(driveSubsystem, -0.1, 0.27, 0.15, 0.3, navxSubsystem)
            , new FlipDirectionCommand(driveSubsystem, navxSubsystem)

            //, new DriveDistanceStraightWhileAligningCommand(driveSubsystem, 4.22, 0.4, 0.5, 0.35, navxSubsystem, robotState, 
           // 		DriveDistanceStraightWhileAligningCommand.getScoringLocationForWideAuto())            
            , new DriveDistanceStraightCommand(driveSubsystem, 4.22, 0.4, 0.5, 0.35, navxSubsystem)
            // , dialMiddlePosition()
            // , new ConeDeployCommand(elbowSubsystem, gripperSubsystem)
            // , autonomousArmSafe()
        );
        sg.setName("Bump Cone wide + Cone");
        return sg;
    }

    public Command autonomousTwoScoreRightCommandFromWorking() {
        double MOVE_DISTANCE_METERS = -3.98;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            autonomousSetup()
            , autonomousArmHigh()
    		, new ConeDeployCommand(elbowSubsystem, gripperSubsystem)
            , autonomousArmSafe()
            , new DriveDistanceStraightCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.76, 0.4, 0.2, navxSubsystem)
            , new TurnRobotRelitiveCommand(driveSubsystem, navxSubsystem, -131)
            , autofrogGrabCommand()
            // , new DriveDistanceStraightCommand(driveSubsystem, -0.1, 0.27, 0.15, 0.3, navxSubsystem)
            , new FlipDirectionCommand(driveSubsystem, navxSubsystem)
            , new DriveDistanceStraightCommand(driveSubsystem, 4.22, 0.7, 0.5, 0.35, navxSubsystem)
            // , dialMiddlePosition()
            // , new ConeDeployCommand(elbowSubsystem, gripperSubsystem)
            // , autonomousArmSafe()
        );
        sg.setName("Cone wide + Cone");
        return sg;
    }    
    
    public List<Command> getTestCommands(){
    	//these will be available to run ad-hoc on the TESTING tab
    	return List.of (
			highScoringElbowCommand(),
			middleScoringElbowCommand(),
			groundScoringElbowCommand(),
			loadingElbowCommand(),
			carryElbowCommand(),
            homeTelescopeAndElbow(),
            new ArmElbowForgetHomesCommand(elbowSubsystem,armSubsystem),
			new PositionTelescopeCommand(armSubsystem,RobotConstants.ARM.POSITION_PRESETS.MAX_METERS, false),
			new PositionTelescopeCommand(armSubsystem,RobotConstants.ARM.POSITION_PRESETS.SCORE_MIDDLE_METERS, false),
			new PositionElbowCommand(elbowSubsystem,RobotConstants.ELBOW.POSITION_PRESETS.MIN_POSITION_DEGREES, false),
			new GripperCommand(gripperSubsystem,GripperState.kClose,"CloseGripper"),
			new GripperCommand(gripperSubsystem,GripperState.kOpen,"OpenGripper"),
            new FlipDirectionCommand(driveSubsystem, navxSubsystem),
            new DriveDirectionCommand(driveSubsystem, 0, -0.2, 1),
            new DriveDistanceCommand(driveSubsystem, 2.5, 0.4),
            autonomousTwoScoreLaneCommand(),
            autoGroundPickupPositionCube()
    	);
    }

    public Command testDownwardSoftConePlacement() {
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            	new ZeroGyroCommand(navxSubsystem, driveSubsystem)
                , new GripperCommand(gripperSubsystem, GripperState.kClose)
                , new DriveSetBrakeMode(driveSubsystem)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true)
                , new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.SCORE_HIGH_METERS, true)
                , new WaitCommand(1.0)

            );
            sg.setName("AutonomousBalanceDeadRecCommand");
            return sg;
    }


    public Command getAutonomousChoice() {
        return autonomousAutoBalanceCommand(false);
    }

    private Command autonomousSetup() {
        return new SequentialCommandGroup(
            new ZeroGyroCommand(navxSubsystem, driveSubsystem)
            , new RetractBrakeCommand(brakeSubsystem)
	        , new GripperCommand(gripperSubsystem, GripperState.kClose)
	        , new DriveSetBrakeMode(driveSubsystem)
	        , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true)
        );
    }

    private Command autonomousArmHigh() {
        return new SequentialCommandGroup(
	          new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true)
	        , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.SCORE_HIGH_METERS, true)
        );
    }

    private Command autonomousScoreCube() {
        return new SequentialCommandGroup(
              new WaitCommand(0.75)
            , new GripperCommand(gripperSubsystem, GripperState.kOpen)
            , new WaitCommand(0.5)
        );
    }

    private Command autonomousArmSafe() {
        return new SequentialCommandGroup(
              new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true)	        
            , new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, true)  
        );
    }

    public Command autoGroundPickupPositionCone() {
        SequentialCommandGroup sg = new SequentialCommandGroup(
            new PositionElbowCommand(elbowSubsystem, 30.75, true),
            //from working version new PositionTelescopeCommand(armSubsystem, 0.42, true)
            new PositionTelescopeCommand(armSubsystem, 0.45, true)
        );
        sg.setName("Dial ground position COne");
        return sg;
    }

    public Command autoGroundPickupPositionCube() {
        SequentialCommandGroup sg = new SequentialCommandGroup(
            new GripperCommand(gripperSubsystem, GripperState.kOpen),
            new PositionElbowCommand(elbowSubsystem, 30.75, true),
            new PositionTelescopeCommand(armSubsystem, 0.37, true)
        );
        sg.setName("Dial ground position Cube");
        return sg;
    }

    public Command autofrogGrabCommand() {
        return new SequentialCommandGroup(
            new GripperCommand(gripperSubsystem, GripperState.kOpen),
            autoGroundPickupPositionCone()
            // from prior working, new DriveDistanceStraightCommand(driveSubsystem, 0.1375, 0.22, 0.15, 0.3, navxSubsystem)
            , new DriveDistanceStraightCommand(driveSubsystem, 0.139, 0.22, 0.15, 0.3, navxSubsystem)
            , new GripperCommand(gripperSubsystem, GripperState.kClose)
            , dialCarryPosition()
        );
    }

    public Command telefrogGrabCommand() {
        return new SequentialCommandGroup(
            new GripperCommand(gripperSubsystem, GripperState.kOpen)
            , autoGroundPickupPositionCube()
            , new WaitCommand(0.25)
            , new GripperCommand(gripperSubsystem, GripperState.kClose)
            , dialCarryPosition()
        );
    }

    public Command autonomousConeCommand() {
        double MOVE_DISTANCE_METERS = -4.0;
        double HOLD_BRAKE_TIME = 2.0;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
              autonomousSetup()
            , autonomousArmHigh()
    		, new ConeDeployCommand(elbowSubsystem, gripperSubsystem)
            , autonomousArmSafe()
            , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.4, 0.3, .1)
            , new DriveBrakeForSeconds(driveSubsystem, HOLD_BRAKE_TIME)
        );
        sg.setName("Cone Wide");
        return sg;
    }

    public Command autonomousRightCommand() {

        double MOVE_DISTANCE_METERS = -3.2;
        double MOVE_SECS  = 1.5;    
        double JOG_FORWARD_SPEED = -0.15;
        double JOG_RIGHT_SPEED = -0.15;
        double HOLD_BRAKE_TIME = 2.0;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
              autonomousSetup()
            , autonomousArmHigh()
            , autonomousScoreCube()
            , autonomousArmSafe()
            , new DriveDirectionCommand(driveSubsystem,JOG_FORWARD_SPEED,JOG_RIGHT_SPEED,MOVE_SECS)
            , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.4, 0.3, .1)
            , new DriveBrakeForSeconds(driveSubsystem, HOLD_BRAKE_TIME)
        );
        sg.setName("Cube Right");
        return sg;
    }

    public Command autonomousLeftCommand() {
        double MOVE_DISTANCE_METERS = -3.2;
        double MOVE_SECS  = 1.5;    
        double JOG_FORWARD_SPEED = -0.15;
        double JOG_RIGHT_SPEED = 0.15;

        double HOLD_BRAKE_TIME = 2.0;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
              autonomousSetup()
            , autonomousArmHigh()
            , autonomousScoreCube()
            , autonomousArmSafe()
            , new DriveDirectionCommand(driveSubsystem,JOG_FORWARD_SPEED,JOG_RIGHT_SPEED,MOVE_SECS)
            , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.4, 0.3, .1)
            , new DriveBrakeForSeconds(driveSubsystem, HOLD_BRAKE_TIME)
        );
        sg.setName("Cube Left");
        return sg;
    }

    public Command autonomousBalanceDeadRecCommand(boolean useBrakes) {
        double MOVE_DISTANCE_METERS = -2.465;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
              autonomousSetup()
            , autonomousArmHigh()
            , autonomousScoreCube()
            , autonomousArmSafe()
            , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.3, 0.3, .1)

        );
        if ( useBrakes) {
        	sg.addCommands(deployBrakeCommand());
        	sg.setName("Center DeadRec Balance w/brakes");
        }
        else {
        	sg.setName("Center DeadRec Balance no brakes");
        }
        return sg;
    }

    public Command autonomousAutoBalanceCommand(boolean useBrakes) {
        double MOVE_DISTANCE_METERS = -4.0;   // Distance to clear the Charging Station
        double MOVE_SPEED = 0.35;              // Speed when clearing the community zone
        double HOLD_BRAKE_TIME = 0;         // Time to hold brake when changing direction
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            // MA:  Ask DC why these were here
            //  new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true)    
            //, new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.MIN_POSITION_DEGREES, true)                
              autonomousSetup()
            , autonomousArmHigh()
            , autonomousScoreCube()
            , autonomousArmSafe()
            , new ConditionalCommand(autoDriveOverAndBalance(MOVE_DISTANCE_METERS, MOVE_SPEED, 
            		HOLD_BRAKE_TIME, 
            		RobotConstants.DRIVE.BALANCE_APPROACH_SPEED,useBrakes),
                                     autoDriveBalanceOnly(-RobotConstants.DRIVE.BALANCE_APPROACH_SPEED,useBrakes), 
                                     this::isTimeForCommunityMove)
        );
        if ( useBrakes) {
            sg.setName("Center Auto Balance w/brakes");        	
        }
        else {
        	sg.setName("Center Auto Balance  no brakes"); 
        }

        return sg;
    }

    public Command autonomousBalanceCrosshairCommand() {
        SequentialCommandGroup sg =  new SequentialCommandGroup(
              autonomousSetup()
            , autonomousArmHigh()
            , autonomousScoreCube()
            , autonomousArmSafe()
            , new DriveCrosshairBalence(driveSubsystem, navxSubsystem, brakeSubsystem)
            , deployBrakeCommand()

        );
        sg.setName("Crosshair Balence Auto");
        return sg;
    }

    private Command autoDriveOverAndBalance(double over_distance, double over_speed, double brake_time, double balance_speed, boolean useBrakes) {
        return new SequentialCommandGroup(
              new DriveDistanceCommand(driveSubsystem, over_distance, over_speed, 0.3, .1)
            , new DriveBrakeForSeconds(driveSubsystem, brake_time)
            , new DriveForwardToBalanceCommand(driveSubsystem, navxSubsystem, brakeSubsystem, balance_speed,useBrakes)
        );
    }

    
    public Command autoDriveBalanceOnly(double balance_speed, boolean useBrakes) {
        return new DriveForwardToBalanceCommand(driveSubsystem, navxSubsystem, brakeSubsystem, balance_speed,useBrakes);
    }

    private boolean isTimeForCommunityMove() {
        // NOTE: getMatchTime() documentation says it returns the time REMAINING in the current game phase (auto/teleop)
        double SECS_REQUIRED_FOR_OVER_AND_BALANCE = 8.0;
        if ( DriverStation.getMatchTime() <  SECS_REQUIRED_FOR_OVER_AND_BALANCE) {
            DriverStation.reportWarning("AUTONOMOUS: Balance only",false);
            return false;
       }
       DriverStation.reportWarning("AUTONMOUS: Move and Balance",false);
       return true;
    }

    private Supplier<DriveInput> addYawToOperatorJoystickInput(Supplier<DriveInput> operatorJoystickInput){
    	return new DriveInputYawMixer(robotState, operatorJoystickInput);
    }

    public void setDefaultDriveCommand (Command newDefaultCommand ) {
    	driveSubsystem.setDefaultCommand(newDefaultCommand);
    }
    public void setDefaultGripperCommand ( Command newDefaultCommand) {
    	gripperSubsystem.setDefaultCommand(newDefaultCommand);
    }

    public Command gripperPanelSyncCommand( Supplier<Boolean> panelGripperButtonSupplier ) {
    	return new DefaultGripperCommand(gripperSubsystem, panelGripperButtonSupplier);
    }

    public Command filteredDriveCommand( Supplier<DriveInput> operatorInput) {
    	return new FilteredDriveCommand(driveSubsystem,addYawToOperatorJoystickInput( operatorInput));
    }

    public Command driveCommand(Supplier<DriveInput> operatorInput) {
        return new SimpleDriveCommand(driveSubsystem, addYawToOperatorJoystickInput(operatorInput));
    }

    public Command deployBrakeCommand() {
        return new DeployBrakeCommand(brakeSubsystem);
    }

    public Command retractBrakeCommand() {
        return new RetractBrakeCommand(brakeSubsystem);
    }

    public Command toggleBrakeModeCommand() {
        return new DriveToggleBrakeMode(driveSubsystem);
    }

	public Command toggleFieldAbsoluteCommand() {
		return new ToggleFieldAbsoluteCommand(driveSubsystem);
	}

    public Command toggleGripperCommand() {
        return new ToggleGripperCommand(gripperSubsystem);
    }

	public Command setDriverRotationEnableCommand(boolean newValue) {
		return new DriveSetRotationEnableCommand(driveSubsystem,newValue);
	}

    public Command alignHorizontalToTag( Supplier<DriveInput> operatorInput) {
  		return new HorizontalAlignWithTagCommand(driveSubsystem, ledSubsystem, addYawToOperatorJoystickInput(operatorInput), robotState);
    }

    public Command getYawToNearestPerpendicular() {
        return new DriveYawToNearestPerpendicular(driveSubsystem, navxSubsystem );
    }

    public Command getZeroGyro() {
        return new ZeroGyroCommand(navxSubsystem, driveSubsystem);
    }

    public Command nudgeLeftCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.LEFT);
    }

    public Command nudgeRightCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.RIGHT);
    }

    public Command oneLocationLeftCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.SCORE_LEFT);
    }

    public Command oneLocationRightCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.SCORE_RIGHT);
    }

    public Command nudgeForwardCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.FORWARD);
    }

    public Command nudgeBackwardCommand() {
        return new NudgeDirectionCommand(driveSubsystem, NudgeDirectionCommand.DIRECTION.BACKWARD);
    }

    public Command nudgeYawLeftCommand() {
        return new NudgeYawCommand(driveSubsystem, NudgeYawCommand.DIRECTION.LEFT);
    }

    public Command nudgeYawRightCommand() {
        return new NudgeYawCommand(driveSubsystem, NudgeYawCommand.DIRECTION.RIGHT);
    }

    public Command driveDistanceCommand(double distanceMeters) {
        return new DriveDirectionCommand(driveSubsystem, distanceMeters, 0, 0);
    }

    public Command groundRetractedPosition() {
        return new PositionElbowCommand(elbowSubsystem, 12, true);
    }

    public Command nudgeElbowUpCommand() {
        return new NudgeElbowUpCommand(elbowSubsystem, false);
    }

    public Command nudgeElbowDownCommand() {
        return new NudgeElbowDownCommand(elbowSubsystem, false);
    }

    public Command nudgeArmForwardCommand() {
        return new NudgeTelescopeForwardCommand(armSubsystem, false);
    }

    public Command nudgeArmBackwardCommand() {
        return new NudgeTelescopeBackwardsCommand(armSubsystem, false);
    }

    public Command openGripperCommand() {
        return new GripperCommand(gripperSubsystem, GripperState.kOpen);
    }

    public Command closeGripperCommand() {
        return new GripperCommand(gripperSubsystem, GripperState.kClose);
    }

    public Command scoreHighCommand() {
        return new SequentialCommandGroup(
        		new ConeDeployCommand(elbowSubsystem, gripperSubsystem),
                dialCarryPosition()
            );
    }

    public Command homeTelescopeAndElbow() {
    	SequentialCommandGroup sg =  new SequentialCommandGroup(
    			armPositionHome(),
    			minElbowCommand()
    	);
    	sg.setName("homeTelescopeAndElbow");
    	return sg;
    }

    public Command armPositionHome() {
        return new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true);
     }

     public Command armPositionFullExtension() {
        return new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MAX_METERS, true);
     }

     public Command dialCarryPosition() {
        return new SequentialCommandGroup(
            armPositionHome(),
            carryElbowCommand()
        );
     }

     public Command dialHighPosition() {
        return new SequentialCommandGroup(
            highScoringElbowCommand(),
            armPositionFullExtension()
        );
     }

     public Command dialMiddlePosition() {
        return new SequentialCommandGroup(
            middleScoringElbowCommand(),
            new PositionTelescopeCommand(armSubsystem, 0.16, true)
        );
     }

     public Command dialLoadPosition() {
        return new SequentialCommandGroup(
            armPositionHome(),
            loadingElbowCommand()
        );
     }

     /**
      * This series moves only the elbow
      */
     public Command highScoringElbowCommand() {
     	return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, "highScoringElbowCommand");
     }

     public Command middleScoringElbowCommand() {
         return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.SCORE_MIDDLE_DEGREES, "middleScoringElbowCommand");
     }

     public Command groundScoringElbowCommand() {
         return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.SCORE_LOW_DEGREES, "groundScoringElbowCommand");
     }

     public Command loadingElbowCommand() {
         return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.LOAD_STATION_DEGREES, "loadingElbowCommand");
     }

     public Command carryElbowCommand() {
         return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, "carryElbowCommand");
     }

     public Command minElbowCommand() {
         return createNamedElbowPositionCommand( RobotConstants.ELBOW.POSITION_PRESETS.MIN_POSITION_DEGREES, "minElbowCommand");
     }

     private Command createNamedElbowPositionCommand(double position, String name) {
     	Command p = new  PositionElbowCommand(elbowSubsystem, position, true);
     	p.setName(name);
     	return p;
     }

}
