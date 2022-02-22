package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrive;
import org.json.simple.JSONObject;

import java.time.Clock;

public class DriveCommand extends CommandBase {

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final SwerveDrive m_swerveDrive;

    private final JSONObject command;

    private final long runTime;
    private final double xSpeed;
    private final double ySpeed;
    private final double rotSpeed;

    private long startTime;

    public DriveCommand(SwerveDrive swerveDrive, JSONObject command) {
        m_swerveDrive = swerveDrive;
        this.command = command;

        this.runTime = (long) command.get("time");
        this.xSpeed = Double.parseDouble(command.get("xspeed")+"");
        this.ySpeed = Double.parseDouble(command.get("yspeed")+"");
        this.rotSpeed = Double.parseDouble(command.get("rotspeed")+"");

        addRequirements(swerveDrive);
    }

    @Override
    public void initialize() {
        startTime = Clock.systemUTC().millis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_swerveDrive.drive(xSpeed, ySpeed, rotSpeed, false);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Clock.systemUTC().millis() - startTime > runTime;
    }
}
