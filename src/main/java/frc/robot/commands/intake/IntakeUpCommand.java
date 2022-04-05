package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

import java.time.Clock;

public class IntakeUpCommand extends CommandBase {

    private final Intake m_intake;
    private boolean isArmUp;
    private long startTime;

    public IntakeUpCommand(Intake intake) {
        m_intake = intake;

        addRequirements(intake);
    }

    @Override
    public void initialize() 
    {
        startTime = Clock.systemUTC().millis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_intake.armUp(true);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intake.beltOff();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Clock.systemUTC().millis() - startTime > 1400;
    }

}
