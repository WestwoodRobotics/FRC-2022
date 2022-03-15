package frc.robot.commands.hangar;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hangar;
import static frc.robot.Constants.HangarConstants.*;

public class HangarStaticHooksCommand extends CommandBase {

    private final Hangar m_hangar;
    private final double degrees;
    private final double startRotation;

    public HangarStaticHooksCommand(Hangar hangar, double degrees) {
        m_hangar = hangar;
        this.degrees = degrees;
        this.startRotation = hangar.getClawRotation();

        addRequirements(hangar);
    }

    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (m_hangar.getClawRotation() - degrees > 0)
            m_hangar.setClawMotorSpeed(-0.3);
        else 
            m_hangar.setClawMotorSpeed(0.3);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_hangar.setClawMotorSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_hangar.getClawRotation() > degrees;
    }

}
