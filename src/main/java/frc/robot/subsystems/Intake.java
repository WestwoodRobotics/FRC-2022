package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.C_INTAKE_BELT_SPEED;
import static frc.robot.Constants.IntakeConstants.C_INTAKE_MAG_SPEED;
import static frc.robot.Constants.IntakeConstants.C_INTAKE_MOVE_SPEED;
import static frc.robot.Constants.IntakeConstants.P_BELT_MOTOR_PORT;
import static frc.robot.Constants.IntakeConstants.P_FLYWHEEL_MOTOR_PORT;
import static frc.robot.Constants.IntakeConstants.P_PINION_MOTOR_PORT;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final CANSparkMax beltMotor = new CANSparkMax(P_BELT_MOTOR_PORT, MotorType.kBrushless),
                              intakeMotor = new CANSparkMax(P_FLYWHEEL_MOTOR_PORT, MotorType.kBrushless);

    public Intake() {}
    
    public void intakeStop(){
        intakeMotor.stopMotor();
    }
    
    public void intakeIn(double speed) {
        intakeMotor.set(speed);
        // Add conversion to RPM? Currently takes 1.0 to -1.0
    }
    
    public void intakeOut(double speed) {
        intakeMotor.set(-speed);
        // Add conversion to RPM? Currently takes 1.0 to -1.0
    }

    public void magIn(double speed) {
        beltMotor.set(speed);
         // Add conversion to RPM? Currently takes 1.0 to -1.0
    }

    public void magOut(double speed) {
        beltMotor.set(-speed);
        // Add conversion to RPM? Currently takes 1.0 to -1.0
    }

}
