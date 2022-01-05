// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.SwerveModuleConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class SwerveModule extends SubsystemBase 
{
  /** Creates a new SwerveModule. */
  private int moduleNum;
  public final TalonFX m_turningMotor;
  public final TalonFX m_driveMotor;
 
  private double driveMotorOutput;
  private double turningMotorOutput;

  private final PIDController driveMotorPID = new PIDController(C_SWERVE_kP, C_SWERVE_kI, C_SWERVE_kD);
  private final ProfiledPIDController turnMotorPID =
                                                  new ProfiledPIDController(C_SWERVE_kP, C_SWERVE_kI, C_SWERVE_kD, 
                                                  new TrapezoidProfile.Constraints(C_kMAX_MOTOR_ANGULAR_SPEED, C_kMAX_MOTOR_ANGULAR_ACCELERATION));

  private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(C_SWERVE_kA, C_SWERVE_kS, C_SWERVE_kV);
  private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(C_SWERVE_kA, C_SWERVE_kS, C_SWERVE_kV);
  
  Pose2d swerveModulePose = new Pose2d();
  //constructor 
  public SwerveModule(int moduleNum, TalonFX driveMotor, TalonFX turningMotor, boolean invertDrive, boolean invertTurn) 
  {
    this.moduleNum = moduleNum;
    m_driveMotor = driveMotor;
    m_turningMotor = turningMotor;
    
    //reset encoders
    driveMotor.setSelectedSensorPosition(0);
    turningMotor.setSelectedSensorPosition(0);

    driveMotor.setInverted(invertDrive);
    turningMotor.setInverted(invertTurn);

    driveMotor.setNeutralMode(NeutralMode.Brake);
    turningMotor.setNeutralMode(NeutralMode.Brake);

    //set the PID controller input range form -pi to pi
    turnMotorPID.enableContinuousInput(-Math.PI, Math.PI);
  }

  //set encoder position of both motors to 0
  public void resetEncoders()
  {
    m_turningMotor.setSelectedSensorPosition(0);
    m_driveMotor.setSelectedSensorPosition(0);
  }

  public Rotation2d getHeading()
  {
    return new Rotation2d(getTurningRadians());
  }

  public double getTurningRadians() 
  {
      return m_turningMotor.getSelectedSensorPosition() * C_kTURNING_MOTOR_GEAR_RATIO;
    
  }

  public double getTurnAngle() 
  {
    return Units.radiansToDegrees(getTurningRadians());
  }

  public double getVelocity()
  {
    return m_driveMotor.getSelectedSensorVelocity() * C_kDRIVE_ENCODER_DISTANCE_PER_PULSE * 10;
  }

  public SwerveModuleState getState()
  {
    return new SwerveModuleState(getVelocity(), new Rotation2d(getTurningRadians()));
  }

  public void setDesiredState(SwerveModuleState state)
  {
    SwerveModuleState outputState = SwerveModuleState.optimize(state, new Rotation2d(getTurningRadians()));

    driveMotorOutput = driveMotorPID.calculate(getVelocity(), outputState.speedMetersPerSecond);
    turningMotorOutput = turnMotorPID.calculate(getTurnAngle(), outputState.angle.getRadians());

    double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);
    double turnFeedforward = m_turnFeedforward.calculate(turnMotorPID.getSetpoint().velocity);

    m_driveMotor.set(ControlMode.PercentOutput, driveMotorOutput + driveFeedforward);
    m_turningMotor.set(ControlMode.PercentOutput, turningMotorOutput);
  }

  public void setPercentOutput(double speed) 
  {
    m_driveMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setBrakeMode(boolean mode) 
  { // True is brake, false is coast
    m_driveMotor.setNeutralMode(mode ? NeutralMode.Brake : NeutralMode.Coast);
    m_turningMotor.setNeutralMode(NeutralMode.Brake);
  }
  
  public Pose2d getPose() 
  {
    return swerveModulePose;
  }

  public void setPose(Pose2d pose) 
  {
    swerveModulePose = pose;
  }

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
  }
}