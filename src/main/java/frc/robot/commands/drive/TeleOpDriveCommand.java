package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;

import static frc.robot.Constants.C_DEADZONE_CIRCLE;
import static frc.robot.Constants.C_DEADZONE_RECTANGLE;
import static frc.robot.Constants.DriveConstants.C_MAX_ANGULAR_SPEED;
import static frc.robot.Constants.DriveConstants.C_MAX_SPEED;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.io.PrintWriter;
import java.time.Clock;

import javax.xml.crypto.KeySelector.Purpose;

import com.revrobotics.SparkMaxLimitSwitch;

public class TeleOpDriveCommand extends CommandBase {

    private final SwerveDrive m_swerveDrive;
    private final Shooter m_shooter;
    private boolean held = false;
    private boolean slowMode = false;
    private Long lastButton;
    private final XboxController controller;

    //trolling lmao
    private File csv;
    private long startTime;

    public TeleOpDriveCommand(SwerveDrive swerveDrive, XboxController controller, Shooter shooter) {
        m_swerveDrive = swerveDrive;
        m_shooter = shooter;
        this.controller = controller;

        addRequirements(swerveDrive);
    }

    @Override
    public void initialize() {
        lastButton = Clock.systemUTC().millis();
        startTime = Clock.systemUTC().millis();

//        csv = new File("pid.csv"); //Paths.get("pid.csv");
//        //"C:\\Users\\Student\\Desktop\\Code\\FRC-2022\\src\\main\\java\\frc\\robot\\commands\\
//        try {
//            csv.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            pw = new PrintWriter(csv);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("BRUH MOMENT: file not found");
//        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        long currTime = Clock.systemUTC().millis() - startTime;

        double leftX, leftY, rightX;


        if (held)
            slowMode = !slowMode;

        leftX = -controller.getLeftX();
        leftY = controller.getLeftY();
        rightX = -controller.getRightX();

        // Find the radius for the circle deadzone
        if (Math.sqrt(Math.pow(leftX, 2) + Math.pow(leftY, 2)) < C_DEADZONE_CIRCLE) {
            leftX = 0;
            leftY = 0;
        }

        leftX = checkDeadzone(leftX);
        rightX = checkDeadzone(rightX);
        leftY = checkDeadzone(leftY);
        
        //m_swerveDrive.translate(-leftX * C_MAX_SPEED, -leftY * C_MAX_SPEED);

        SmartDashboard.putBoolean("slowmode: ", slowMode);


        m_swerveDrive.drive(
                (leftX * ((slowMode) ? C_MAX_SPEED : 2.0)),
                (leftY * ((slowMode) ? C_MAX_SPEED : 2.0)),
                (-rightX * C_MAX_ANGULAR_SPEED),
                false);



        if (controller.getPOV() == 0 && Clock.systemUTC().millis() - lastButton > 800) {
            Shooter.shootingRPM = Math.min(Shooter.shootingRPM + 5, 12000);
        }
        if (controller.getPOV() == 180 && Clock.systemUTC().millis() - lastButton > 800) {
            Shooter.shootingRPM = Math.max(Shooter.shootingRPM - 5, 3000);
        }

        SmartDashboard.putNumber("Shooting RPM: ", m_shooter.shootingRPM);
       // SmartDashboard.putNumber("Hood Angle: ", m_shooter.getShooterAngle());
        SmartDashboard.putNumber("pov", controller.getPOV());
    }

    private double checkDeadzone(double val) {
        // zeros if within deadzone rectangle
        if (Math.abs(val) < C_DEADZONE_RECTANGLE) return 0;
        // squares the value to decrease sensitivity
//        else if (val < 0) return -Math.pow(val, 3);
        return Math.pow(val, 3);
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
