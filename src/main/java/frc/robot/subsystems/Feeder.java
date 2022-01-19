package frc.robot.subsystems;

import static frc.robot.Constants.FeederConstants.*;
//import edu.wpi.first.wpilibj.controller.PIDController;
//import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Feeder extends SubsystemBase {

    private final WPI_TalonSRX leftFeeder = new WPI_TalonSRX(P_LEFT_FEEDER);
    private final WPI_TalonSRX rightFeeder = new WPI_TalonSRX(P_RIGHT_FEEDER);

    public Feeder() {

        leftFeeder.setInverted(true);
        rightFeeder.follow(leftFeeder);

        //leftFeeder.setNeutralMode(NeutralMode.Brake);
    }

    public void FeederOn() {
        leftFeeder.set(1);
    }

    public void FeederOff() {
        leftFeeder.set(0);
    }

    public Boolean FeederState() {
        if (leftFeeder.get() == 1) {
            return true;
        }
        return false;
    }

}