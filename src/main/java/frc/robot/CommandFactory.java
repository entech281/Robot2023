package frc.robot;

import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.adapter.DriveInputYawMixer;
import frc.robot.commands.ConeDeployCommand;
import frc.robot.commands.DefaultGripperCommand;
import frc.robot.commands.DeployBrakeCommand;
import frc.robot.commands.DriveBrakeForSeconds;
import frc.robot.commands.DriveDirectionCommand;
import frc.robot.commands.DriveDistanceCommand;
import frc.robot.commands.DriveForwardToBalanceCommand;
import frc.robot.commands.DriveSetBrake;
import frc.robot.commands.DriveSetRotationEnableCommand;
import frc.robot.commands.DriveToggleBrakeMode;
import frc.robot.commands.FilteredDriveCommand;
import frc.robot.commands.GripperCommand;
import frc.robot.commands.HorizontalAlignWithTagCommand;
import frc.robot.commands.PositionElbowCommand;
import frc.robot.commands.PositionTelescopeCommand;
import frc.robot.commands.RetractBrakeCommand;
import frc.robot.commands.SimpleDriveCommand;
import frc.robot.commands.SnapYawDegreesCommand;
import frc.robot.commands.ToggleFieldAbsoluteCommand;
import frc.robot.commands.ToggleGripperCommand;
import frc.robot.commands.TogglePrecisionDriveCommand;
import frc.robot.commands.ZeroGyroCommand;
import frc.robot.commands.nudge.NudgeDirectionCommand;
import frc.robot.commands.nudge.NudgeElbowDownCommand;
import frc.robot.commands.nudge.NudgeElbowUpCommand;
import frc.robot.commands.nudge.NudgeTelescopeBackwardsCommand;
import frc.robot.commands.nudge.NudgeTelescopeForwardCommand;
import frc.robot.commands.nudge.NudgeYawCommand;
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
    	//both authonomous right and autonomous left should move the robout slightly OUTwards
    	return List.of( 
    			autonomousAutoBalanceCommand(),    			
    			autonomousBalanceDeadRecCommand(),
    			autonomousRightCommand(),
    			autonomousLeftCommand()
    	);

    }
    
    public List<Command> getPrematchCommands(){
    	return List.of(
    		homeTelescopeAndElbow()
    	);
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
			new PositionTelescopeCommand(armSubsystem,RobotConstants.ARM.POSITION_PRESETS.MAX_METERS, false),
			new PositionTelescopeCommand(armSubsystem,RobotConstants.ARM.POSITION_PRESETS.SCORE_MIDDLE_METERS, false),
			new PositionElbowCommand(elbowSubsystem,RobotConstants.ELBOW.POSITION_PRESETS.MIN_POSITION_DEGREES, false),
			new GripperCommand(gripperSubsystem,GripperState.kClose,"CloseGripper"),
			new GripperCommand(gripperSubsystem,GripperState.kOpen,"OpenGripper")

    	);
    }
    
    public Command testDownwardSoftConePlacement() {
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            	new ZeroGyroCommand(navxSubsystem)
                , new GripperCommand(gripperSubsystem, GripperState.kClose)
                , new DriveSetBrake(driveSubsystem)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true)            
                , new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.SCORE_HIGH_METERS, true)
                , new WaitCommand(1.0)
 
            );
            sg.setName("AutonomousBalanceDeadRecCommand");
            return sg;    	
    }


    public Command getAutonomousChoice() {
        return autonomousAutoBalanceCommand();
    }
    
//    public Command autonomousFarCommand() {
//        double MOVE_DISTANCE_METERS = -4.0;
//        double HOLD_BRAKE_TIME = 2.0;
//        Command c =  createScoreAndDriveDistance(MOVE_DISTANCE_METERS,HOLD_BRAKE_TIME);
//        c.setName("autonomousFar");
//        return c;
//    }
    
    public Command autonomousRightCommand() {
        double MOVE_DISTANCE_METERS = -3.2;
        double MOVE_SECS  = 1.5;    
        double JOG_FORWARD_SPEED = -0.15;
        double JOG_RIGHT_SPEED = -0.15;
        double HOLD_BRAKE_TIME = 2.0;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            	new ZeroGyroCommand(navxSubsystem)
                , new GripperCommand(gripperSubsystem, GripperState.kClose)
                , new DriveSetBrake(driveSubsystem)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true)
                , new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.SCORE_HIGH_METERS, true)
                , new WaitCommand(1.0)
                , new GripperCommand(gripperSubsystem, GripperState.kOpen)
                , new WaitCommand(0.5)
                , new PositionTelescopeCommand ( armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS,true)
                , new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, true)
                , new DriveDirectionCommand(driveSubsystem,JOG_FORWARD_SPEED,JOG_RIGHT_SPEED,MOVE_SECS)
                , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.4, 0.3, .1)
                , new DriveBrakeForSeconds(driveSubsystem, HOLD_BRAKE_TIME)
            );
            sg.setName("autonomousRight");
            return sg;    	
    }
    
    public Command autonomousLeftCommand() {
    	//TODO: refactor. high duplicatoin betwene center auto, and with Right ( this is a mirror of Right)
        double MOVE_DISTANCE_METERS = -3.2;
        double MOVE_SECS  = 1.5;    
        double JOG_FORWARD_SPEED = -0.15;
        double JOG_RIGHT_SPEED = 0.15;
        double HOLD_BRAKE_TIME = 2.0;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            	new ZeroGyroCommand(navxSubsystem)
                , new GripperCommand(gripperSubsystem, GripperState.kClose)
                , new DriveSetBrake(driveSubsystem)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true)
                , new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.SCORE_HIGH_METERS, true)
                , new WaitCommand(1.0)
                , new GripperCommand(gripperSubsystem, GripperState.kOpen)
                , new WaitCommand(0.5)
                , new PositionTelescopeCommand ( armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS,true)
