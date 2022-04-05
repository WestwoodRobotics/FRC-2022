// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ShooterToggleCommand extends CommandBase {
    private final Shooter m_shooter;
    private final double rpm;
    private boolean finished;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ShooterToggleCommand(Shooter subsystem, double rpm) {
        m_shooter = subsystem;
        finished = false;
        this.rpm = rpm;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    public ShooterToggleCommand(Shooter subsystem) {
        m_shooter = subsystem;
        finished = false;
        this.rpm = Shooter.shootingRPM;
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        finished = false;
        if (m_shooter.getShooterVel() > 100) {
            m_shooter.setShooterPercent(0);
            finished = true;
        }
        else
            m_shooter.setShooterVelPID(rpm);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println(finished);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return  m_shooter.getShooterVel() > rpm - 50 || finished;
    }
}
