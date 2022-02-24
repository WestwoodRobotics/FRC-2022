package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import static frc.robot.Constants.IntakeConstants.*;

public class Intake {

    private final CANSparkMax beltMotor = new CANSparkMax(P_BELT_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless),
                              flywheelMotor = new CANSparkMax(P_FLYWHEEL_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless),
                              pinionMotor = new CANSparkMax(P_PINION_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);

    public Intake() {}
    
    public void pushIntake(){
        pinionMotor.set(C_INTAKE_MOVE_SPEED);
    }
    
    public void pullIntake(){
        pinionMotor.set(-C_INTAKE_MOVE_SPEED);
    }
    
    public void intakeStop(){
        flywheelMotor.stopMotor();
    }
    
    public void intakeIn(){
        flywheelMotor.set(C_INTAKE_BELT_SPEED);
    }
    
    public void intakeOut(){
        flywheelMotor.set(-C_INTAKE_BELT_SPEED);
    }

    public void magIn() {
        beltMotor.set(C_INTAKE_MAG_SPEED);
    }

    public void magOut() {
        beltMotor.set(-C_INTAKE_MAG_SPEED);
    }

}
