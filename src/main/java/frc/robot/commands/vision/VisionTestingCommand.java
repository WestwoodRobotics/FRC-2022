package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class VisionTestingCommand extends SequentialCommandGroup {

    private final Vision m_vision;
    private final Shooter m_shooter;

    public VisionTestingCommand(Vision vision, Shooter shooter) {
        m_vision = vision;
        m_shooter = shooter;

        addRequirements(vision);
    }

    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is sch eduled.
    @Override
    public void execute() {

        SmartDashboard.putString("x", "" + m_vision.getXOff());
        SmartDashboard.putString("y", "" + m_vision.getY());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
