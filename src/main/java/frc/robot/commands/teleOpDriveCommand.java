package frc.robot.commands;

import static frc.robot.Constants.C_DEADZONE_CIRCLE;
import static frc.robot.Constants.C_DEADZONE_RECTANGLE;
import static frc.robot.Constants.DriveConstants.C_kMAX_ANGULAR_SPEED;
import static frc.robot.Constants.DriveConstants.C_kMAX_SPEED;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrive;

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
        double leftX = 0, leftY = 0, rightX = 0;

        leftX = controller.getLeftX();
        leftY = -controller.getLeftY();
        rightX = controller.getRightX();

        // if (gamepad.)

        // Find the radius for the circle deadzone
        if (Math.sqrt(Math.pow(leftX, 2) + Math.pow(leftY, 2)) < C_DEADZONE_CIRCLE) {
            leftX = 0;
            leftY = 0;
        }

        if (Math.abs(leftX) < C_DEADZONE_RECTANGLE)
            leftX = 0;
        else if (leftX < 0)
            leftX = -Math.pow(leftX, 2);
        else
            leftX = Math.pow(leftX, 2);

        if (Math.abs(leftY) < C_DEADZONE_RECTANGLE)
            leftY = 0;
        else if (leftY < 0)
            leftY = -Math.pow(leftY, 2);
        else
            leftY = Math.pow(leftY, 2);

        if (Math.abs(rightX) < C_DEADZONE_RECTANGLE)
            rightX = 0;
        else if (rightX < 0)
            rightX = -Math.pow(rightX, 2);
        else
            rightX = Math.pow(rightX, 2);

        m_swerveDrive.drive(
                (leftX * C_kMAX_SPEED),
                (leftY * C_kMAX_SPEED),
                (-rightX * C_kMAX_ANGULAR_SPEED),
                false);
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
