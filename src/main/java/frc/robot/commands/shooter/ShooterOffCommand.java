package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterOffCommand extends CommandBase {

    private final Shooter m_shooter;

    public ShooterOffCommand(Shooter shooter) {
        m_shooter = shooter;

        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        m_shooter.shooterOff();
        SmartDashboard.putBoolean("Shooter Enabled", false);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
