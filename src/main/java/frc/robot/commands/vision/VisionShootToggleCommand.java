package frc.robot.commands.vision;

import static frc.robot.Constants.ShooterConstants.*;

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

    public VisionShootToggleCommand(
            SwerveDrive swerveDrive, Vision vision, Shooter shooter, Magazine magazine, boolean aim) {
        m_magazine = magazine;
        m_swerveDrive = swerveDrive;
        m_vision = vision;
        m_shooter = shooter;

        addRequirements(swerveDrive, vision);

        System.out.println("was shot" + wasShot);

        if (wasShot) {
            System.out.println("Cancelling shooter");
            addCommands(new ShooterOffCommand(m_shooter), new TopMagazineOffCommand(m_magazine));
            wasShot = false;
        } else {
            if (aim) {
                addCommands(
                        new VisionAlignCommand(m_swerveDrive, m_vision),
                        new ShooterToggleCommand(m_shooter, calcPower()),
                        new TopMagazineToggleCommand(m_magazine, false));
            } else {
                addCommands(
                        new ShooterToggleCommand(m_shooter, calcPower()),
                        new TopMagazineToggleCommand(m_magazine, false));
            }
            wasShot = true;
        }
    }

    private double calcPower() {
        double ty = m_vision.getY();
        double power = 0;

        if (ty > C_SHOT_MAP[0][0]) return C_SHOT_MAP[0][1];

        if (ty < C_SHOT_MAP[C_SHOT_MAP.length - 1][0]) return C_SHOT_MAP[C_SHOT_MAP.length - 1][1];

        for (int i = 1; i < C_SHOT_MAP.length; i++) {

            System.out.println("LVL 1." + i);
            System.out.println(C_SHOT_MAP[i - 1][0] + ">" + ty);

            if (C_SHOT_MAP[i - 1][0] > ty && ty > C_SHOT_MAP[i][0]) {

                System.out.println("LVL 2." + ty);

                double slope = (C_SHOT_MAP[i - 1][1] - C_SHOT_MAP[i][1]) / (C_SHOT_MAP[i - 1][0] - C_SHOT_MAP[i][0]);

                System.out.println("slope " + slope);
                System.out.println("i0 = " + C_SHOT_MAP[i][0]);

                power = -1 * ((-1 * C_SHOT_MAP[i][1]) + slope * (ty - C_SHOT_MAP[i][0]));
            }
        }

        return power;
        // return Math.pow(-2.565991 * ty, 2) + (-4.477647 * ty) + 6714; <--- ORIGINAL
        // return Math.pow(-2.565991 * ty, 2) + (-4.577647 * ty) + 6414;
    }
}
