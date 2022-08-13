package frc.robot.commands.intake;

import static frc.robot.Constants.MagazineConstants.C_BELT_MAX_SPEED;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import java.time.Clock;

public class IntakeInCommand extends CommandBase {

    private final Intake m_intake;
    private final Magazine m_magazine;
    private final XboxController controller;

    private long time;

    public IntakeInCommand(Intake intake, XboxController controller, Magazine magazine) {

        m_intake = intake;
        m_magazine = magazine;
        this.controller = controller;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        time = Clock.systemUTC().millis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (controller.getRightTriggerAxis() > 0.5) {

            m_magazine.bottomMagazineOn(C_BELT_MAX_SPEED);
            // m_magazine.topMagazineOn(-C_BELT_MAX_SPEED);

            // m_intake.armDown();
            m_intake.beltOn(false);

            time = Clock.systemUTC().millis();
        } else {
            // m_intake.armUp(Clock.systemUTC().millis() - time < 600);

            if (Clock.systemUTC().millis() - time > 600) {
                m_intake.beltOff();
                if (Clock.systemUTC().millis() - time < 640) m_magazine.bottomMagazineOff();
                // m_magazine.topMagazineOff();
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intake.brakeMode(true);
        m_intake.beltOff();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
