package frc.robot;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.driveZeroCommand;
import frc.robot.subsystems.SwerveDrive;

public class Autonomous {

    private final String path;
    private final SwerveDrive m_SwerveDrive;

    public Autonomous(SwerveDrive swerveDrive, String path) {

        this.path = path;
        m_SwerveDrive = swerveDrive;

    }

    public void queue() {

        LinkedBlockingDeque<JSONObject> instructions = getFromJSON();

        instructions.iterator().forEachRemaining((e) -> {
            String command = (String) e.get("commandType");
            System.out.println(e);
            switch (command) {
                case "drive":
                    new DriveCommand(m_SwerveDrive, e).schedule(false);
                    break;
                case "zero":
                    new driveZeroCommand(m_SwerveDrive).schedule(true);
                    break;
                default:
                    new driveZeroCommand(m_SwerveDrive).schedule(true);

            }
        });
    }

    private LinkedBlockingDeque<JSONObject> getFromJSON() {

        LinkedBlockingDeque<JSONObject> output = new LinkedBlockingDeque<>();

        // parsing instructions json

        Object obj = null;
        try {
            obj = new JSONParser()
                    .parse(new FileReader(Filesystem.getDeployDirectory().getPath() + "/paths/" + path + ".json"));
        } catch (IOException | org.json.simple.parser.ParseException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }

        // typecasting obj to JSONObject
        JSONArray jsonArray = (JSONArray) obj;

        for (Object o : jsonArray) {
            output.add((JSONObject) o);
        }

        return output;

    }

}
