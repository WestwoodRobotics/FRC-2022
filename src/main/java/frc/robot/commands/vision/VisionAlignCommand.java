package frc.robot.commands.vision;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

import java.time.Clock;

import static frc.robot.Constants.DriveConstants.*;

public class VisionAlignCommand extends CommandBase {
    private final SwerveDrive m_swerveDrive;
    private final Vision m_vision;
    private double averageXOff;
    private long startTime;
    private int count = 0;


    public VisionAlignCommand(SwerveDrive swerveDrive, Vision vision) {
        m_swerveDrive = swerveDrive;
        m_vision = vision;

        addRequirements(swerveDrive, vision);
    }

    @Override
    public void initialize() {
        startTime = Clock.systemUTC().millis();
        averageXOff = m_vision.getXOff();
<<<<<<< HEAD:src/main/java/frc/robot/commands/vision/VisionAlignCommand.java
=======
        count++;
>>>>>>> master:src/main/java/frc/robot/commands/vision/AlignLimelightRotationCommand.java
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

<<<<<<< HEAD:src/main/java/frc/robot/commands/vision/VisionAlignCommand.java
        double kPOff = Math.abs(m_vision.getY() - 20) + 10;
        double kP = Math.PI / kPOff * (m_vision.getXOff() < 0 ? -1 : 1);

=======
        count++;

        double kPOff = Math.abs(m_vision.getY() - 20) + 10;
        double kP = Math.PI / kPOff * (m_vision.getXOff() < 0 ? -1 : 1);
>>>>>>> master:src/main/java/frc/robot/commands/vision/AlignLimelightRotationCommand.java
        m_swerveDrive.drive(0, 0, bound((m_vision.getXOff())*C_kPXVision) + kP, false);

    }

    public double bound(double input) {
        if (input < -.3 * C_MAX_ANGULAR_SPEED)
<<<<<<< HEAD:src/main/java/frc/robot/commands/vision/VisionAlignCommand.java
            return -.3 * C_MAX_ANGULAR_SPEED;
        if (input > .3 * C_MAX_ANGULAR_SPEED)
            return .3 * C_MAX_ANGULAR_SPEED;
=======
            return -.1 * C_MAX_ANGULAR_SPEED;
        if (input > .3 * C_MAX_ANGULAR_SPEED)
            return .1 * C_MAX_ANGULAR_SPEED;
>>>>>>> master:src/main/java/frc/robot/commands/vision/AlignLimelightRotationCommand.java
        return input;
    }


    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_swerveDrive.drive(0,0,0, false);
<<<<<<< HEAD:src/main/java/frc/robot/commands/vision/VisionAlignCommand.java
=======
        System.out.println("END");
>>>>>>> master:src/main/java/frc/robot/commands/vision/AlignLimelightRotationCommand.java
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
<<<<<<< HEAD:src/main/java/frc/robot/commands/vision/VisionAlignCommand.java
        return (Math.abs(m_vision.getXOff()) < Constants.VisionConstants.C_ACCEPTABLE_DEGREE_DISTANCE ) || Clock.systemUTC().millis() - startTime > 3000; /// (Math.abs(m_vision.getY() - 20)/40)*4
=======
        return (Math.abs(m_vision.getXOff()) < Constants.VisionConstants.C_ACCEPTABLE_DEGREE_DISTANCE ); /// (Math.abs(m_vision.getY() - 20)/40)*4
>>>>>>> master:src/main/java/frc/robot/commands/vision/AlignLimelightRotationCommand.java
    }

}
