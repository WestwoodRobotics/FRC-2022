// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.P_LOGITECH_CONTROLLER;
import static frc.robot.Constants.P_LEFT_JOY;
import static frc.robot.Constants.P_RIGHT_JOY;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.Test;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.SwerveDrive;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final SwerveDrive m_swerveDrive = new SwerveDrive();

  private final Joystick mechJoy = new Joystick(P_LOGITECH_CONTROLLER);
  private final JoystickButton square = new JoystickButton(mechJoy, 1);

  private final Joystick left = new Joystick(P_LEFT_JOY);
  private final Joystick right = new Joystick(P_RIGHT_JOY);


  // private final XboxController mechJoy = new XboxController(P_LOGITECH_CONTROLLER);
  // private final XboxControllerSim square = new XboxControllerSim(1);


  private final Test timmyTest = new Test();
  


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer(){ 
    
    // Configure the button bindings
    configureButtonBindings();
   // square.toggleWhenActive(new InstantCommand -> timmyTest.toString());
    timmyTest.toString();

   // SmartDashboard.putString("Value", "" + mechJoy.getX());
    SmartDashboard.putString("Value", "" + timmyTest.toString());
    // Configure default commands
    m_swerveDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () ->
                m_swerveDrive.drive(
                    -left.getY(),
                    left.getY(),
                    right.getX(),
                    false),
            m_swerveDrive));

        
  }

  

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

   
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }


}
