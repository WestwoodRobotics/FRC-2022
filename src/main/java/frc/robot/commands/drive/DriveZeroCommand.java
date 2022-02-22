package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrive;

import java.time.Clock;

public class DriveZeroCommand extends CommandBase {

    private final SwerveDrive m_swerveDrive;
    private long startTime;

    public DriveZeroCommand(SwerveDrive swerveDrive) {
        m_swerveDrive = swerveDrive;

        addRequirements(swerveDrive);
    }

    @Override
    public void initialize() {
        startTime = Clock.systemUTC().millis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_swerveDrive.zeroOut();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // waits 300 ms to make sure the robot is in position
        return Clock.systemUTC().millis() - startTime > 300;
    }

}
