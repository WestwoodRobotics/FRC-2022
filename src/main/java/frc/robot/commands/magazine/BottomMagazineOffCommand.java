package frc.robot.commands.magazine;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;

public class BottomMagazineOffCommand extends CommandBase {

    private final Magazine m_magazine;

    public BottomMagazineOffCommand (Magazine magazine) {
        m_magazine = magazine;

        addRequirements(magazine);
    }

    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_magazine.bottomFeederOff();
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
