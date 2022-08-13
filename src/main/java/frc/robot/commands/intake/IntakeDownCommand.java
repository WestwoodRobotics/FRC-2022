package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import java.time.Clock;

public class IntakeDownCommand extends CommandBase {

	private final Intake m_intake;
	private long startTime;

	public IntakeDownCommand(Intake intake) {
		m_intake = intake;

		addRequirements(intake);
	}

	@Override
	public void initialize() {
		startTime = Clock.systemUTC().millis();
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {

		m_intake.armDown();
		m_intake.beltOn(false);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return Clock.systemUTC().millis() - startTime > 400;
	}
}
