package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.magazine.TopMagazineOffCommand;
import frc.robot.commands.magazine.TopMagazineToggleCommand;
import frc.robot.commands.shooter.ShooterOffCommand;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

public class VisionShootToggleCommand extends SequentialCommandGroup {

    private final SwerveDrive m_swerveDrive;
    private final Vision m_vision;
    private final Shooter m_shooter;
    private final Magazine m_magazine;

    public static boolean wasShot = false;

    public VisionShootToggleCommand(SwerveDrive swerveDrive, Vision vision, Shooter shooter, Magazine magazine, boolean aim) {
        m_magazine = magazine;
        m_swerveDrive = swerveDrive;
        m_vision = vision;
        m_shooter = shooter;

        addRequirements(swerveDrive, vision);

        if (wasShot) {
            addCommands(new ShooterOffCommand(m_shooter), new TopMagazineOffCommand(m_magazine));
            wasShot = false;
        }
        else {
            if (aim) {
                addCommands(new VisionAlignCommand(m_swerveDrive, m_vision), new ShooterToggleCommand(m_shooter, calcPower()), new TopMagazineToggleCommand(m_magazine, false));
            } else {
                addCommands(new ShooterToggleCommand(m_shooter, calcPower()), new TopMagazineToggleCommand(m_magazine, false));
            }
            wasShot = true;
        }

    }

    private double calcPower() {
        double ty = m_vision.getY();
        return Math.pow(-2.565991 * ty, 2) + (-4.677647 * ty) + 6764;
        //return Math.pow(-2.565991 * ty, 2) + (-4.477647 * ty) + 6714; <--- ORIGINAL
        //return Math.pow(-2.565991 * ty, 2) + (-4.577647 * ty) + 6414;
    }
}
