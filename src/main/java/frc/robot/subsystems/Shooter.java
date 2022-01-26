package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Shooter extends SubsystemBase {

    //private ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
    //initializing motors
    private final CANSparkMax shooterLeft = new CANSparkMax(P_LEFT_SHOOTER, MotorType.kBrushless);
    private final CANSparkMax shooterRight = new CANSparkMax(P_RIGHT_SHOOTER, MotorType.kBrushless);

    //velocityPID 
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(C_kS, C_kV, C_kA);
    private PIDController velPID = new PIDController(C_kP, C_kI, C_kD);

    //Constructor
    public Shooter() {

        //shooterLeft.restoreFactoryDefaults();
        //shooterRight.restoreFactoryDefaults();

        shooterLeft.setIdleMode(IdleMode.kCoast);
        shooterRight.setIdleMode(IdleMode.kCoast);

        shooterLeft.follow(shooterRight);
        shooterLeft.setInverted(true);
    }

    public void setShooterVoltage( double voltage ) {
        shooterLeft.setVoltage(voltage);
    }

    public void setShooterVelocityPID(double rpm) {
        //this.currentPose.setLaunchRPM(rpm);
        double volt = 0;
    
        volt += feedforward.calculate(rpm / 60.0);
        //SmartDashboard.putNumber("volt feedforward", volt);
        volt += velPID.calculate(getShooterVel() / 60.0, rpm / 60.0);
        //SmartDashboard.putNumber("volt velocityPID", volt);
    
        this.setShooterVoltage(volt);
      }

    public void shooterOff() {
        shooterLeft.stopMotor();
    }

    public double getShooterVel() {
        return shooterLeft.getEncoder().getVelocity();
    }

    
}