// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.VisionConstants.C_GOAL_HEIGHT;
import static frc.robot.Constants.VisionConstants.C_MOUNTING_ANGLE;
import static frc.robot.Constants.VisionConstants.C_ROBOT_HEIGHT;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
    /** Creates a new VisionProcessing. */
    private final NetworkTable table;

    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;

    public Vision() {
        table = NetworkTableInstance.getDefault().getTable("limelight");

        table.getEntry("camMode").setNumber(1); // set Limelight camera stream to Driver Vision
        table.getEntry("ledMode").setNumber(0); // disable limelight LED

        // ty = table.getEntry("ty");
        // ta = table.getEntry("ta");
    }

    public double getDistanceFromGoal() {
        // uses trig to return distance to the found goal, height given
        return (C_GOAL_HEIGHT - C_ROBOT_HEIGHT) / Math.tan((C_MOUNTING_ANGLE + ty.getDouble(0)) * Math.PI / 180);
    }

    public double getY() {
        return table.getEntry("ty").getDouble(0);
    }

    public double getXOff() {
        return table.getEntry("tx").getDouble(0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        // //read values periodically
        // double x = tx.getDouble(0);
        // double y = ty.getDouble(0);
        // //double area = ta.getDouble(0);

        // //post to smart dashboard periodically
        // SmartDashboard.putNumber("LimelightX", x);
        // SmartDashboard.putNumber("LimelightY", y);
        // SmartDashboard.putNumber("LimelightArea", area);
    }
}
