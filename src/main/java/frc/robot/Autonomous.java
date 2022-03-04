package frc.robot;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.drive.DriveCommand;
import frc.robot.commands.drive.DriveZeroCommand;
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

    private Command sequence;

    public Autonomous(SwerveDrive swerveDrive, Vision vision, String path) {

        this.path = path;
        m_SwerveDrive = swerveDrive;
        m_vision = vision;

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
                case "intake":
                case "zero":
                    sequence = sequence.andThen(new DriveZeroCommand(m_SwerveDrive));
                    break;
            }
        });        
    }

}
