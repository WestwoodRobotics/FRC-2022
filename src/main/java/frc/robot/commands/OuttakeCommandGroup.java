package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.magazine.BottomMagazineToggleCommand;
import frc.robot.commands.magazine.TopMagazineToggleCommand;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;

public class OuttakeCommandGroup extends ParallelCommandGroup {

    Magazine m_magazine;
    Shooter m_shooter;

    public OuttakeCommandGroup(Magazine magazine, Shooter shooter) {
        m_magazine = magazine;
        m_shooter = shooter;

        addCommands(new BottomMagazineToggleCommand(m_magazine, false), new ShooterToggleCommand(m_shooter, 4000),
                new TopMagazineToggleCommand(m_magazine, false));
    }
}
