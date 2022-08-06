package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Feeder;

public class FeederReverseCommand extends ParallelCommandGroup {

    private final Feeder m_feeder;

    public FeederReverseCommand(Feeder feeder) {
        m_feeder = feeder;

        addCommands(new TopFeederToggleCommand(m_feeder, true), new BottomFeederToggleCommand(m_feeder, true));
    }


}
