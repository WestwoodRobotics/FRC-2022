package frc.robot.commands.hangar;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hangar;

public class HangarConstantControlCommand extends CommandBase {

    private final Hangar m_hangar;
    private final XboxController controller;

    public HangarConstantControlCommand(Hangar hangar, XboxController controller) {
        m_hangar = hangar;
        this.controller = controller;

        addRequirements(hangar);
    }

    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double leftY, rightX;

        leftY = controller.getLeftY();
        rightX = -controller.getRightX();

        rightX = checkDeadzone(rightX);
        leftY = checkDeadzone(leftY);

        // m_hangar.setStaticHooksMotorSpeed(rightX * (controller.getLeftBumper() ? 0.1 : 1));

        double power = (controller.getLeftBumper()) ? 0.4 : 1;

        m_hangar.setWinchMotorSpeed(leftY * power);
    }

    private double checkDeadzone(double val) {
        // zeros if within deadzone rectangle
        if (Math.abs(val) < C_DEADZONE_RECTANGLE) {
            return 0;
        }
        // squares the value to decrease sensitivity
        else if (val < 0) {
            return -Math.pow(val, 2);
        } else {
            return Math.pow(val, 2);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // should never end in teleop
        return false;
    }
}
