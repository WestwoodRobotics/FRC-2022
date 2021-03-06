// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cscore.*;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.PIDTuningCommand;
import frc.robot.commands.drive.TeleOpDriveCommand;
import frc.robot.commands.feeder.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.hangar.HangarConstantControlCommand;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.commands.vision.AlignLimelightRotationCommand;
import frc.robot.commands.vision.LimelightShootToggleCommand;
import frc.robot.commands.vision.VisionTestingCommand;
import frc.robot.subsystems.*;

import static frc.robot.Constants.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final SwerveDrive m_swerveDrive = new SwerveDrive();
  private final Vision m_vision = new Vision();
  private final Hangar m_hangar = new Hangar();
  private final Intake m_intake = new Intake();
  private final Shooter m_shooter = new Shooter();
  private final Feeder m_feeder = new Feeder();
  //private final SwerveModule m_swerveModule = new SwerveModule();

  private final Autonomous auton =  new Autonomous(m_swerveDrive, m_vision, m_feeder, m_intake, m_shooter, "auton");

  private final XboxController mainController = new XboxController(P_LOGITECH_CONTROLLER);
  private final XboxController hangarController = new XboxController(P_LOGITECH_CONTROLLER2);
  
  private final JoystickButton rBumper = new JoystickButton(mainController, XboxController.Button.kRightBumper.value),
                               lBumper = new JoystickButton(mainController, XboxController.Button.kLeftBumper.value);
  
  private final JoystickButton yButton = new JoystickButton(mainController, XboxController.Button.kY.value),
                               xButton = new JoystickButton(mainController, XboxController.Button.kX.value),
                               bButton = new JoystickButton(mainController, XboxController.Button.kB.value),
                               aButton = new JoystickButton(mainController, XboxController.Button.kA.value);

  private final JoystickButton hangarYButton = new JoystickButton(hangarController, XboxController.Button.kY.value),
                               hangarXButton = new JoystickButton(hangarController, XboxController.Button.kX.value),
                               hangarBButton = new JoystickButton(hangarController, XboxController.Button.kB.value),
                               hangarAButton = new JoystickButton(hangarController, XboxController.Button.kA.value);

  private UsbCamera usbCamera = new UsbCamera("USB Camera 0", 0);
  private MjpegServer mjpegServer1 = new MjpegServer("serve_USB Camera 0", 1181);
  private CvSink cvSink = new CvSink("opencv_USB Camera 0");
  private CvSource outputStream = new CvSource("Blur", VideoMode.PixelFormat.kMJPEG, 640, 480, 30);
  private MjpegServer mjpegServer2 = new MjpegServer("serve_Blur", 1182);

  // private final Joystick left = new Joystick(P_LEFT_JOY);
  // private final Joystick right = new Joystick(P_RIGHT_JOY);
  // private final Joystick gamepad = new Joystick(0);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    configureButtonBindings();
    setDefaultCommands();
    

  }

  private void setDefaultCommands() {
    // Configure default commands
    m_swerveDrive.setDefaultCommand(new TeleOpDriveCommand(m_swerveDrive, mainController, m_shooter));
    //m_swerveDrive.setDefaultCommand(new PIDTuningCommand(m_swerveDrive));

    m_intake.setDefaultCommand(new IntakeConstantControlCommand(m_intake, mainController, m_feeder));

    m_vision.setDefaultCommand(new VisionTestingCommand(m_vision, m_shooter));


    mjpegServer1.setSource(usbCamera);
    cvSink.setSource(usbCamera);
    mjpegServer2.setSource(outputStream);

    m_hangar.setDefaultCommand(new HangarConstantControlCommand(m_hangar, hangarController));
  }
  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
  
    rBumper.whenPressed(new LimelightShootToggleCommand(m_swerveDrive, m_vision, m_shooter, m_feeder, false));
    lBumper.whenPressed(new AlignLimelightRotationCommand(m_swerveDrive, m_vision));

    xButton.whenPressed(new ShooterToggleCommand(m_shooter, 4000).andThen(new TopFeederToggleCommand(m_feeder, false)));
    yButton.whenPressed(new ShooterToggleCommand(m_shooter, 6500).andThen(new TopFeederToggleCommand(m_feeder, false)));
    //bButton.whenPressed(new TopFeederOffCommand(m_feeder));
    bButton.whenPressed(new ShooterToggleCommand(m_shooter).andThen(new TopFeederToggleCommand(m_feeder, false)));

    hangarBButton.whenPressed(new InstantCommand(() -> {
      try {
        m_feeder.getCurrentCommand().cancel();
        m_shooter.getCurrentCommand().cancel();
        m_intake.getCurrentCommand().cancel();
        m_swerveDrive.getCurrentCommand().cancel();
        m_vision.getCurrentCommand().cancel();
      }
      catch (Exception e) {}

      setDefaultCommands();
    }));

    hangarXButton.whenPressed(new ShooterToggleCommand(m_shooter, 4100).andThen( new TopFeederToggleCommand(m_feeder, false)));

//    bButton.whenPressed(new AlignLimelightRotationCommand(m_swerveDrive, m_vision));
    //lBumper speed multiplier is in the teleop drive command

    //Lower feeder wheel
    aButton.whenPressed(new BottomFeederOnCommand(m_feeder));
    aButton.whenReleased(new BottomFeederOffCommand(m_feeder));
//    xButton.whenPressed(new PIDTuningCommand(m_swerveDrive));

    //yButton.whileHeld(new PIDTuningCommand(m_swerveDrive));

    //xButton.whenPressed(new AlignLimelightRotationCommand(m_swerveDrive, m_vision));


    //hangarYButton.whileHeld(new TopFeederToggleCommand(m_feeder, true).alongWith(new BottomFeederToggleCommand(m_feeder, true)));
    



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

}
