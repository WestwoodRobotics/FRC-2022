package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.HangarConstants.*;

public class Hangar extends SubsystemBase {

    private static final TalonFX winchMotor = new TalonFX(P_WINCH_MOTOR);
    private static final TalonFX staticHooksMotor = new TalonFX(P_STATIC_HOOKS);

    public Hangar() {
        winchMotor.setSelectedSensorPosition(0);
        staticHooksMotor.setSelectedSensorPosition(0);

        staticHooksMotor.setNeutralMode(NeutralMode.Brake);
        winchMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setWinchMotorSpeed(double speed) {
        winchMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public double getWinchRotation() {
        return winchMotor.getSelectedSensorPosition() / 2048 * 360;
    }

    public void setStaticHooksMotorSpeed(double speed) {
        staticHooksMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public double getStaticHooksRotation() {
        return staticHooksMotor.getSelectedSensorPosition() / 2048 * 360;
    }

}
