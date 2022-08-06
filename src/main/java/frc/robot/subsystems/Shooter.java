package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    // initializing motors
    private final TalonFX shooterLeft = new TalonFX(P_LEFT_SHOOTER),
            shooterRight = new TalonFX(P_RIGHT_SHOOTER);

    // velocityPID
    private final SimpleMotorFeedforward feedforward;
    private final PIDController velPID;

    private final XboxController controller;

    private double targetVel = 0;
    public static double shootingRPM = 6750;

    // Constructor
    public Shooter(XboxController controller) {

        feedforward = m_FeedForward;
        velPID = m_PID;

        this.controller = controller;


        shooterLeft.setNeutralMode(NeutralMode.Coast);
        shooterRight.setNeutralMode(NeutralMode.Coast);

        shooterRight.configVoltageCompSaturation(12);
        shooterLeft.configVoltageCompSaturation(12);

        shooterRight.enableVoltageCompensation(true);
        shooterLeft.enableVoltageCompensation(true);

        shooterRight.setInverted(true);
        shooterLeft.follow(shooterRight);
    }

    public void setShooterVoltage(double voltage) {
        shooterRight.set(ControlMode.Current, voltage);
    }

    public void setShooterRpm (double rpm){
        shooterRight.set(ControlMode.Velocity, rpm);
    }

    public void setShooterPercent(double percent) {
        shooterRight.set(ControlMode.PercentOutput, percent);
        targetVel = 0;
    }


    public void setShooterVel(double rpm) {
        shooterRight.set(ControlMode.Velocity, rpm);
    }

    public void setShooterVelPID(double vel) {
        targetVel = vel;
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

    @Override
    public void periodic() {

        if (controller.getPOV() == 0)
            targetVel += 10;

        if (controller.getPOV() == 180)
            targetVel -= 10;

        SmartDashboard.putNumber("Shooter target", targetVel);
        SmartDashboard.putNumber("shooter speed", getShooterVel());


        if (targetVel != 0)
            shooterRight.set(ControlMode.PercentOutput, m_FeedForward.calculate(targetVel) );
    }

}