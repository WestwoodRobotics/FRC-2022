package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import java.time.Clock;

public class FeederOffCommand extends CommandBase {

    private final Feeder m_feeder;
    private long startTime;

    public FeederOffCommand(Feeder feeder) {
        m_feeder = feeder;

        addRequirements(feeder);
    }

    @Override
    public void initialize() {
        startTime = Clock.systemUTC().millis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_feeder.feederOff();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // waits 300 ms to make sure the robot is in position
        return Clock.systemUTC().millis() - startTime > 100;
    }

}
