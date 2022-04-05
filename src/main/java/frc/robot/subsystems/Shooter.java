package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.*;

import java.beans.Encoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
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

    // private ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
    // initializing motors
    private final TalonFX shooterLeft = new TalonFX(P_LEFT_SHOOTER),
            shooterRight = new TalonFX(P_RIGHT_SHOOTER);

    private final CANSparkMax hood = null;
    //private final CANSparkMax hood = new CANSparkMax(P_HOOD, MotorType.kBrushless);
    private final DigitalInput hoodLimit = new DigitalInput(P_HOOD_LIMIT);

    // velocityPID
    private SimpleMotorFeedforward feedforward;
    private PIDController velPID;

    // Hood Values
    public static double hoodStartPosition = 0;
    public static double shootingRPM = 6750;

    // Constructor
    public Shooter() {

        // shooterLeft.restoreFactoryDefaults();
        // shooterRight.restoreFactoryDefaults();

        feedforward = m_FeedForward;
        velPID = m_PID;

        shooterLeft.setNeutralMode(NeutralMode.Coast);
        shooterRight.setNeutralMode(NeutralMode.Coast);

        // hood.setIdleMode(IdleMode.kBrake);
        // // hoodStartPosition = hood.getEncoder().getPosition();
        // hood.getEncoder().setPosition(0);
        // hood.getEncoder().setPositionConversionFactor(1);

        shooterRight.setInverted(true);
        shooterLeft.follow(shooterRight);
    }

    public void setShooterVoltage(double voltage) {

        shooterRight.set(ControlMode.Current, voltage);
        // shooterLeft.setVoltage(voltage);

    }

    public void setShooterRpm (double rpm)
    {
        shooterRight.set(ControlMode.Velocity, rpm);

    }


    public void setShooterPercent(double percent) {
        shooterRight.set(ControlMode.PercentOutput, percent);
    }

    public void setShooterVel(double rpm) {
        shooterRight.set(ControlMode.Velocity, rpm);
    }

    public void setShooterVelPID(double vel) {
        shooterRight.set(ControlMode.PercentOutput, m_FeedForward.calculate(vel) + m_PID.calculate(getShooterVel(), vel));
    }

    public void shooterOn() {
        shooterRight.set(ControlMode.PercentOutput, 0.18);
    }

    public void shooterOff() {
        shooterLeft.set(ControlMode.Disabled, 0);
    }

    public double getShooterVel() {
        return shooterLeft.getSelectedSensorVelocity();
    }

    public void moveHood(double voltage) {
        hood.setVoltage(voltage);
    }

    public void stopHood() {
        hood.setVoltage(0);
    }

    public double getShooterAngle() {
        return ((hood.getEncoder().getPosition()) * (18.0 / 64.0 / (Math.pow(3.61, 3))) * 360);
        // (rotations of small gear form start) * GEAR_RATIO/gearboxes = rotations of
        // big gear
        // rotations of big gear * 360 = angle change
    }

    public void setShooterAngle(double angle) {
        if (angle <= MIN_ANGLE || angle >= MAX_ANGLE) {

        } else {
            if (angle < this.getShooterAngle()) {
                hood.setVoltage(1);
            } else if (angle > this.getShooterAngle()) {
                hood.setVoltage(-1);
            } else {
                hood.setVoltage(0);
            }
        }
    }

    public void setHoodStart() {
        // hoodStartPosition = hood.getEncoder().getPosition();
    }

    public void resetHood() {
        while (!hoodLimit.get()) {
            hood.setVoltage(-1);
        }
        hood.setVoltage(0);
        hoodStartPosition = hood.getEncoder().getPosition();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("goal RPM", shootingRPM);
        SmartDashboard.putNumber("actual RPM", getShooterVel());
    }
}