// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;

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
    public static final int P_LOGITECH_CONTROLLER = 0,
                            P_LOGITECH_CONTROLLER2 = 1,
                            P_LEFT_JOY = 1,
                            P_RIGHT_JOY = 0;

    //controller constants
    public static final double  C_DEADZONE_CIRCLE = 0.3,    // Radius of deadzone circle
                                C_DEADZONE_RECTANGLE = 0.15;// Half width of deadzone rectangle

    public static final class HangarConstants {
        public static final int P_WINCH_MOTOR = 8,
                                P_STATIC_HOOKS = 7;

        public static final double C_WINCH_CIRCUMFERENCE = 94.7458, //mm
                                   C_WINCH_HEIGHT = 800,
                                   C_WINCH_GEARDIFF = 1.0/12; //mm
    }

    public static final class SwerveModuleConstants {
        // CAN ports
        public static final int P_FRONT_RIGHT_TURN = 11,  //1
                                P_FRONT_RIGHT_DRIVE = 12, //2
                                P_FRONT_LEFT_TURN = 13,  //3
                                P_FRONT_LEFT_DRIVE = 14,  //4
                                P_REAR_LEFT_TURN = 15,    //5
                                P_REAR_LEFT_DRIVE = 16,   //6
                                P_REAR_RIGHT_TURN = 17,   //7
                                P_REAR_RIGHT_DRIVE= 18,   //8
                                P_PIDGIN = 32;

        //CANcoder ports
        public static final int P_FRONT_RIGHT_ENCODER = 1,
                                P_FRONT_LEFT_ENCODER = 2,
                                P_BACK_RIGHT_ENCODER = 4,
                                P_BACK_LEFT_ENCODER = 3;
        
        //chassis constant
        public static final double C_DISTANCE_FROM_CENTER_WIDTH = 0.4953/2.0,
                                   C_DISTANCE_FROM_CENTER_LENGTH = 0.6477/2.0; //meters

        //module constants
        public static final double C_DRIVE_MOTOR_GEAR_RATIO = 6.75,
                                   C_TURNING_MOTOR_GEAR_RATIO = 12.8,
                                   C_WHEELS_DIAMETER = 0.1, //meters
                                   C_MAX_VOLTAGE = 12;

        
        public static final int C_ENCODER_CPR = 2048;

        public static final double C_DRIVE_ENCODER_DISTANCE_PER_PULSE = (C_WHEELS_DIAMETER * Math.PI) / ((double) C_ENCODER_CPR * C_DRIVE_MOTOR_GEAR_RATIO),
                                   C_kTURNING_ENCODER_DISTANCE_PER_PULSE = (2.0 * Math.PI) / (C_ENCODER_CPR * C_TURNING_MOTOR_GEAR_RATIO); // Assumes the encoders are on a 1:1 reduction with the module shaft.

        //motor constants
        public static final double C_MAX_MOTOR_ANGULAR_SPEED = 0.02 * 2 * Math.PI, //radians per seconds
                                   C_MAX_MOTOR_ANGULAR_ACCELERATION = 0.02 * 2 * Math.PI, //radians per seconds sqaured
                                   C_EDGES_PER_REVOLUTION = 2048; //for use in characterization

        //PID constants
        /*public static final double  C_DRIVE_kP = 2.3,
                                    C_DRIVE_kI = 20,
                                    C_DRIVE_kD = 0;

        public static final double  C_TURN_kP = 2.5,
                                    C_TURN_kI = 8.2,
                                    C_TURN_kD = 0.06;
        */

        //tuned
        public static final double  C_DRIVE_kP = 2.3,
                                    C_DRIVE_kI = 20,
                                    C_DRIVE_kD = 0;

        public static final double  C_TURN_kP = 10,
                                    C_TURN_kI = 0,
                                    C_TURN_kD = 0;

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
    }
    public static final class DriveConstants {
        public static final double C_MAX_SPEED = 6, //meters per second, controls mapped to this by direct multiplication
                                   C_MAX_ANGULAR_SPEED = 1.3 * Math.PI,
                                   C_kPXVision = 0.4; //radians per second
    }

    public static double map(double input, double min, double max, double outMin, double outMax) { return (input - min)/(max-min) * (outMax - outMin) + outMin; }

    public static class VisionConstants {
        public static final double C_MOUNTING_ANGLE = 14, // degrees
                                   C_GOAL_HEIGHT = 2.642, //meters
                                   C_ROBOT_HEIGHT = 0.457, //meters
                                   C_GOAL_DISTANCE = 7.919718984, //meters
                                   C_ACCEPTABLE_GOAL_OFFSET = .3,
                                   C_ACCEPTABLE_DEGREE_DISTANCE = 3; // acceptable degree offset for alignment

        public static double getHoodAngle(double distance) {

            return distance; //use linear regression

        }

    }

    public static final class ShooterConstants
    {
        // CAN Ports
        public static final int     P_LEFT_SHOOTER = 21,
                                    P_RIGHT_SHOOTER = 22,
                                    P_HOOD = 20,
                                    P_HOOD_LIMIT = 1;

        public static final double  GEAR_RATIO = 18/64;

        public static final double  MIN_ANGLE = 0,
                                    MAX_ANGLE = 26;

        public static final double  C_kS = 0.14,
                                    C_kV = 0.130,
                                    C_kA = 0.0207,
                                    C_kP = 0.01,
                                    C_kI = 0,
                                    C_kD = 0;

        public static final int C_MAX_RPM = 6380;

        //shooter PID constants
  /*      public static final double  C_LEFT_SHOOTER_kP = 0.0,
                                    C_LEFT_SHOOTER_kI = 0.0,
                                    C_LEFT_SHOOTER_kD = 0.0;

        public static final double  C_RIGHT_SHOOTER_kP = 0.01,
                                    C_RIGHT_SHOOTER_kI = 0.0,
                                    C_RIGHT_SHOOTER_kD = 0.0;
    */
    }

    public static final class FeederConstants
    {
        //motor ports, denoted with 1, then number
        public static final int     P_TOP_MAGAZINE = 6,
                                    P_BOTTOM_MAGAZINE = 5;
    }

    public static final class IntakeConstants
    {
        public static final int     P_INTAKE_ARM = 31,
                                    P_INTAKE_BELT = 30;
        
        public static final double  C_INTAKE_ARM_VOLTAGE = 6, //In Volts
                                    C_INTAKE_BELT_VOLTAGE = 12; //In Volts

    }

}
