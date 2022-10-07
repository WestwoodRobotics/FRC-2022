// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;

/** An example command that uses an example subsystem. */
public class ShooterToggleCommand extends CommandBase {
    private final Shooter m_shooter;
    private final Magazine m_magazine;
    private final double rpm;
    private boolean isActive;
    private boolean finished;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem
     *            The subsystem used by this command.
     */
    public ShooterToggleCommand(Shooter subsystem, Magazine mag_subsystem, double rpm) {
        m_shooter = subsystem;
        m_magazine = mag_subsystem;
        finished = false;
        this.rpm = rpm;
        this.isActive = false;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem, mag_subsystem);
    }

    // public ShooterToggleCommand(Shooter subsystem) {
    //     m_shooter = subsystem;
    //     finished = false;
    //     this.rpm = Shooter.shootingRPM;
    //     addRequirements(subsystem);
    // }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        finished = false;
        if (isActive) {
            m_shooter.setShooterPercent(0);
            SmartDashboard.putBoolean("Shooter Enabled", false);
            execute(); // run final execution actions
            finished = true;
        } else {
            m_shooter.setShooterVelPID(rpm);
            SmartDashboard.putBoolean("Shooter Enabled", true);
        }
        isActive = !isActive;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        SmartDashboard.putNumber("shooter velocity (not tuning)", m_shooter.getShooterVel());
        if (reachedRPM() && isActive) {
            m_magazine.topMagazineOn(1);
        } else {
            m_magazine.topMagazineOff();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    public boolean reachedRPM() {
        return (m_shooter.getShooterVel() > rpm - 50 && m_shooter.getShooterVel() < rpm + 100);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return reachedRPM() || finished;
    }
}
