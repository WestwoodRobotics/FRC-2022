package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

public class AlignLimelightCommand extends SequentialCommandGroup {

    private final SwerveDrive m_swerveDrive;
    private final Vision m_vision;

    public AlignLimelightCommand(SwerveDrive swerveDrive, Vision vision) {
        m_swerveDrive = swerveDrive;
        m_vision = vision;

        addRequirements(swerveDrive, vision);

        addCommands(new AlignLimelightRotationCommand(m_swerveDrive, m_vision), new AlignLimelightDistanceCommand(m_swerveDrive, m_vision));
    }
}
