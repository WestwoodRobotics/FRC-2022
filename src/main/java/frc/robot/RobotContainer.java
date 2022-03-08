// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.drive.DriveZeroCommand;
import frc.robot.commands.drive.TeleOpDriveCommand;
import frc.robot.subsystems.Hangar;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

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

  private final Autonomous auton =  new Autonomous(m_swerveDrive, m_vision, "testPath");

  private final XboxController mechJoy = new XboxController(P_LOGITECH_CONTROLLER);
  private final JoystickButton yButton = new JoystickButton(mechJoy, XboxController.Button.kY.value);
  private final JoystickButton xButton = new JoystickButton(mechJoy, XboxController.Button.kX.value);
  private final JoystickButton aButton = new JoystickButton(mechJoy, XboxController.Button.kA.value);

  private final Joystick left = new Joystick(P_LEFT_JOY);
  private final Joystick right = new Joystick(P_RIGHT_JOY);
  // private final Joystick gamepad = new Joystick(0);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the button bindings
    configureButtonBindings();
    // square.toggleWhenActive(new InstantCommand -> timmyTest.toString());
    // timmyTest.toString();

    // Configure default commands
    m_swerveDrive.setDefaultCommand(new TeleOpDriveCommand(m_swerveDrive, mechJoy));
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

    aButton.whenPressed(new InstantCommand(
        () -> SmartDashboard.putNumber("Distance from Goal in meters", m_vision.getDistanceFromGoal())));

    xButton.whenPressed(new InstantCommand(auton::run));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new DriveZeroCommand(m_swerveDrive);
  }

}
