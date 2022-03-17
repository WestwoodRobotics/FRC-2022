package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import java.time.Clock;

public class BottomFeederOnCommand extends CommandBase {

    private final Feeder m_feeder;

    public BottomFeederOnCommand(Feeder feeder) {
        m_feeder = feeder;

        addRequirements(feeder);
    }

    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_feeder.bottomFeederOn();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }

}
