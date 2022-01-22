package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Shooter extends SubsystemBase {

    //initializing motors
    private final CANSparkMax shooterLeft = new CANSparkMax(P_LEFT_SHOOTER, MotorType.kBrushless);
    private final CANSparkMax shooterRight = new CANSparkMax(P_RIGHT_SHOOTER, MotorType.kBrushless);

    //Constructor
    public Shooter() {

        //shooterLeft.restoreFactoryDefaults();
        //shooterRight.restoreFactoryDefaults();

        shooterLeft.setIdleMode(IdleMode.kCoast);
        shooterRight.setIdleMode(IdleMode.kCoast);

        shooterLeft.setInverted(true);
        shooterRight.follow(shooterLeft);

    }

    public void setShooterVoltage( double voltage ) {
        shooterLeft.setVoltage(voltage);
    }

    public void ShooterOff() {
        shooterLeft.stopMotor();
    }

    public double getVelocity() {
        return shooterLeft.getEncoder().getVelocity();
    }

    
}