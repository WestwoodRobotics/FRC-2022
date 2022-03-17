package frc.robot.subsystems;

import static frc.robot.Constants.FeederConstants.*;
//import edu.wpi.first.wpilibj.controller.PIDController;
//import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Feeder extends SubsystemBase {

    private final WPI_TalonSRX topFeeder = new WPI_TalonSRX(P_TOP_MAGAZINE);
    private final WPI_TalonSRX bottomFeeder = new WPI_TalonSRX(P_BOTTOM_MAGAZINE);

    //constructor
    public Feeder() {

        topFeeder.setInverted(false);
        bottomFeeder.setInverted(true);
        //set stuff for right feeder please lol

        topFeeder.setNeutralMode(NeutralMode.Coast);
    }

    public void topFeederOn() {
        topFeeder.set(0.3);
    }

    public void topFeederOff() {
        topFeeder.set(0);
    }
    public void bottomFeederOn() {
        bottomFeeder.set(0.3);
    }

    public void bottomFeederOff() {
        bottomFeeder.set(0);
    }

    public void topToggleState() {
        if (feederState())
            topFeederOff();
        else
            topFeederOn();
    }

    public Boolean feederState() {
        if (Math.abs(topFeeder.get()) > 0) {
            return true;
        }
        return false;
    }

}