//                , new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, true)
                , new DriveDirectionCommand(driveSubsystem,JOG_FORWARD_SPEED,JOG_RIGHT_SPEED,MOVE_SECS)
                , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.4, 0.3, .1)
                , new DriveBrakeForSeconds(driveSubsystem, HOLD_BRAKE_TIME)
            );
            sg.setName("autonomousLe");
            return sg;    	
    }
    
    public Command autonomousBalanceDeadRecCommand() {
        double MOVE_DISTANCE_METERS = -2.6;
        double HOLD_BRAKE_TIME = 2.0;
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            	new ZeroGyroCommand(navxSubsystem)
                , new GripperCommand(gripperSubsystem, GripperState.kClose)
                , new DriveSetBrake(driveSubsystem)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true)
                , new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.SCORE_HIGH_METERS, true)
                , new WaitCommand(1.0)
                , new GripperCommand(gripperSubsystem, GripperState.kOpen)
                , new WaitCommand(0.5)
                , new PositionTelescopeCommand ( armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS,true)
                , new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, true)
                , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, 0.4, 0.3, .1)
                , new DriveBrakeForSeconds(driveSubsystem, HOLD_BRAKE_TIME)
            );
            sg.setName("AutonomousBalanceDeadRecCommand");
        return sg;
    }
    
    public Command autonomousAutoBalanceCommand() {
        double MOVE_DISTANCE_METERS = -4.0;   // Distance to clear the Charging Station
        double MOVE_SPEED = 0.35;              // Speed when clearing the community zone
        double HOLD_BRAKE_TIME = 0;         // Time to hold brake when changing direction
        double BALANCE_SPEED = 0.16;          // Speed when trying to balance
        SequentialCommandGroup sg =  new SequentialCommandGroup(
            	new ZeroGyroCommand(navxSubsystem)
                , new GripperCommand(gripperSubsystem, GripperState.kClose)
                , new DriveSetBrake(driveSubsystem)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true)            
                , new PositionElbowCommand(elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.SCORE_HIGH_DEGREES, true)
                , new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.SCORE_HIGH_METERS, true)
                , new WaitCommand(1.0)
                , new GripperCommand(gripperSubsystem, GripperState.kOpen)
                , new WaitCommand(0.5)
                , new PositionTelescopeCommand ( armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS,true)
                , new PositionElbowCommand ( elbowSubsystem, RobotConstants.ELBOW.POSITION_PRESETS.CARRY_DEGREES, true)
                , new DriveDistanceCommand(driveSubsystem, MOVE_DISTANCE_METERS, MOVE_SPEED, 0.3, .1)
                , new DriveBrakeForSeconds(driveSubsystem, HOLD_BRAKE_TIME)
                , new DriveForwardToBalanceCommand(driveSubsystem, navxSubsystem, BALANCE_SPEED)
            );
        sg.setName("centerAutoBalance");
        return sg;
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

    public Command togglePrecisionDriveCommand() {
        return new TogglePrecisionDriveCommand(driveSubsystem);
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

    public Command snapYawDegreesCommand(double angle) {
        return new SnapYawDegreesCommand(driveSubsystem, angle,robotState );
    }

    public Command getZeroGyro() {
        return new ZeroGyroCommand(navxSubsystem);
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
        return new NudgeYawCommand(driveSubsystem, NudgeYawCommand.DIRECTION.LEFT,robotState);
    }

    public Command nudgeYawRightCommand() {
        return new NudgeYawCommand(driveSubsystem, NudgeYawCommand.DIRECTION.RIGHT,robotState);
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
                new PositionTelescopeCommand(armSubsystem, RobotConstants.ARM.POSITION_PRESETS.MIN_METERS, true),
                new GripperCommand(gripperSubsystem, GripperState.kClose)
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
            middleScoringElbowCommand()
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
