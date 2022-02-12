// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.P_LEFT_JOY;
import static frc.robot.Constants.P_LOGITECH_CONTROLLER;
import static frc.robot.Constants.P_RIGHT_JOY;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.driveZeroCommand;
import frc.robot.commands.teleOpDriveCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.SwerveDrive;

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
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final SwerveDrive m_swerveDrive = new SwerveDrive();

  private final XboxController mechJoy = new XboxController(P_LOGITECH_CONTROLLER);
  private final JoystickButton yButton = new JoystickButton(mechJoy, XboxController.Button.kY.value);
  private final JoystickButton square = new JoystickButton(mechJoy, XboxController.Button.kX.value);

  private final Joystick left = new Joystick(P_LEFT_JOY);
  private final Joystick right = new Joystick(P_RIGHT_JOY);
  private final Joystick gamepad = new Joystick(0);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the button bindings
    configureButtonBindings();
    // square.toggleWhenActive(new InstantCommand -> timmyTest.toString());
    // timmyTest.toString();

    // Configure default commands
    m_swerveDrive.setDefaultCommand(new teleOpDriveCommand(m_swerveDrive, mechJoy));
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

    // square.whenPressed((new InstantCommand(() -> System.out.println("hi"));
    // square.whenPressed(new InstantCommand( () ->
    // SmartDashboard.putString("ornage", "orange")));
    // SmartDashboard.putString("Value", "" + mechJoy.getLeftX());
    // square.whenPressed( () -> m_swerveDrive.printTest(i) );

    // parsing file "JSONExample.json"
    Object obj = null;
    try {
      obj = new JSONParser().parse(new FileReader(Filesystem.getDeployDirectory().getPath() + "/paths/testPath.json"));
    } catch (IOException | ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // typecasting obj to JSONObject
    JSONObject jo = (JSONObject) obj;

    if (jo != null)
      square.whenPressed(new InstantCommand(() -> {
        m_swerveDrive.getCurrentCommand().cancel();
        System.out.println("-----------------------testing drive ");
        new DriveCommand(m_swerveDrive, jo);
      }));
    else
      System.out.println("-----------------------file not found");

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new driveZeroCommand(m_swerveDrive);
  }

}
