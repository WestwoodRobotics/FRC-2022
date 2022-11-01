package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.AutonConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.drive.DriveCommand;
import frc.robot.commands.drive.DriveZeroCommand;
import frc.robot.commands.intake.IntakeDownCommand;
import frc.robot.commands.intake.IntakeUpCommand;
import frc.robot.commands.magazine.BottomMagazineOffCommand;
import frc.robot.commands.magazine.BottomMagazineOnCommand;
import frc.robot.commands.magazine.TopMagazineOffCommand;
import frc.robot.commands.magazine.TopMagazineOnCommand;
import frc.robot.commands.magazine.TopMagazineToggleCommand;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.commands.vision.VisionShootToggleCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import javax.xml.namespace.QName;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static frc.robot.Constants.SwerveModuleConstants.C_DISTANCE_FROM_CENTER_LENGTH;
import static frc.robot.Constants.SwerveModuleConstants.C_DISTANCE_FROM_CENTER_WIDTH;
public class Autonomous {

    private final String path;
    private final SwerveDrive m_SwerveDrive;
    private final Vision m_vision;
    private final Shooter m_shooter;
    private final Intake m_intake;
    private final Magazine m_magazine;

    private Command sequence;
    private AutonTrajectory autonTrajectory;
    private SwerveDriveKinematics kDriveKinematics;


    public Autonomous(
            SwerveDrive swerveDrive, Vision vision, Magazine magazine, Intake intake, Shooter shooter, String path) {

        this.path = path;
        m_SwerveDrive = swerveDrive;
        m_vision = vision;
        m_magazine = magazine;
        m_shooter = shooter;
        m_intake = intake;

        kDriveKinematics = new SwerveDriveKinematics(
                new Translation2d(C_DISTANCE_FROM_CENTER_WIDTH, C_DISTANCE_FROM_CENTER_LENGTH),
                new Translation2d(-C_DISTANCE_FROM_CENTER_WIDTH, C_DISTANCE_FROM_CENTER_LENGTH),
                new Translation2d(-C_DISTANCE_FROM_CENTER_WIDTH, -C_DISTANCE_FROM_CENTER_LENGTH),
                new Translation2d(C_DISTANCE_FROM_CENTER_WIDTH, -C_DISTANCE_FROM_CENTER_LENGTH)
        );
        
        sequence = new DriveZeroCommand(m_SwerveDrive);
        autonTrajectory = new AutonTrajectory(swerveDrive);
        // initialize();
    }

    // private LinkedBlockingDeque<JSONObject> getFromJSON() {

    //     LinkedBlockingDeque<JSONObject> output = new LinkedBlockingDeque<>();

    //     // parsing instructions json
    //     Object obj = null;
    //     try {
    //         obj = new JSONParser()
    //                 .parse(new FileReader(Filesystem.getDeployDirectory().getPath() + "/paths/" + path + ".json"));
    //     } catch (IOException | org.json.simple.parser.ParseException e) {
    //         // Auto-generated catch block
    //         e.printStackTrace();
    //     }

    //     JSONArray jsonArray = (JSONArray) obj;

    //     // assumes not null then adds all commands to a queue
    //     assert jsonArray != null;
    //     for (Object o : jsonArray) {
    //         output.add((JSONObject) o);
    //     }

    //     return output;
    // }

    public void run() {
        sequence.schedule();
    }

    public void initialize() {
        // loads instructions
        // LinkedBlockingDeque<JSONObject> instructions = getFromJSON();

        // instructions.iterator().forEachRemaining((e) -> {

        //     // checks what command is next then adds the command to the sequence
        //     String command = (String) e.get("commandType");

        //     switch (command) {
        //         case "drive":
        //             sequence = sequence.andThen(new DriveCommand(m_SwerveDrive, e));
        //             break;
        //         case "zero":
        //             sequence = sequence.andThen(new DriveZeroCommand(m_SwerveDrive));
        //             break;
        //         case "limelight":
        //             sequence = sequence.andThen(
        //                     new VisionShootToggleCommand(m_SwerveDrive, m_vision, m_shooter, m_magazine, true));
        //             break;
        //         case "intakeDown":
        //             sequence = sequence.andThen(new IntakeDownCommand(m_intake));
        //             break;
        //         case "intakeUp":
        //             sequence = sequence.andThen(new IntakeUpCommand(m_intake));
        //             break;
        //         case "shoot":
        //             sequence = sequence.andThen(new ShooterToggleCommand(m_shooter, m_magazine, 6800))
        //                     .andThen(new TopMagazineToggleCommand(m_magazine, false));
        //             break;
        //         case "topFeeder":
        //             // if ((boolean) e.get("direction")) 
        //             sequence = sequence.andThen(new TopMagazineOnCommand(m_magazine));
        //             // else sequence = sequence.andThen(new TopMagazineOffCommand(m_magazine));
        //             break;
        //         case "bottomFeeder":
        //             // if ((boolean) e.get("direction"))
        //             sequence = sequence.andThen(new BottomMagazineOnCommand(m_magazine));
        //             // else sequence = sequence.andThen(new BottomMagazineOffCommand(m_magazine));
        //             break;
        //         case "wait":
        //             sequence = sequence.andThen(new WaitCommand(4.5));
        //             break;
        //     }
        // });
    }

    public Command getCommand() {
        
        Trajectory traj_1 = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
            List.of(
                //new Translation2d(0.0, 0.0),
                //new Translation2d(0.0, 0.0)
            ),
                new Pose2d(0.0, 1.0, Rotation2d.fromDegrees(0)
            ),
            autonTrajectory.getTrajectoryConfig());

        // RamseteCommand rCommand = new RamseteCommand(
        //     traj_1, m_SwerveDrive:: getPose, 
        //     new RamseteController(AutonConstants.kRamseteB, AutonConstants.kRamseteZeta), 
        //     new SimpleMotorFeedforward(
        //         DriveConstants.ksVolts, 
        //         DriveConstants.kvVoltSecondsPerMeter, 
        //         DriveConstants.kvVoltsSecondsSquaredPerMeter),
        //     kDriveKinematics, 
        //     m_SwerveDrive:: getWheelSpeeds,
        //     new PIDController(1.5, 0, 0),
        //     new PIDController(1.5, 0, 0),
        //     m_SwerveDrive:: tankDriveVolts,
        //     m_SwerveDrive
        // );



        return autonTrajectory.generateControllerCommand(traj_1);
    }
}
