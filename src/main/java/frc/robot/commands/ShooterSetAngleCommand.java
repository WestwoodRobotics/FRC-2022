// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.ShooterConstants.*;

/** An example command that uses an example subsystem. */
public class ShooterSetAngleCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_shooter;
  private double angle;
  

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShooterSetAngleCommand(Shooter subsystem, double angle) {
    m_shooter = subsystem;
    this.angle = angle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    if(angle < m_shooter.getShooterAngle()) {
      m_shooter.moveHood(-1);
    } else if(angle > m_shooter.getShooterAngle()) {
      m_shooter.moveHood(1);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopHood();
  }

  public boolean isAngleLegal() {
    return angle < MAX_ANGLE && angle > MIN_ANGLE;
  }

  public boolean isCommandFinished() {
    return Math.abs(m_shooter.getShooterAngle() - angle) < 1;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !isAngleLegal() || isCommandFinished();
  }
}
