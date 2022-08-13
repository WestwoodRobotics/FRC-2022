package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDrive;
import java.time.Clock;
import org.json.simple.JSONObject;

public class DriveCommand extends CommandBase {

	@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
	private final SwerveDrive m_swerveDrive;

	private final long runTime;
	private final double xSpeed;
	private final double ySpeed;
	private final double rotSpeed;

	private long startTime;

	public DriveCommand(SwerveDrive swerveDrive, JSONObject command) {
		m_swerveDrive = swerveDrive;

		this.runTime = (long) command.get("time");
		this.xSpeed = Double.parseDouble(command.get("xspeed") + "");
		this.ySpeed = Double.parseDouble(command.get("yspeed") + "");
		this.rotSpeed = Double.parseDouble(command.get("rotspeed") + "");

		addRequirements(swerveDrive);
	}

	public DriveCommand(SwerveDrive swerveDrive, long time, double xSpeed, double ySpeed, double rotSpeed) {
		m_swerveDrive = swerveDrive;

		this.runTime = time;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.rotSpeed = rotSpeed;

		addRequirements(swerveDrive);
	}

	@Override
	public void initialize() {
		// saves start time in ms
		startTime = Clock.systemUTC().millis();

		// System.out.println(Clock.systemUTC().millis()+ ", " + (45 -
		// m_swerveDrive.getTurnMotorPosition()));

	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		m_swerveDrive.drive(xSpeed, ySpeed, rotSpeed, false);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		// checks that the current time - start time is greater than or equal to the
		// wanted run time
		return Clock.systemUTC().millis() - startTime >= runTime;
	}
}
