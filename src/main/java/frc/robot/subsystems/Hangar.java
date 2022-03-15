package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.HangarConstants.*;

public class Hangar extends SubsystemBase {

    // 2 Falcon 500 motors
    private static TalonFX winchMotor = new TalonFX(P_WINCH_MOTOR),
                           clawMotor = new TalonFX(P_CLAW_MOTOR);

    public Hangar() {
        winchMotor.setSelectedSensorPosition(0);
        clawMotor.setSelectedSensorPosition(0);
    }

    public void setWinchMotorSpeed(double speed) {
        winchMotor.set(TalonFXControlMode.Velocity, speed);
    }

    public double getWinchRotation() {
        return winchMotor.getSelectedSensorPosition() / 2048 * 360;
    }

    public void setClawMotorSpeed(double speed) {
        clawMotor.set(TalonFXControlMode.Velocity, speed);
    }

    public double getClawRotation() {
        return clawMotor.getSelectedSensorPosition() / 2048 * 360;
    }

}
