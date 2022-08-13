package frc.robot.commands.magazine;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Magazine;

public class MagazineReverseCommand extends ParallelCommandGroup {

    private final Magazine m_magazine;

    public MagazineReverseCommand(Magazine magazine) {
        m_magazine = magazine;

        addCommands(
                new TopMagazineToggleCommand(m_magazine, true),
                new BottomMagazineToggleCommand(m_magazine, true));
    }
}
