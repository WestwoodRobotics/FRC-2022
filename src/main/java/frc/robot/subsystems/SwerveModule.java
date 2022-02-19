// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.SwerveModuleConstants.C_DRIVE_kA;
import static frc.robot.Constants.SwerveModuleConstants.C_DRIVE_kD;
import static frc.robot.Constants.SwerveModuleConstants.C_DRIVE_kI;
import static frc.robot.Constants.SwerveModuleConstants.C_DRIVE_kP;
import static frc.robot.Constants.SwerveModuleConstants.C_DRIVE_kS;
import static frc.robot.Constants.SwerveModuleConstants.C_DRIVE_kV;
import static frc.robot.Constants.SwerveModuleConstants.C_MAX_VOLTAGE;
import static frc.robot.Constants.SwerveModuleConstants.C_TURN_kA;
import static frc.robot.Constants.SwerveModuleConstants.C_TURN_kD;
import static frc.robot.Constants.SwerveModuleConstants.C_TURN_kI;
import static frc.robot.Constants.SwerveModuleConstants.C_TURN_kP;
import static frc.robot.Constants.SwerveModuleConstants.C_TURN_kS;
import static frc.robot.Constants.SwerveModuleConstants.C_TURN_kV;
import static frc.robot.Constants.SwerveModuleConstants.C_DRIVE_ENCODER_DISTANCE_PER_PULSE;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class SwerveModule extends SubsystemBase 
{ 
  /** Creates a new SwerveModule. */
  private int moduleNum;
  public final TalonFX m_turningMotor;
  public final TalonFX m_driveMotor;
  
  public final CANCoder e_Encoder;
 
  private double driveMotorOutput;
  private double turningMotorOutput;

  private final PIDController driveMotorPID = new PIDController(C_DRIVE_kP, C_DRIVE_kI, C_DRIVE_kD);
  private final PIDController turnMotorPID =
                                                  new PIDController(C_TURN_kP, C_TURN_kI, C_TURN_kD);

  private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(C_DRIVE_kS, C_DRIVE_kV,C_DRIVE_kA);
  private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(C_TURN_kS, C_TURN_kV, C_TURN_kA);

  private boolean drive_inverted;
  private boolean turn_inverted;
  
  Pose2d swerveModulePose = new Pose2d();
  //constructor 
  public SwerveModule(int moduleNum, TalonFX driveMotor, TalonFX turningMotor, CANCoder encoder, boolean invertDrive, boolean invertTurn) 
  {
    this.moduleNum = moduleNum;
    m_driveMotor = driveMotor;
    m_turningMotor = turningMotor;
    e_Encoder = encoder;
    
    //reset encoders
    resetEncoders();

    // m_driveMotor.setInverted(invertDrive);
    //m_turningMotor.setInverted(invertTurn);

    this.drive_inverted = invertDrive;
    this.turn_inverted = invertTurn;

    driveMotor.setNeutralMode(NeutralMode.Brake);
    turningMotor.setNeutralMode(NeutralMode.Coast);

    driveMotor.clearStickyFaults();
    turningMotor.clearStickyFaults();

    //set the PID controller input range form -pi to pi
    turnMotorPID.enableContinuousInput(-Math.PI, Math.PI);

    // Set I term bounds of drive PID to full motor output range.
    driveMotorPID.setIntegratorRange(-C_MAX_VOLTAGE, C_MAX_VOLTAGE);
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
    //return 2*Math.PI * m_turningMotor.getSelectedSensorPosition()/(Constants.SwerveModuleConstants.C_kENCODER_CPR * Constants.SwerveModuleConstants.C_kTURNING_MOTOR_GEAR_RATIO);
    //System.out.println(this.moduleNum + " " + this.e_Encoder.getAbsolutePosition());
    return Math.toRadians(e_Encoder.getAbsolutePosition());
    // UNTESTED, MIGHT CAUSE PROBLEMO
  }

  public double getTurnAngle() 
  {
    return Units.radiansToDegrees(getTurningRadians());
  }

  public double getVelocity()
  {
    return m_driveMotor.getSelectedSensorVelocity() * C_DRIVE_ENCODER_DISTANCE_PER_PULSE * 10;
  }

  public SwerveModuleState getState()
  {
    return new SwerveModuleState(getVelocity(), new Rotation2d(getTurningRadians()));
  }

  public void setDesiredState(SwerveModuleState state)
  {
    SwerveModuleState outputState = SwerveModuleState.optimize(state, new Rotation2d(getTurningRadians()));
    //SwerveModuleState outputState = state;
    
    double drive_vel = getVelocity();
    driveMotorOutput = driveMotorPID.calculate(drive_vel, outputState.speedMetersPerSecond);
    turningMotorOutput = turnMotorPID.calculate(getTurningRadians(), outputState.angle.getRadians());
    
    double driveFeedforward = m_driveFeedforward.calculate(outputState.speedMetersPerSecond);
    double turnFeedforward = m_turnFeedforward.calculate(outputState.angle.getRadians());
    //double turnFeedforward = m_turnFeedforward.calculate(Math.PI);

    m_driveMotor.set(ControlMode.PercentOutput, (this.drive_inverted ? -1 : 1) * (driveFeedforward + driveMotorOutput) / C_MAX_VOLTAGE);
    m_turningMotor.set(ControlMode.PercentOutput, (this.turn_inverted ? -1 : 1) * (turningMotorOutput + turnFeedforward) / C_MAX_VOLTAGE);


    SmartDashboard.putString("time ms", "" + System.currentTimeMillis());
    SmartDashboard.putString("speed ms", "" + outputState.speedMetersPerSecond);
    SmartDashboard.putString("drive vel", ""+ drive_vel);
    SmartDashboard.putString("drive motor output", "" + driveMotorOutput);
    SmartDashboard.putString("speed drive pct off", "" + (drive_vel/outputState.speedMetersPerSecond)*100 + "%");


    // System.out.println(
    //   System.currentTimeMillis() + ", " +
    //   outputState.speedMetersPerSecond+ ", " +
    //   drive_vel + ", " +
    //   driveMotorOutput);
  }

  public void setPercentOutput(double speed) 
  {
    // m_driveMotor.set(ControlMode.PercentOutput, speed);
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
