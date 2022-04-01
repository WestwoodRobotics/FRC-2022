package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

import java.time.Clock;

import static frc.robot.Constants.DriveConstants.*;

public class AlignLimelightRotationCommand extends CommandBase {
    private final SwerveDrive m_swerveDrive;
    private final Vision m_vision;
    private long startTime;


    public AlignLimelightRotationCommand(SwerveDrive swerveDrive, Vision vision) {
        m_swerveDrive = swerveDrive;
        m_vision = vision;

        addRequirements(swerveDrive, vision);
    }

    @Override
    public void initialize() {
        startTime = Clock.systemUTC().millis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // if (m_vision.getXOff() > 0) {
        //     m_swerveDrive.drive(0, 0, -1, false);
        // } else {
        //     m_swerveDrive.drive(0, 0, 1, false);
        // }

        m_swerveDrive.drive(0, 0, bound(m_vision.getXOff()*C_kPXVision), false);

    }

    public double bound(double input) {
        if (input < -1 * C_MAX_ANGULAR_SPEED)
            return -.3 * C_MAX_ANGULAR_SPEED;
        if (input > C_MAX_ANGULAR_SPEED)
            return .3 * C_MAX_ANGULAR_SPEED;
        return input;
    }


    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return ((Math.abs(m_vision.getXOff()) < Constants.VisionConstants.C_ACCEPTABLE_DEGREE_DISTANCE));
    }

}
