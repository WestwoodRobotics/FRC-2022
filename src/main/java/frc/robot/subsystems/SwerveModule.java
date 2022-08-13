// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.SwerveModuleConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Conversions;

public class SwerveModule extends SubsystemBase {
    /** Creates a new SwerveModule. */
    private final int moduleNum;

    private ShuffleboardTab tab;

    public final TalonFX m_turningMotor;
    public final TalonFX m_driveMotor;

    public final CANCoder e_Encoder;

    private double driveMotorOutput;
    private double turningMotorOutput;

    public final PIDController driveMotorPID;
    public final PIDController turnMotorPID;

    public final SimpleMotorFeedforward m_driveFeedforward;

    private final boolean drive_inverted;
    private final boolean turn_inverted;
    private double lastAngle;

    Pose2d swerveModulePose = new Pose2d();

    // constructor
    public SwerveModule(
            int moduleNum,
            TalonFX driveMotor,
            TalonFX turningMotor,
            CANCoder encoder,
            boolean invertDrive,
            boolean invertTurn,
            PIDController driveMotorPID,
            PIDController turnMotorPID,
            SimpleMotorFeedforward feedforward) {
        this.moduleNum = moduleNum;

        m_driveMotor = driveMotor;
        m_turningMotor = turningMotor;
        e_Encoder = encoder;

        m_driveFeedforward = feedforward;
        this.driveMotorPID = driveMotorPID;
        this.turnMotorPID = turnMotorPID;

        // reset encoders
        resetEncoders();

        this.drive_inverted = invertDrive;
        this.turn_inverted = invertTurn;

        driveMotor.setNeutralMode(NeutralMode.Coast);
        driveMotor.clearStickyFaults();

        turningMotor.setNeutralMode(NeutralMode.Brake);
        turningMotor.clearStickyFaults();

        TalonFXConfiguration swerveAngleFXConfig = new TalonFXConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit =
                new SupplyCurrentLimitConfiguration(true, 20, 30, 0.1);

        // put in constants later
        swerveAngleFXConfig.slot0.kP = .6;
        swerveAngleFXConfig.slot0.kI = 0.0;
        swerveAngleFXConfig.slot0.kD = 12;
        swerveAngleFXConfig.slot0.kF = 0.0;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;
        swerveAngleFXConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;

        m_turningMotor.configFactoryDefault();
        m_turningMotor.configAllSettings(swerveAngleFXConfig);
        m_turningMotor.setInverted(invertTurn);
        m_turningMotor.setNeutralMode(NeutralMode.Coast);
        resetToAbsolute();

        // Set I term bounds of drive PID to full motor output range.
        driveMotorPID.setIntegratorRange(-C_MAX_VOLTAGE, C_MAX_VOLTAGE);
    }

    private void resetToAbsolute() {
        double offset = 0.0;
        // double absolutePosition =
        // Conversions.degreesToFalcon(Rotation2d.fromDegrees(e_Encoder.getAbsolutePosition())
        //  .getDegrees() - offset, Constants.SwerveModuleConstants.C_TURNING_MOTOR_GEAR_RATIO);
        double absolutePosition = 0;
        m_turningMotor.setSelectedSensorPosition(absolutePosition);
        lastAngle = 0.0;
    }

    // set encoder position of both motors to 0
    public void resetEncoders() {
        m_turningMotor.setSelectedSensorPosition(0);
        m_driveMotor.setSelectedSensorPosition(0);
    }

    public double getVelocity() {
        return m_driveMotor.getSelectedSensorVelocity() * C_DRIVE_ENCODER_DISTANCE_PER_PULSE * 10;
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
                getVelocity(),
                Rotation2d.fromDegrees(
                        Conversions.falconToDegrees(
                                m_turningMotor.getSelectedSensorPosition(),
                                C_TURNING_MOTOR_GEAR_RATIO)));
    }

    public void setDesiredState(SwerveModuleState state) // ballsssssss in
            {

        state.speedMetersPerSecond = state.speedMetersPerSecond * 204800 / 6.12;

        SwerveModuleState outputState =
                SwerveModuleState.optimize(state, new Rotation2d(lastAngle));

        double drive_vel = getVelocity();
        driveMotorOutput = driveMotorPID.calculate(drive_vel, outputState.speedMetersPerSecond);

        double driveFeedforward = m_driveFeedforward.calculate(outputState.speedMetersPerSecond);

        m_driveMotor.set(
                ControlMode.PercentOutput,
                (this.drive_inverted ? -1 : 1) * (driveFeedforward + driveMotorOutput));
        m_turningMotor.set(
                ControlMode.Position,
                Conversions.degreesToFalcon(
                        outputState.angle.getDegrees(), C_TURNING_MOTOR_GEAR_RATIO));
        System.out.println(m_turningMotor.getClosedLoopError());
        lastAngle = outputState.angle.getRadians();
        // testing the correct motor output
        // System.out.println(
        // System.currentTimeMillis() + ", " +
        // outputState.speedMetersPerSecond+ ", " +
        // drive_vel + ", " +
        // driveMotorOutput);
    }

    public void setBrakeMode(boolean mode) { // True is brake, false is coast
        m_driveMotor.setNeutralMode(mode ? NeutralMode.Brake : NeutralMode.Coast);
        m_turningMotor.setNeutralMode(NeutralMode.Brake);
    }

    public Pose2d getPose() {
        return swerveModulePose;
    }

    public void setPose(Pose2d pose) {
        swerveModulePose = pose;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
