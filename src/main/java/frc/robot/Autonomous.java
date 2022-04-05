package frc.robot;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drive.DriveCommand;
import frc.robot.commands.drive.DriveZeroCommand;
import frc.robot.commands.magazine.BottomMagazineOffCommand;
import frc.robot.commands.magazine.BottomMagazineOnCommand;
import frc.robot.commands.magazine.TopMagazineOffCommand;
import frc.robot.commands.magazine.TopMagazineOnCommand;
import frc.robot.commands.magazine.TopMagazineToggleCommand;
import frc.robot.commands.intake.IntakeDownCommand;
import frc.robot.commands.intake.IntakeUpCommand;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.commands.vision.VisionShootToggleCommand;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;

public class Autonomous {

    private final String path;
    private final SwerveDrive m_SwerveDrive;
    private final Vision m_vision;
    private final Shooter m_shooter;    
    private final Intake m_intake;
    private final Magazine m_magazine;

    private Command sequence;

    public Autonomous(SwerveDrive swerveDrive, Vision vision, Magazine magazine, Intake intake, Shooter shooter, String path) {

        this.path = path;
        m_SwerveDrive = swerveDrive;
        m_vision = vision;
        m_magazine = magazine;
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
                    sequence = sequence.andThen(new VisionShootToggleCommand(m_SwerveDrive,m_vision,m_shooter, m_magazine, true));
                    break;
                case "intakeDown":
                    sequence = sequence.andThen(new IntakeDownCommand(m_intake));
                    break;
                case "intakeUp":
                    sequence = sequence.andThen(new IntakeUpCommand(m_intake));
                    break;
                case "shoot":
                    sequence = sequence.andThen(new ShooterToggleCommand(m_shooter, 3000)).andThen(new TopMagazineToggleCommand(m_magazine, false));
                    break;
                case "topFeeder":
                    if ((boolean)e.get("direction"))
                        sequence = sequence.andThen(new TopMagazineOnCommand(m_magazine));
                    else
                        sequence = sequence.andThen(new TopMagazineOffCommand(m_magazine));
                    break;
                case "bottomFeeder":
                    if ((boolean)e.get("direction"))
                        sequence = sequence.andThen(new BottomMagazineOnCommand(m_magazine));
                    else
                        sequence = sequence.andThen(new BottomMagazineOffCommand(m_magazine));
                    break;
                case "wait":
                    sequence = sequence.andThen(new WaitCommand(4.5));
                    break;

            }
        });
    }


    public Command getCommand() {
        return sequence;
    }

}
