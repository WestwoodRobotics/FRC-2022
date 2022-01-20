// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import static frc.robot.Constants.P_LOGITECH_CONTROLLER;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class Test extends InstantCommand {
  // Called when the command is initially scheduled.

  //timmy the timer, after the infamous Timmy

  // Get the current time from the timer. 
  // If the clock is running it is derived from the current system clock the start time stored in the timer class. 
  // If the clock is not running, then return the time when it was last stopped.
  public Timer timmy = new Timer();


  private final Joystick mechJoy = new Joystick(P_LOGITECH_CONTROLLER);
  //private final JoystickButton square = new JoystickButton(mechJoy, 1);

  @Override
  public void initialize() {
    timmy.start();
    //may not work because I am stupid and the start() is running the timer???
    System.out.println("Test has been initialized");
    SmartDashboard.getString("Initialize", "Test is initialized");
    SmartDashboard.getString("Initialize Timer", (mechJoy.getX()+ ""));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Test has executed");
    SmartDashboard.getString("Execute", "Test is executed");
    SmartDashboard.getString("Execute Timer", (timmy.get()+ ""));
    SmartDashboard.getString("Execute mech.", (mechJoy.getX()+ ""));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end() {
    System.out.println("Test has ended");
    SmartDashboard.getString("End", "Test has ended");
    SmartDashboard.getString("End Timer", (timmy.get()+ ""));
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println("Test is Finished");
    SmartDashboard.getString("isFinished", "Test is Finished");
    SmartDashboard.getString("isFinished Timer", (timmy.get()+ ""));
    return false;
    
  }


  @Override
  public String toString() {
      // TODO Auto-generated method stub
      
      return "" + mechJoy.getX();
  }

}