// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** An example command that uses an example subsystem. */
public class RunShoota extends CommandBase {

  private Shooter s_shooter;


  /**
   * Creates a new ExampleCommand.
   *
   * The subsystem used by this command.
   */


  public RunShoota(Shooter s_shooter) {

    this.s_shooter = s_shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  /*  if(shooterOn == true)
    {

    }
    else{
      continue;
    }
*/
    s_shooter.setShooterVoltage(10);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s_shooter.shooterOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
