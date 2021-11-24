// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.SwerveConstants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class SwerveModule extends SubsystemBase {
  /** Creates a new SwerveModule. */
  private int moduleNum;
  public final TalonFX turningMotor;
  public final TalonFX driveMotor;

  private final PIDController driveMotorPID = new PIDController(C_SWERVE_kP, C_SWERVE_kI, C_SWERVE_kD);
  private final ProfiledPIDController turnMotorPID =
                                                  new ProfiledPIDController(C_SWERVE_kP, C_SWERVE_kI, C_SWERVE_kD, 
                                                  new TrapezoidProfile.Constraints(C_kMaxMotorAngularSpeed, C_kMaxMotorAngularAcceleration));

  private final SimpleMotorFeedforward driveFeedforward = new SimpleMotorFeedforward(C_SWERVE_kA, C_SWERVE_kS, C_SWERVE_kV);
  private final SimpleMotorFeedforward turnFeedforward = new SimpleMotorFeedforward(C_SWERVE_kA, C_SWERVE_kS, C_SWERVE_kV);
  
  //constructor 
  public SwerveModule(int moduleNum, TalonFX driveMotor, TalonFX turningMotor, boolean invertDrive, boolean invertTurn) {
    this.moduleNum = moduleNum;
    this.driveMotor = driveMotor;
    this.turningMotor = turningMotor;
    
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
    turningMotor.setSelectedSensorPosition(0);
    driveMotor.setSelectedSensorPosition(0);
  }

  


  /*public Rotation2d getHeading()
  {
    //return new Rotaion2d ();
  }*/

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
