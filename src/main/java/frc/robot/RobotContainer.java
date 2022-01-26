// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.P_LEFT_JOY;
import static frc.robot.Constants.P_LOGITECH_CONTROLLER;
import static frc.robot.Constants.P_RIGHT_JOY;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.RunFeeda;
import frc.robot.commands.RunShoota;
import frc.robot.commands.Test;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
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
  private final Shooter m_shooter = new Shooter();
  private final Feeder m_feeder = new Feeder();

  private final XboxController mechJoy = new XboxController(P_LOGITECH_CONTROLLER);
  private final JoystickButton square = new JoystickButton(mechJoy, 1);
  private final JoystickButton circle = new JoystickButton(mechJoy, 3);
  private final JoystickButton triangle = new JoystickButton(mechJoy, 4);


  private final Joystick left = new Joystick(P_LEFT_JOY);
  private final Joystick right = new Joystick(P_RIGHT_JOY);

  //ShuffleboardLayout shooterCommands = Shuffleboard.getTab("Shooter");


  // private final XboxController mechJoy = new XboxController(P_LOGITECH_CONTROLLER);
  // private final XboxControllerSim square = new XboxControllerSim(1);


  private final Test timmyTest = new Test();
  private final RunShoota shootshoot = new RunShoota(m_shooter);
  private final RunFeeda feedfeed = new RunFeeda(m_feeder);
  //private final RunShoota shootCommand = new RunShoota(m_shooter);
  


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
                    mechJoy.getLeftX(),
                    -mechJoy.getLeftY(),
                    mechJoy.getRightX(),
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

    triangle.toggleWhenPressed(new InstantCommand(() -> m_shooter.setShooterVelocityPID(1000))).whenReleased(() -> m_shooter.shooterOff());
    circle.whenPressed(() -> m_feeder.feederOn()).whenReleased(() -> m_feeder.feederOff());
    //triangle.whenPressed(new InstantCommand(m_shooter::shooterOff, m_shooter));;
    //square.whenPressed((new InstantCommand(() -> System.out.println("hi"))));
    //square.whenPressed(new InstantCommand( () -> SmartDashboard.putString("ornage", "orange")));
    //SmartDashboard.putString("Value", "" + mechJoy.getLeftX());
    //square.whenPressed( () -> m_swerveDrive.printTest(i) );
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
