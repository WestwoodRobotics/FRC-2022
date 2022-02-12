// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

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

    //controller ports
    public static final int P_LOGITECH_CONTROLLER = 0;
    public static final int P_LEFT_JOY = 1;
    public static final int P_RIGHT_JOY = 0;

    //controller constants
    public static final double  C_DEADZONE_CIRCLE = 0.3,    // Radius of deadzone circle
                                C_DEADZONE_RECTANGLE = 0.15;// Half width of deadzone rectangle

    public static final class SwerveModuleConstants
    {
        //motor ports, denoted with 1, then number
        public static final int P_FRONT_RIGHT_TURN = 11,  //1
                                P_FRONT_RIGHT_DRIVE = 12, //2
                                P_FRONT_LEFT_TURN = 13,   //3
                                P_FRONT_LEFT_DRIVE = 14,  //4
                                P_REAR_LEFT_TURN = 15,    //5
                                P_REAR_LEFT_DRIVE = 16,   //6
                                P_REAR_RIGHT_TURN = 17,   //7
                                P_REAR_RIGHT_DRIVE= 18;   //8
        
        //chassis constant
        public static final double C_DISTANCE_FROM_CENTER = 0.5969/2.0; //meters


        //module constants
        public static final double  C_kDRIVE_MOTOR_GEAR_RATIO = 6.75,
                                    C_kTURNING_MOTOR_GEAR_RATIO = 12.8,
                                    C_kWHEELS_DIAMETER = 0.1, //meters
                                    C_MAX_VOLTAGE = 12;
                                    
        
        public static final int C_kENCODER_CPR = 2048;

        public static final double  C_kDRIVE_ENCODER_DISTANCE_PER_PULSE =
                                    (C_kWHEELS_DIAMETER * Math.PI) / ((double) C_kENCODER_CPR * C_kDRIVE_MOTOR_GEAR_RATIO),
                                    C_kTURNING_ENCODER_DISTANCE_PER_PULSE =
                                    // Assumes the encoders are on a 1:1 reduction with the module shaft.
                                    (2.0 * Math.PI) / (C_kENCODER_CPR* C_kTURNING_MOTOR_GEAR_RATIO);

        //motor constants
        public static final double  C_kMAX_MOTOR_ANGULAR_SPEED = 0.02 * 2 * Math.PI, //radians per seconds
                                    C_kMAX_MOTOR_ANGULAR_ACCELERATION = 0.02 * 2 * Math.PI, //radians per seconds sqaured
                                    C_kEDGES_PER_REVOLUTION = 2048; //for use in characterization

        //PID constants
        public static final double  C_DRIVE_kP = 2,
                                    C_DRIVE_kI = 20,
                                    C_DRIVE_kD = 0;

        public static final double  C_TURN_kP = 2.3,
                                    C_TURN_kI = 8.6,
                                    C_TURN_kD = 0.06; 
                                    // C_TURN_kD = 0;                            
        //Feedfoward constants drive motor
        //tiles
        public static final double  C_DRIVE_kA = 0,
                                    C_DRIVE_kS = 0.5,
                                    C_DRIVE_kV = 0;
        
        //Feedforward constants turn motor
        //tiles
        public static final double  C_TURN_kA = 0.00,
                                    C_TURN_kS = 0.5,
                                    C_TURN_kV = 0.0; 

        // public static final double  C_TURN_kA = 0,
        //                             C_TURN_kS = 0,
        //                             C_TURN_kV = 0; 

    }
    public static final class DriveConstants 
    {
        public static final double  C_kMAX_SPEED = 1, //meters per second, controls mapped to this by direct multiplication
                                    C_kMAX_ANGULAR_SPEED = 1 * Math.PI; //radians per second

    }

    public static double map(double input, double min, double max, double outMin, double outMax) {
        return (input - min)/(max-min) * (outMax - outMin) + outMin;
    }
    
}
