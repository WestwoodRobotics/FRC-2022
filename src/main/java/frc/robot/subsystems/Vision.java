// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.VisionConstants.*;

import javax.print.attribute.DocAttribute;

public class Vision extends SubsystemBase {
  /** Creates a new VisionProcessing. */
  private NetworkTable table;
  private NetworkTableEntry tx;
  private NetworkTableEntry ty; 
  private NetworkTableEntry ta;

  public Vision() 
  {
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
  }

  public double getDistanceFromGoal()
  {
    return (C_kGoalHeight - C_kRobotHeight) / Math.tan((C_kMountingAngle + ty.getDouble(0)) * Math.PI/180);
  }

  public double getXOff() {
    return tx.getDouble(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
