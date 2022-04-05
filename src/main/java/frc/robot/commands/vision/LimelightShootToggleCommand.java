package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.feeder.TopFeederOffCommand;
import frc.robot.commands.feeder.TopFeederToggleCommand;
import frc.robot.commands.shooter.ShooterOffCommand;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

import javax.print.DocFlavor;

public class LimelightShootToggleCommand extends SequentialCommandGroup {

    private final SwerveDrive m_swerveDrive;
    private final Vision m_vision;
    private final Shooter m_shooter;
    private final Feeder m_feeder;

    public static boolean wasShot = false;

    public LimelightShootToggleCommand(SwerveDrive swerveDrive, Vision vision, Shooter shooter, Feeder feeder, boolean aim) {
        m_feeder = feeder;
        m_swerveDrive = swerveDrive;
        m_vision = vision;
        m_shooter = shooter;

        addRequirements(swerveDrive, vision);

        if (wasShot) {
            addCommands(new ShooterOffCommand(m_shooter), new TopFeederOffCommand(m_feeder));
            wasShot = false;
        }
        else {
            if (aim) {
                addCommands(new AlignLimelightRotationCommand(m_swerveDrive, m_vision), new ShooterToggleCommand(m_shooter, calcPower()), new TopFeederToggleCommand(m_feeder, false));
            } else {
                addCommands(new ShooterToggleCommand(m_shooter, calcPower()), new TopFeederToggleCommand(m_feeder, false));
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
