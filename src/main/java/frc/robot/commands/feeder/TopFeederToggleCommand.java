package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class TopFeederToggleCommand extends CommandBase {

    private final Feeder m_feeder;
    private final boolean reverse;

    public TopFeederToggleCommand(Feeder feeder, boolean reverse) {
        m_feeder = feeder;
        this.reverse = reverse;

    }

    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (reverse) {
            m_feeder.topToggleState(-1);
        } else {
            m_feeder.topToggleState(1);
        }
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
