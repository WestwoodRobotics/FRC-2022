// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveModule extends SubsystemBase {
  /** Creates a new SwerveModule. */
  private int moduleNum;
  public final TalonFX turningMotor;
  public final TalonFX driveMotor;

  /*private Translation2d m_frontLeftLocation = new Translation2d(0, 0);
  private Translation2d m_frontRightLocation = new Translation2d(0, -0);
  private Translation2d m_backLeftLocation = new Translation2d(-0, 0);
  private Translation2d m_backRightLocation = new Translation2d(-0, -0);
  private SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
                                                m_frontLeftLocation, 
                                                m_frontRightLocation, 
                                                m_backLeftLocation, 
                                                m_backRightLocation);
  */
  public SwerveModule(int moduleNum, TalonFX turningMotor, TalonFX driveMotor) {
    this.moduleNum = moduleNum;
    this.turningMotor = turningMotor;
    this.driveMotor = driveMotor;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
