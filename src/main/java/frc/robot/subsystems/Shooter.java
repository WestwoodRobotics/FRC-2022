package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.*;

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


public class Shooter extends SubsystemBase {

    //private ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
    //initializing motors
    private final CANSparkMax shooterLeft = new CANSparkMax(P_LEFT_SHOOTER, MotorType.kBrushless);
    private final CANSparkMax shooterRight = new CANSparkMax(P_RIGHT_SHOOTER, MotorType.kBrushless);
    private final CANSparkMax hood = new CANSparkMax(P_HOOD, MotorType.kBrushless);
    private final DigitalInput hoodLimit = new DigitalInput(P_HOOD_LIMIT);

    //velocityPID 
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(C_kS, C_kV, C_kA);
    private PIDController velPID = new PIDController(C_kP, C_kI, C_kD);

    //Hood Values
    private double hoodStartPosition = 0;

    //Constructor
    public Shooter() {

        //shooterLeft.restoreFactoryDefaults();
        //shooterRight.restoreFactoryDefaults();

        shooterLeft.setIdleMode(IdleMode.kCoast);
        shooterRight.setIdleMode(IdleMode.kCoast);

        hood.setIdleMode(IdleMode.kBrake);
        //hoodStartPosition = hood.getEncoder().getPosition();
        hood.getEncoder().setPosition(0);
        hood.getEncoder().setPositionConversionFactor(1);

        shooterRight.setInverted(false);
        shooterLeft.follow(shooterRight, true);        
    }

    public void setShooterVoltage( double voltage ) {
 
        shooterRight.setVoltage(voltage);
        //shooterLeft.setVoltage(voltage);

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

    public void moveHood(double voltage) {
        hood.setVoltage(voltage);
    }

    public void stopHood() {
        hood.setVoltage(0);
    }

    public double getShooterAngle() {
        return ((hood.getEncoder().getPosition()) * (18.0/64.0/(Math.pow(3.61, 3))) * 360);
        //(rotations of small gear form start) * GEAR_RATIO/gearboxes = rotations of big gear
        //rotations of big gear * 360 = angle change
    }
    
    public void setShooterAngle(double angle) {
        if(angle <= MIN_ANGLE || angle >= MAX_ANGLE) {

        } else {
            if(angle < this.getShooterAngle()) {
                hood.setVoltage(1);
            } else if(angle > this.getShooterAngle()) {
                hood.setVoltage(-1);
            } else {
                hood.setVoltage(0);
            }
        }
    }

    public void setHoodStart() {
        //hoodStartPosition = hood.getEncoder().getPosition();
    }

    public void resetHood() {
        while(!hoodLimit.get()) {
            hood.setVoltage(-1);
        }
        hood.setVoltage(0);
        hoodStartPosition = hood.getEncoder().getPosition();
    }

    @Override
    public void periodic() {
        SmartDashboard.putString("shooter angle", "" + getShooterAngle());
        SmartDashboard.putString("shooter encoder angle", "" + hood.getEncoder().getPosition());
    }
}