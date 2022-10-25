// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.cscore.*;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.drive.DriveConstantControlCommand;
import frc.robot.commands.hangar.HangarConstantControlCommand;
import frc.robot.commands.intake.*;
import frc.robot.commands.magazine.*;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    private final XboxController mainController = new XboxController(P_LOGITECH_CONTROLLER);
    private final XboxController hangarController = new XboxController(P_LOGITECH_CONTROLLER2);

    public final SwerveDrive m_swerveDrive = new SwerveDrive();
    private final Vision m_vision = new Vision();
    private final Hangar m_hangar = new Hangar();
    private final Intake m_intake = new Intake();
    private final Shooter m_shooter = new Shooter(mainController);
    private final Magazine m_magazine = new Magazine();
    // private final SwerveModule m_swerveModule = new SwerveModule();

    private final Autonomous auton = new Autonomous(m_swerveDrive, m_vision, m_magazine, m_intake, m_shooter, "");

    private final JoystickButton rBumper = new JoystickButton(mainController, XboxController.Button.kRightBumper.value),
            lBumper = new JoystickButton(mainController, XboxController.Button.kLeftBumper.value);
    private final JoystickButton yButton = new JoystickButton(mainController, XboxController.Button.kY.value),
            xButton = new JoystickButton(mainController, XboxController.Button.kX.value),
            bButton = new JoystickButton(mainController, XboxController.Button.kB.value),
            aButton = new JoystickButton(mainController, XboxController.Button.kA.value),
            startButton = new JoystickButton(mainController, XboxController.Button.kStart.value);
    private final JoystickButton hangarYButton = new JoystickButton(hangarController, XboxController.Button.kY.value),
            hangarXButton = new JoystickButton(hangarController, XboxController.Button.kX.value),
            hangarBButton = new JoystickButton(hangarController, XboxController.Button.kB.value),
            hangarAButton = new JoystickButton(hangarController, XboxController.Button.kA.value);

    public final Timer timer = new Timer();

    // private final UsbCamera usbCamera = new UsbCamera("USB Camera 0", 0);
    // private final MjpegServer mjpegServer1 = new MjpegServer("serve_USB Camera
    // 0", 1181);
    // private final CvSink cvSink = new CvSink("opencv_USB Camera 0");
    // private final CvSource outputStream = new CvSource("Blur",
    // VideoMode.PixelFormat.kMJPEG,
    // 640, 480, 30);
    // private final MjpegServer mjpegServer2 = new MjpegServer("serve_Blur", 1182);

    // private final Joystick left = new Joystick(P_LEFT_JOY);
    // private final Joystick right = new Joystick(P_RIGHT_JOY);
    // private final Joystick gamepad = new Joystick(0);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        configureButtonBindings();
        setDefaultCommands();

        // mjpegServer1.setSource(usbCamera);
        // cvSink.setSource(usbCamera);
        // mjpegServer2.setSource(outputStream);

    }

    private void setDefaultCommands() {

        // Configure default commands
        m_swerveDrive.setDefaultCommand(new DriveConstantControlCommand(m_swerveDrive, mainController));
        m_hangar.setDefaultCommand(new HangarConstantControlCommand(m_hangar,
        hangarController));
        m_intake.setDefaultCommand(new IntakeConstantControlCommand(m_intake, mainController, m_magazine));
        // m_intake.setDefaultCommand(new IntakeInCommand(m_intake, mainController,
        // m_magazine));

        // m_swerveDrive.setDefaultCommand(new PIDTuningCommand(m_swerveDrive));
        // m_shooter.setDefaultCommand(new PIDTuningCommand(m_shooter));

    }
    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

        // aim & shoot command
        // lBumper.whenPressed(new VisionShootToggleCommand(m_swerveDrive, m_vision,
        // m_shooter,
        // m_magazine, true));

        // intake

        // Outtake command
        // bButton.whenPressed(new OuttakeCommandGroup(m_magazine, m_shooter));
        // bButton.whenReleased(new OuttakeCommandGroup(m_magazine, m_shooter));

        aButton.whenPressed((new TopMagazineOffCommand(m_magazine)));
        // aButton.whenPressed(new VisionShootToggleCommand(m_swerveDrive, m_vision,
        // m_shooter,
        // m_magazine, false));
        // manual high
        yButton.whenPressed(
                new ShooterToggleCommand(m_shooter, m_magazine, 6800));

        // manual short
        xButton.whenPressed(
                new ShooterToggleCommand(m_shooter, m_magazine, 4500));

        // Lower feeder wheel
        rBumper.whenHeld(new BottomMagazineToggleCommand(m_magazine, false));
        // rBumper.whenReleased(new BottomMagazineOffCommand(m_magazine));

        // death command
        hangarBButton.whenPressed(new InstantCommand(() -> {
            try {
                CommandScheduler.getInstance().cancelAll();
            } catch (Exception ignored) {
            }

            setDefaultCommands();
        }));

        Sendable resetEncoderCommand = new InstantCommand(() -> {
            System.out.println("Encoders reset!");
            m_swerveDrive.resetAllEncoders();
        });

        SmartDashboard.putData("Reset Encoders", resetEncoderCommand);

        Sendable resetPigeonCommand = new InstantCommand(() -> {
            WPI_Pigeon2 imu = new WPI_Pigeon2(5);
            imu.reset(); 
            System.out.println("Pigeons are flying in formation now!");
        });

        SmartDashboard.putData("Reset Pigeon", resetPigeonCommand);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return auton.getCommand();
    }

    public void teleopTimer() {
        timer.reset();
        timer.start();
    }

    public void periodic() {
        SmartDashboard.putNumber("Timer:", 135 - timer.get());
    }

    public void disabledInit() {
        m_swerveDrive.saveEncoderOffsets();
    }
}
