package frc.robot.commands.intake;

import java.time.Clock;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Intake;
import static frc.robot.Constants.MagazineConstants.*;

public class IntakeConstantControlCommand extends CommandBase {

    private final Intake m_intake;
    private final Magazine m_magazine;
    private final XboxController controller;

    private long time;

    public IntakeConstantControlCommand(Intake intake, XboxController controller, Magazine magazine) {

        m_intake = intake;
        m_magazine = magazine;
        this.controller = controller;

        addRequirements(intake);

    }

    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (controller.getRightTriggerAxis() > 0.5) {

            m_magazine.bottomMagazineOn(C_BELT_MAX_SPEED);

            m_intake.armDown();
            m_intake.beltOn(false);

            time = Clock.systemUTC().millis();
        } else {
            m_intake.armUp(Clock.systemUTC().millis() - time < 1400);

            if (Clock.systemUTC().millis() - time > 1400) {
                m_intake.beltOff();
                if (Clock.systemUTC().millis() - time < 1540)
                    m_magazine.bottomMagazineOff();
            }

        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_intake.brakeMode(true);
        m_intake.armUpEnd();
        m_intake.beltOff();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }




}
