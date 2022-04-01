package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.*;

import java.beans.Encoder;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.opencv.core.Mat;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {

    //private ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
    //initializing motors
    private final CANSparkMax intakeArm = new CANSparkMax(P_INTAKE_ARM, MotorType.kBrushless);
    private final CANSparkMax intakeBelt = new CANSparkMax(P_INTAKE_BELT, MotorType.kBrushless);
    
    //PID might not be needed lol

    //Constructor
    public Intake() {

        intakeArm.setIdleMode(IdleMode.kBrake);
        intakeBelt.setIdleMode(IdleMode.kCoast);

        intakeArm.getEncoder().setPosition(0);

    }

    public double getIntake() {
        return intakeArm.getEncoder().getPosition();
    }

    public void beltOn()
    {
        intakeBelt.setVoltage( C_INTAKE_BELT_VOLTAGE );
    }

    public void beltOff() {
        intakeBelt.set(0);
    }

    public void brakeMode(boolean brake)
    {
        if(brake)
        {
            intakeArm.setIdleMode(IdleMode.kBrake);
        }
        else
        {
            intakeArm.setIdleMode(IdleMode.kCoast);
        }
    }
    public void armUp() 
    {
        intakeArm.setVoltage(1 * C_INTAKE_ARM_VOLTAGE);
    }
    
    public void armDown() 
    {
        intakeArm.setVoltage(-1 * C_INTAKE_ARM_VOLTAGE);
    }

    public void armOff()
    {
        intakeArm.setVoltage(0);
    }

    public void armUpEnd() {
        intakeArm.setVoltage(2);
    }
    
    @Override
    public void periodic() {
        //SmartDashboard.putString("Mein");
    }
}
