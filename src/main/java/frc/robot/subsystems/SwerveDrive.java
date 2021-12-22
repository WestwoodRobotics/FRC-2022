// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.SwerveConstants.C_DISTANCE_FROM_CENTER_METERS;
import static frc.robot.Constants.SwerveConstants.P_FRONT_LEFT_DRIVE;
import static frc.robot.Constants.SwerveConstants.P_FRONT_LEFT_TURN;
import static frc.robot.Constants.SwerveConstants.P_FRONT_RIGHT_DRIVE;
import static frc.robot.Constants.SwerveConstants.P_FRONT_RIGHT_TURN;
import static frc.robot.Constants.SwerveConstants.P_REAR_LEFT_DRIVE;
import static frc.robot.Constants.SwerveConstants.P_REAR_LEFT_TURN;
import static frc.robot.Constants.SwerveConstants.P_REAR_RIGHT_DRIVE;
import static frc.robot.Constants.SwerveConstants.P_REAR_RIGHT_TURN;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrive extends SubsystemBase {
  /** Creates a new SwerveDrive. */
  private final Translation2d m_frontRightLocation = new Translation2d(C_DISTANCE_FROM_CENTER_METERS,C_DISTANCE_FROM_CENTER_METERS);
  private final Translation2d m_frontLeftLocation = new Translation2d(-C_DISTANCE_FROM_CENTER_METERS,C_DISTANCE_FROM_CENTER_METERS);
  private final Translation2d m_rearLeftLocation = new Translation2d(-C_DISTANCE_FROM_CENTER_METERS,-C_DISTANCE_FROM_CENTER_METERS);
  private final Translation2d m_rearRightLocation = new Translation2d(C_DISTANCE_FROM_CENTER_METERS,-C_DISTANCE_FROM_CENTER_METERS);

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
  public SwerveDrive() {
    imu.reset();

  }

  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative)
  {
    var swerveModuleState = m_kinematics.toSwerveModuleStates(
        fieldRelative
          ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, imu.getRotation2d())
          : new ChassisSpeeds(xSpeed, ySpeed, rot));
          //start here next time 
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}