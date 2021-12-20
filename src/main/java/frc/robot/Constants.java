// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class SwerveConstants
    {
        //motor ports
        public static final int P_DRIVE_TALONFX1 = 11,
                                P_DRIVE_TALONFX2 = 12,
                                P_DRIVE_TALONFX3 = 13,
                                P_DRIVE_TALONFX4 = 14,
                                P_DRIVE_TALONFX5 = 15,
                                P_DRIVE_TALONFX6 = 16,
                                P_DRIVE_TALONFX7 = 17,
                                P_DRIVE_TALONFX8 = 18;

        //module constants
        public static final double  C_kDRIVE_MOTOR_GEAR_RATIO = 0,
                                    C_kTURNING_MOTOR_GEAR_RATIO = 0,
                                    C_kWHEELS_DIAMETER_METERS = Units.inchesToMeters(0);
        
        public static final int C_kENCODER_CPR = 0;

        public static final double  C_kDRIVE_ENCODER_DISTANCE_PER_PULSE =
                                    (C_kWHEELS_DIAMETER_METERS * Math.PI) / ((double) C_kENCODER_CPR * C_kDRIVE_MOTOR_GEAR_RATIO),
                                    C_kTURNING_ENCODER_DISTANCE_PER_PULSE =
                                    // Assumes the encoders are on a 1:1 reduction with the module shaft.
                                    (2.0 * Math.PI) / (C_kENCODER_CPR* C_kTURNING_MOTOR_GEAR_RATIO);

        //motor constants
        public static final double  C_kMaxMotorAngularSpeed = 0 * 2 * Math.PI, //rad/s
                                    C_kMaxMotorAngularAcceleration = 0 * 2 * Math.PI; //rad/s^2

        //PID constants
        public static final double  C_SWERVE_kP = 0,
                                    C_SWERVE_kI = 0,
                                    C_SWERVE_kD = 0;
        //Feedfoward constants
        public static final double  C_SWERVE_kA = 0,
                                    C_SWERVE_kS = 0,
                                    C_SWERVE_kV =0;



    }

    
}
