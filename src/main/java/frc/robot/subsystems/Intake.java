package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import static frc.robot.Constants.IntakeConstants.*;

public class Intake {

    private final CANSparkMax beltMotor = new CANSparkMax(P_BELT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless),
                              flywheelMotor = new CANSparkMax(P_FLYWHEEL_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless),
                              pinionMotor = new CANSparkMax(P_PINION_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);

    public Intake() {
    }

    //TODO EXTEND INTAKE, RETRACT INTAKE, BRING BALL FORWARD, GRAB BALL (COMMANDS?)

}
