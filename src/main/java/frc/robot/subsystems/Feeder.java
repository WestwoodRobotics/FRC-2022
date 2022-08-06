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

        topFeeder.setNeutralMode(NeutralMode.Brake);
    }

    public boolean getTopFeederDir() {
        return (topFeeder.getSelectedSensorVelocity() < 0);
    }

    public void topFeederOn(double speed) {
        topFeeder.set(0.3 * speed);
    }

    public void topFeederOff() {
        topFeeder.set(0);
    }
    public void bottomFeederOn(double speed) {
        bottomFeeder.set(speed);
    }

    public void bottomFeederOff() {
        bottomFeeder.set(0);
    }

    public void topToggleState(double speed) {
        if (topFeederState())
            topFeederOff();
        else
            topFeederOn(speed);
    }

    public void bottomToggleState(double speed) {
        if (bottomFeederState())
            bottomFeederOff();
        else
            bottomFeederOn(speed);
    }

    public Boolean topFeederState() {
        if (Math.abs(topFeeder.get()) > 0) {
            return true;
        }
        return false;
    }

    
    public Boolean bottomFeederState() {
        if (Math.abs(bottomFeeder.get()) > 0) {
            return true;
        }
        return false;
    }

}