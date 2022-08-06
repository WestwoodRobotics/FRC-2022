package frc.robot.commands.shooter;

<<<<<<< HEAD
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
=======
>>>>>>> master
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
<<<<<<< HEAD
        SmartDashboard.putBoolean("Shooter Enabled", false);
=======
>>>>>>> master
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
