package frc.robot.subsystems;

import static frc.robot.Constants.MagazineConstants.*;
// import edu.wpi.first.wpilibj.controller.PIDController;
// import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Magazine extends SubsystemBase {

    private final WPI_TalonSRX topMagazine = new WPI_TalonSRX(P_TOP_MAGAZINE);
    private final WPI_TalonSRX bottomMagazine = new WPI_TalonSRX(P_BOTTOM_MAGAZINE);

    // constructor
    public Magazine() {

        topMagazine.setInverted(false);
        bottomMagazine.setInverted(true);
        // set stuff for right magazine please lol

        topMagazine.setNeutralMode(NeutralMode.Brake);
    }

    public boolean getTopMagazineDir() {
        return (topMagazine.getSelectedSensorVelocity() < 0);
    }

    public void topMagazineOn(double speed) {
        topMagazine.set(0.3 * speed);
    }

    public void topMagazineOff() {
        topMagazine.set(0);
    }

    public void bottomMagazineOn(double speed) {
        bottomMagazine.set(speed);
    }

    public void bottomMagazineOff() {
        bottomMagazine.set(0);
    }

    public void topToggleState(double speed) {
        if (topMagazineState())
            topMagazineOff();
        else
            topMagazineOn(speed);
    }

    public void bottomToggleState(double speed) {
        if (bottomMagazineState())
            bottomMagazineOff();
        else
            bottomMagazineOn(speed);
    }

    public Boolean topMagazineState() {
        if (Math.abs(topMagazine.get()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean bottomMagazineState() {
        if (Math.abs(bottomMagazine.get()) > 0) {
            return true;
        }
        return false;
    }
}
