package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.magazine.BottomMagazineToggleCommand;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;

public class OuttakeCommandGroup extends ParallelCommandGroup {

	Magazine m_magazine;
	Shooter m_shooter;

	public OuttakeCommandGroup (Magazine magazine, Shooter shooter) {
		m_magazine = magazine;
		m_shooter = shooter;

		addCommands(new BottomMagazineToggleCommand(m_magazine, true), new ShooterToggleCommand(m_shooter, 4500));
	}


}
