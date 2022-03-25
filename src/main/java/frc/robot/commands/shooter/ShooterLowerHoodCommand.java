// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.ShooterConstants.*;

/** An example command that uses an example subsystem. */
public class ShooterLowerHoodCommand extends CommandBase {
  private final Shooter m_shooter;
  

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShooterLowerHoodCommand(Shooter subsystem) {
    m_shooter = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    m_shooter.moveHood(-0.2);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopHood();
  }

  public boolean isAngleLegal() {
    return m_shooter.getShooterAngle() < MAX_ANGLE && m_shooter.getShooterAngle() > MIN_ANGLE;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !isAngleLegal();
  }
}
