package frc.robot.commands.magazine;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;

public class TopMagazineToggleCommand extends CommandBase {

    private final Magazine m_magazine;
    private final boolean reverse;

    public TopMagazineToggleCommand(Magazine magazine, boolean reverse) {
        m_magazine = magazine;
        this.reverse = reverse;
    }

    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // if (reverse) {
        //     m_magazine.topToggleState(-1);
        // } else {
        //     m_magazine.topToggleState(1);
        // }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
