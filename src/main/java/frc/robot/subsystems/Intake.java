// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.lang.invoke.ConstantBootstraps;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IntakeConstants.*;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  private final CANSparkMax beltMotor = new CANSparkMax(P_BELT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANSparkMax flywheelMotor = new CANSparkMax(P_FLYWHEEL_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANSparkMax pinionMotor = new CANSparkMax(P_PINION_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);

  /** Creates a new intake. */
  public Intake() {
  }

  public void pushIntake() {
    pinionMotor.set(true);
  }

  public void pullIntake() {
    intakeSol1.set(false);
  }

  public void intakeStop() {
    intakeMotor1.stopMotor();
  }

  public void intakeIn() {
    intakeMotor1.set(C_INTAKE_SPEED);
  }

  public void intakeOut() {
    intakeMotor1.set(-C_INTAKE_SPEED);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
