package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrive;

import static frc.robot.Constants.C_DEADZONE_CIRCLE;
import static frc.robot.Constants.C_DEADZONE_RECTANGLE;
import static frc.robot.Constants.DriveConstants.C_MAX_ANGULAR_SPEED;
import static frc.robot.Constants.DriveConstants.C_MAX_SPEED;

public class teleOpDriveCommand extends CommandBase {

    private final SwerveDrive m_swerveDrive;
    private final XboxController controller;

    public teleOpDriveCommand(SwerveDrive swerveDrive, XboxController controller) {
        m_swerveDrive = swerveDrive;
        this.controller = controller;

        addRequirements(swerveDrive);
    }

    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double leftX, leftY, rightX;

        leftX = controller.getLeftX();
        leftY = -controller.getLeftY();
        rightX = controller.getRightX();

        // Find the radius for the circle deadzone
        if (Math.sqrt(Math.pow(leftX, 2) + Math.pow(leftY, 2)) < C_DEADZONE_CIRCLE) {
            leftX = 0;
            leftY = 0;
        }

        leftX = checkDeadzone(leftX);
        rightX = checkDeadzone(rightX);
        leftY = checkDeadzone(leftY);
        
        m_swerveDrive.drive(
                (leftX * C_MAX_SPEED),
                (leftY * C_MAX_SPEED),
                (-rightX * C_MAX_ANGULAR_SPEED),
                false);
    }

    private double checkDeadzone(double val) {
        // zeros if within deadzone rectangle
        if (Math.abs(val) < C_DEADZONE_RECTANGLE) return 0;
        // squares the value to decrease sensitivity
        else if (val < 0) return -Math.pow(val, 2);
        return Math.pow(val, 2);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // should never end in teleop
        return false;
    }

}
