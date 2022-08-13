package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    // private ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
    // initializing motors
    private final CANSparkMax intakeArm = new CANSparkMax(P_INTAKE_ARM, MotorType.kBrushless);
    private final CANSparkMax intakeBelt = new CANSparkMax(P_INTAKE_BELT, MotorType.kBrushless);

    // PID might not be needed lol

    // Constructor
    public Intake() {

        intakeArm.setIdleMode(IdleMode.kBrake);
        intakeBelt.setIdleMode(IdleMode.kCoast);

        intakeArm.getEncoder().setPosition(0);
    }

    public double getIntake() {
        return intakeArm.getEncoder().getPosition();
    }

    public void beltOn(boolean reverse) {
        if (!reverse) intakeBelt.setVoltage(C_INTAKE_BELT_VOLTAGE);
        else intakeBelt.set(-C_INTAKE_BELT_VOLTAGE);
    }

    public void beltOff() {
        intakeBelt.set(0);
    }

    public void brakeMode(boolean brake) {
        if (brake) {
            intakeArm.setIdleMode(IdleMode.kBrake);
        } else {
            intakeArm.setIdleMode(IdleMode.kCoast);
        }
    }

    public void armUp(boolean highPower) {
        if (highPower) {
            intakeArm.setVoltage(1 * C_INTAKE_ARM_VOLTAGE);
        } else {
            intakeArm.setVoltage(C_INTAKE_ARM_VOLTAGE / 2); // 5 volt (was 3.33)
        }
    }

    public void armDown() {
        intakeArm.setVoltage(-C_INTAKE_ARM_VOLTAGE / 2); // 3.33 Volts (low power)
    }

    public void armOff() {
        intakeArm.setVoltage(0);
    }

    public void armUpEnd() {
        // intakeArm.setVoltage(C_INTAKE_ARM_VOLTAGE / 5); // 2 Volts, untested, might not work
    } // for borken intake

    @Override
    public void periodic() {
        // SmartDashboard.putString("Mein");
    }
}
