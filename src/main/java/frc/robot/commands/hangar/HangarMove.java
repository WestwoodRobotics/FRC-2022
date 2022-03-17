package frc.robot.commands.hangar;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hangar;

import static frc.robot.Constants.HangarConstants.C_WINCH_CIRCUMFERENCE;
import static frc.robot.Constants.HangarConstants.C_WINCH_GEARDIFF;

public class HangarMove extends CommandBase {

    private final Hangar m_hangar;
    private final XboxController controller;

    public HangarMove(Hangar hangar, XboxController controller) {
        m_hangar = hangar;
        this.controller = controller;

        addRequirements(hangar);
    }

    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double Xleft, Xright;

        Xleft = controller.getLeftX();
        Xright = controller.getRightX();

        m_hangar.setWinchMotorSpeed(Xleft);
        m_hangar.setClawMotorSpeed(0.5 * Xright);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
