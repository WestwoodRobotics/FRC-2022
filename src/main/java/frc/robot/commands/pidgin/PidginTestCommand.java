package frc.robot.commands.pidgin;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PidginTestCommand extends CommandBase {
    Pigeon2 pidgin;

    public PidginTestCommand(Pigeon2 pidginIn) {
        this.pidgin = pidginIn;
    }

    /*public void checkVals() {
        int _loopCount = 0;
        if(_loopCount++ > 10)
        {
            _loopCount = 0;
            //double yaw = pidgin.getYaw();
            //SmartDashboard.putString("Pigeon Yaw is: " + yaw);
        }
    }*/
}
