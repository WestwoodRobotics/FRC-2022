package frc.robot.commands.intake;
import java.time.Clock;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeMagazineOutCommand extends CommandBase {

    private long startTime;
    private Intake m_intake;
    private double speed;

    public IntakeMagazineOutCommand(Intake m_intake, double speed) {
        this.m_intake = m_intake;
        this.speed = speed;
        addRequirements(m_intake);
        
    }

    @Override
    public void initialize() {
        startTime = Clock.systemUTC().millis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_intake.magOut(speed); 
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // waits 300 ms to make sure the robot is in position
        return Clock.systemUTC().millis() - startTime > 300;
    }

}
