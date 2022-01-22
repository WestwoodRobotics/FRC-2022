// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.SwerveModuleConstants.*;
import static frc.robot.Constants.DriveConstants.*;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDrive extends SubsystemBase {
  /** Creates a new SwerveDrive. */
  private final Translation2d m_frontRightLocation = new Translation2d(C_DISTANCE_FROM_CENTER,C_DISTANCE_FROM_CENTER);
  private final Translation2d m_frontLeftLocation = new Translation2d(-C_DISTANCE_FROM_CENTER,C_DISTANCE_FROM_CENTER);
  private final Translation2d m_rearLeftLocation = new Translation2d(-C_DISTANCE_FROM_CENTER,-C_DISTANCE_FROM_CENTER);
  private final Translation2d m_rearRightLocation = new Translation2d(C_DISTANCE_FROM_CENTER,-C_DISTANCE_FROM_CENTER);

  private final TalonFX frontRightDriveMotor = new TalonFX(P_FRONT_RIGHT_DRIVE);
  private final TalonFX frontRightTurnMotor = new TalonFX(P_FRONT_RIGHT_TURN);
  private final TalonFX frontLeftDriveMotor = new TalonFX(P_FRONT_LEFT_DRIVE);
  private final TalonFX frontLeftTurnMotor = new TalonFX(P_FRONT_LEFT_TURN);
  private final TalonFX rearLeftDriveMotor = new TalonFX(P_REAR_LEFT_DRIVE);
  private final TalonFX rearLeftTurnMotor = new TalonFX(P_REAR_LEFT_TURN);
  private final TalonFX rearRightDriveMotor = new TalonFX(P_REAR_RIGHT_DRIVE);
  private final TalonFX rearRightTurnMotor = new TalonFX(P_REAR_RIGHT_TURN);

  private final SwerveModule m_frontRight = new SwerveModule(0, frontRightDriveMotor, frontRightTurnMotor, false, false);
  private final SwerveModule m_frontLeft = new SwerveModule (1, frontLeftDriveMotor, frontLeftTurnMotor, false, false);
  private final SwerveModule m_rearLeft = new SwerveModule(2, rearLeftDriveMotor, rearLeftTurnMotor, false, false);
  private final SwerveModule m_rearRight = new SwerveModule (3, rearRightDriveMotor, rearRightTurnMotor, false, false);
 
  private AHRS imu = new AHRS();

  private final SwerveDriveKinematics m_kinematics =
                new SwerveDriveKinematics(m_frontRightLocation, m_frontLeftLocation, m_rearLeftLocation, m_rearRightLocation);
  private final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(m_kinematics, imu.getRotation2d());

  public SwerveDrive() 
  {
    imu.reset();
  }

  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative)
  {
    SwerveModuleState[] swerveModuleStates = m_kinematics.toSwerveModuleStates(
        fieldRelative
          ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, imu.getRotation2d())
          : new ChassisSpeeds(xSpeed, ySpeed, rot));
          SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, C_kMAX_SPEED);
          m_frontRight.setDesiredState(swerveModuleStates[0]);
          m_frontLeft.setDesiredState(swerveModuleStates[1]);
          m_rearLeft.setDesiredState(swerveModuleStates[2]);
          m_rearRight.setDesiredState(swerveModuleStates[3]);
    
    
  }

  public void test(){
    SwerveModuleState state = new SwerveModuleState(0, Rotation2d.fromDegrees(45));
    m_frontRight.setDesiredState(state);
    m_frontLeft.setDesiredState(state);
  


    SmartDashboard.putString("testValue", "test");

  }

  public void updateOdometry()
  {
    m_odometry.update(
      imu.getRotation2d(), 
      m_frontRight.getState(),
      m_frontLeft.getState(),
      m_rearLeft.getState(),
      m_rearRight.getState()
      );
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}