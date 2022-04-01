package frc.robot;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drive.DriveCommand;
import frc.robot.commands.drive.DriveZeroCommand;
import frc.robot.commands.feeder.BottomFeederOffCommand;
import frc.robot.commands.feeder.BottomFeederOnCommand;
import frc.robot.commands.feeder.TopFeederOffCommand;
import frc.robot.commands.feeder.TopFeederOnCommand;
import frc.robot.commands.feeder.TopFeederToggleCommand;
import frc.robot.commands.intake.IntakeDownCommand;
import frc.robot.commands.intake.IntakeUpCommand;
import frc.robot.commands.shooter.ShooterOnCommand;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.commands.vision.AlignLimelightCommand;
import frc.robot.commands.vision.AlignLimelightRotationCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

import org.ejml.dense.row.linsol.qr.SolvePseudoInverseQrp_DDRM;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;
import java.util.concurrent.LinkedBlockingDeque;

import javax.lang.model.util.ElementScanner6;

public class Autonomous {

    private final String path;
    private final SwerveDrive m_SwerveDrive;
    private final Vision m_vision;
    private final Shooter m_shooter;    
    private final Intake m_intake;
    private final Feeder m_feeder;

    private Command sequence;

    public Autonomous(SwerveDrive swerveDrive, Vision vision, Feeder feeder, Intake intake, Shooter shooter, String path) {

        this.path = path;
        m_SwerveDrive = swerveDrive;
        m_vision = vision;
        m_feeder = feeder;
        m_shooter = shooter;
        m_intake = intake;


        sequence = new DriveZeroCommand(m_SwerveDrive);
        initialize();
    }

    private LinkedBlockingDeque<JSONObject> getFromJSON() {

        LinkedBlockingDeque<JSONObject> output = new LinkedBlockingDeque<>();

        // parsing instructions json
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader(Filesystem.getDeployDirectory().getPath() + "/paths/" + path + ".json"));
        } catch (IOException | org.json.simple.parser.ParseException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray jsonArray = (JSONArray) obj;

        // assumes not null then adds all commands to a queue
        assert jsonArray != null;
        for (Object o : jsonArray) {
            output.add((JSONObject) o);
        }

        return output;

    }

    public void run() {
        sequence.schedule();
    }

    public void initialize() {
        // loads instructions
        LinkedBlockingDeque<JSONObject> instructions = getFromJSON();
        
        instructions.iterator().forEachRemaining((e) -> {

            // checks what command is next then adds the command to the sequence
            String command = (String) e.get("commandType");

            switch (command) {
                case "drive":
                    sequence = sequence.andThen(new DriveCommand(m_SwerveDrive, e));
                    break;
                case "zero":
                    sequence = sequence.andThen(new DriveZeroCommand(m_SwerveDrive));
                    break;
                case "limelight":
                    sequence = sequence.andThen(new AlignLimelightRotationCommand(m_SwerveDrive, m_vision));
                    break;
                case "intakeDown":
                    sequence = sequence.andThen(new IntakeDownCommand(m_intake));
                    break;
                case "intakeUp":
                    sequence = sequence.andThen(new IntakeUpCommand(m_intake));
                    break;
                case "shoot":
                    sequence = sequence.andThen(new ShooterToggleCommand(m_shooter, 3000)).andThen(new TopFeederToggleCommand(m_feeder, false));
                    break;
                case "topFeeder":
                    if ((boolean)e.get("direction"))
                        sequence = sequence.andThen(new TopFeederOnCommand(m_feeder));
                    else
                        sequence = sequence.andThen(new TopFeederOffCommand(m_feeder));
                    break;
                case "bottomFeeder":
                    if ((boolean)e.get("direction"))
                        sequence = sequence.andThen(new BottomFeederOnCommand(m_feeder));
                    else
                        sequence = sequence.andThen(new BottomFeederOffCommand(m_feeder));
                    break;
                case "wait":
                    sequence = sequence.andThen(new WaitCommand(4.5));
                    break;

            }
        });    
        System.out.println(sequence.toString());


//        System.out.println(
//      System.currentTimeMillis() + ", " +
//      outputState.speedMetersPerSecond+ ", " +
//      drive_vel + ", " +
//      driveMotorOutput);


    }


    public Command getCommand() {
        return sequence;
    }

}
