package frc.robot.subsystems;

import static frc.robot.Constants.FeederConstants.*;
//import edu.wpi.first.wpilibj.controller.PIDController;
//import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Feeder extends SubsystemBase {

    private final WPI_TalonSRX leftFeeder = new WPI_TalonSRX(P_TOP_MAGAZINE);
    private final WPI_TalonSRX rightFeeder = new WPI_TalonSRX(P_BOTTOM_MAGAZINE);

    //constructor
    public Feeder() {

        leftFeeder.setInverted(true);
        //set stuff for right feeder please lol

        leftFeeder.setNeutralMode(NeutralMode.Coast);
    }

    public void topFeederOn() {
        leftFeeder.set(1);
    }

    public void topFeederOff() {
        leftFeeder.set(0);
    }
    public void bottomFeederOn() {
        rightFeeder.set(1);
    }

    public void bottomFeederOff() {
        rightFeeder.set(0);
    }

    public void topToggleState() {
        if (feederState())
            topFeederOff();
        else
            topFeederOn();
    }

    public Boolean feederState() {
        if (leftFeeder.get() == 1) {
            return true;
        }
        return false;
    }

}