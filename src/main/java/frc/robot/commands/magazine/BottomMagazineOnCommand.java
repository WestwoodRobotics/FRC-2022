package frc.robot.commands.magazine;

import static frc.robot.Constants.MagazineConstants.*;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;

public class BottomMagazineOnCommand extends CommandBase {

	private final Magazine m_magazine;

	public BottomMagazineOnCommand(Magazine magazine) {
		m_magazine = magazine;

		addRequirements(magazine);
	}

	@Override
	public void initialize() {
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		m_magazine.bottomMagazineOn(C_BELT_MAX_SPEED);
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
