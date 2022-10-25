package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

import static frc.robot.Constants.SwerveModuleConstants.C_DISTANCE_FROM_CENTER_LENGTH;
import static frc.robot.Constants.SwerveModuleConstants.C_DISTANCE_FROM_CENTER_WIDTH;

public class AutonTrajectory {
    private TrajectoryConfig trajectoryConfig;
    private PIDController xController;
    private PIDController yController;
    private ProfiledPIDController thetaController;
    private SwerveDrive m_swerveDrive;
    private SwerveDriveKinematics kDriveKinematics;

    // https://github.com/SeanSun6814/FRC0ToAutonomous/blob/master/%236%20Swerve%20Drive%20Auto/src/main/java/frc/robot/RobotContainer.java
    public AutonTrajectory(SwerveDrive swerveDrive) {
        m_swerveDrive = swerveDrive;

        kDriveKinematics = new SwerveDriveKinematics(
                new Translation2d(C_DISTANCE_FROM_CENTER_WIDTH, C_DISTANCE_FROM_CENTER_LENGTH),
                new Translation2d(-C_DISTANCE_FROM_CENTER_WIDTH, C_DISTANCE_FROM_CENTER_LENGTH),
                new Translation2d(-C_DISTANCE_FROM_CENTER_WIDTH, -C_DISTANCE_FROM_CENTER_LENGTH),
                new Translation2d(C_DISTANCE_FROM_CENTER_WIDTH, -C_DISTANCE_FROM_CENTER_LENGTH)
        );

        trajectoryConfig = new TrajectoryConfig(
                Constants.AutonConstants.C_MAX_AUTON_SPEED,
                Constants.AutonConstants.C_MAX_AUTON_ACCEL)
                .setKinematics(kDriveKinematics);

        xController = new PIDController(1.5, 0, 0);
        yController = new PIDController(1.5, 0, 0);
        thetaController = new ProfiledPIDController(1, 0, 0, Constants.AutonConstants.kThetaControllerConstraints); // add in code
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public TrajectoryConfig getTrajectoryConfig() {
        return trajectoryConfig;
    }

    public SwerveControllerCommand generateControllerCommand(Trajectory trajectory) {
        return new SwerveControllerCommand(
                trajectory,
                m_swerveDrive::getPose,
                kDriveKinematics,
                xController,
                yController,
                thetaController,
                m_swerveDrive::setModuleStates,
                m_swerveDrive);
    }
}