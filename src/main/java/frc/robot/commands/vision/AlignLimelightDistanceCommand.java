package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

import static frc.robot.Constants.VisionConstants.C_ACCEPTABLE_GOAL_OFFSET;
import static frc.robot.Constants.VisionConstants.C_GOAL_DISTANCE;

public class AlignLimelightDistanceCommand extends CommandBase {
    private final SwerveDrive m_swerveDrive;
    private final Vision m_vision;

    public AlignLimelightDistanceCommand(SwerveDrive swerveDrive, Vision vision) {
        m_swerveDrive = swerveDrive;
        m_vision = vision;

        addRequirements(swerveDrive, vision);
    }

    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double distance = m_vision.getDistanceFromGoal();

        if (C_GOAL_DISTANCE < distance)
            m_swerveDrive.drive(0, -1, 0, false);
        else
            m_swerveDrive.drive(0, 1, 0, false);
    }


    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(m_vision.getDistanceFromGoal() - C_GOAL_DISTANCE) < C_ACCEPTABLE_GOAL_OFFSET;
    }

}
