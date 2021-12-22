// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.geometry.Translation2d;
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
        //motor ports, denoted with 1, then number
        public static final int P_FRONT_RIGHT_TURN = 11, //1
                                P_FRONT_RIGHT_DRIVE = 12, //2
                                P_FRONT_LEFT_TURN = 13, //3
                                P_FRONT_LEFT_DRIVE = 14,  //4
                                P_REAR_LEFT_TURN = 15,  //5
                                P_REAR_LEFT_DRIVE = 16,   //6
                                P_REAR_RIGHT_TURN = 17, //7
                                P_REAR_RIGHT_DRIVE= 18;  //8
        
        //chasi constant
        public static final double C_DISTANCE_FROM_CENTER_METERS = 0.5969/2.0;


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
