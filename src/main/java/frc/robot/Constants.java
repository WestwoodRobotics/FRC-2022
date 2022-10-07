// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Filesystem;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 * </p>
 * <p>
 * Prefix definitions:<br>
 * <b>M</b> - Mechanism<br>
 * <b>C</b> - Constant<br>
 * <b>P</b> - Port
 * </p>
 */
public final class Constants {

    /**
     * controller ports
     **/
    public static final int P_LOGITECH_CONTROLLER = 0;

    public static final int P_LOGITECH_CONTROLLER2 = 1;
    public static final int P_LEFT_JOY = 1;
    public static final int P_RIGHT_JOY = 0;

    /**
     * controller constants
     **/
    public static final double C_DEADZONE_CIRCLE = 0.08; // Radius of deadzone circle

    public static final double C_DEADZONE_RECTANGLE = 0.12; // Half width of deadzone rectangle
    public static final double C_JOYSTICK_EASE_SPEED_ACCEL = 0.6;
    public static final double C_JOYSTICK_EASE_SPEED_BRAKE = 0.2;

    public static final class HangarConstants {
        public static final int P_WINCH_MOTOR = 20;
        public static final int P_STATIC_HOOKS = 7;
        public static final double C_WINCH_CIRCUMFERENCE = 94.7458; // mm
        public static final double C_WINCH_HEIGHT = 800;
        public static final double C_WINCH_GEARDIFF = 1.0 / 12; // mm
    }

    public static final class SwerveModuleConstants {
        // CAN ports move on their own?
        /**
         * The CAN IDs of the drive motors
         **/
        public static final int P_FRONT_RIGHT_TURN = 11; // 1

        public static final int P_FRONT_RIGHT_DRIVE = 12; // 2
        public static final int P_FRONT_LEFT_TURN = 13; // 3
        public static final int P_FRONT_LEFT_DRIVE = 14; // 4
        public static final int P_REAR_LEFT_TURN = 15; // 5
        public static final int P_REAR_LEFT_DRIVE = 16; // 6
        public static final int P_REAR_RIGHT_TURN = 17; // 7
        public static final int P_REAR_RIGHT_DRIVE = 18; // 8

        /**
         * CANcoder ports
         **/
        public static final int P_FRONT_RIGHT_ENCODER = 1;

        public static final int P_FRONT_LEFT_ENCODER = 2;
        public static final int P_BACK_RIGHT_ENCODER = 4;
        public static final int P_BACK_LEFT_ENCODER = 3;

        public static final String C_ENCODER_OFFSETS_FILE_PATH =
                Filesystem.getOperatingDirectory().getPath() + "/turnEncoderOffsets.txt";

        /**
         * Chassis constants, signified in meters
         **/
        public static final double C_DISTANCE_FROM_CENTER_WIDTH = 0.4953 / 2.0;

        public static final double C_DISTANCE_FROM_CENTER_LENGTH = 0.6477 / 2.0; // meters

        /**
         * Module constants
         **/
        public static final double C_DRIVE_MOTOR_GEAR_RATIO = 6.75;

        public static final double C_TURNING_MOTOR_GEAR_RATIO = 12.8;
        public static final double C_WHEELS_DIAMETER = 0.1016; // meters
        public static final double C_WHEELS_CIRCUMFERENCE = Math.PI * C_WHEELS_DIAMETER;
        public static final double C_MAX_VOLTAGE = 12;

        public static final int C_ENCODER_CPR = 2048;

        public static final double C_DRIVE_ENCODER_DISTANCE_PER_PULSE = (C_WHEELS_DIAMETER * Math.PI)
                / ((double) C_ENCODER_CPR * SwerveModuleConstants.C_DRIVE_MOTOR_GEAR_RATIO);
        public static final double C_kTURNING_ENCODER_DISTANCE_PER_PULSE =
                (2.0 * Math.PI) / (C_ENCODER_CPR * C_TURNING_MOTOR_GEAR_RATIO); // Assumes
        // the encoders are on a 1:1 reduction with the module shaft.

        /**
         * Motor constants
         **/
        public static final double C_MAX_MOTOR_ANGULAR_SPEED = 0.02 * 2 * Math.PI; // radians/sec

        public static final double C_MAX_MOTOR_ANGULAR_ACCELERATION = 0.02 * 2 * Math.PI; // radians/s^2
        public static final double C_EDGES_PER_REVOLUTION = 2048; // encoder edges per revolution

        /**
         * Drive PID Controllers
         */
        public static final PIDController m_rRDrivePID = new PIDController(0.0000005, 0.000000005, 0.0000002);

        public static final PIDController m_rLDrivePID = new PIDController(0.0000005, 0.000000005, 0.0000002);
        public static final PIDController m_fLDrivePID = new PIDController(0.0000007, 0.00000001, 0.0000004);
        public static final PIDController m_fRDrivePID = new PIDController(0.0000007, 0.00000001, 0.0000004);
        /**
         * Turn PID Controllers
         **/
        public static final PIDController m_rRTurnPID = new PIDController(0.225, 0.002, 0.01); // double p until

        public static final PIDController // oscillations then
                // 1/10 for d, increase
                // until no oscillations then 1/100 for i
                m_rLTurnPID = new PIDController(0.2, 0.002, 0.01);
        public static final PIDController m_fLTurnPID = new PIDController(0.2, 0.002, 0.01);
        public static final PIDController m_fRTurnPID = new PIDController(0.205, 0.002, 0.01);
        // P=0.8, I=0, D=0
        // 0.6, 0.006, 0.005

        public static final SimpleMotorFeedforward m_rRDriveFeedForward =
                new SimpleMotorFeedforward(0.0352094709, 0.00004316248515, 0.00000000002113902343);
        public static final SimpleMotorFeedforward m_rLDriveFeedForward =
                new SimpleMotorFeedforward(0.0357376904, 0.00004255308416, 0.00000000003524346109);
        public static final SimpleMotorFeedforward m_fLDriveFeedForward =
                new SimpleMotorFeedforward(0.0361192778, 0.00004295102713, 0.00000000002950698504);
        public static final SimpleMotorFeedforward m_fRDriveFeedForward =
                new SimpleMotorFeedforward(0.0355919531, 0.00004297063293, 0.0000000000355919531);

        /**
         * PID constants
         **/
        public static final double C_DRIVE_kP = 0; // 2.3

        public static final double C_DRIVE_kI = 0; // 20
        public static final double C_DRIVE_kD = 0; // 0.03
        public static final double C_TURN_kP = 3.3; // 3.3 | 3.8 * Math.PI/180
        public static final double C_TURN_kI = 9.4; // 9.4
        public static final double C_TURN_kD = 0.15; // 0.15

        // Feedfoward constants drive motor
        // tiles
        public static final double C_DRIVE_kA = 0.4; // 0.4
        public static final double C_DRIVE_kS = 0.0; // .8 old value
        public static final double C_DRIVE_kV = 0.0;

        // Feedforward constants turn motor
        // tiles
        public static final double C_TURN_kA = 0.0;
        public static final double C_TURN_kS = 0.65; // 0.65
        public static final double C_TURN_kV = 0;
    }

    public static final class DriveConstants {
        public static final double C_MAX_SPEED = 1; // meters per second, controls mapped to this by direct
        public static final double // multiplication
                C_MAX_ANGULAR_SPEED = 1.3 * Math.PI;
        public static final double C_kPXVision = 0.015; // radians per second
    }

    //    public static double map(double input, double min, double max, double outMin, double outMax) {
    //        return (input - min) / (max - min) * (outMax - outMin) + outMin;
    //    }

    public static class VisionConstants {
        public static final double C_MOUNTING_ANGLE = 14; // degrees
        public static final double C_GOAL_HEIGHT = 2.642; // meters
        public static final double C_ROBOT_HEIGHT = 0.457; // meters
        public static final double C_GOAL_DISTANCE = 7.919718984; // meters
        public static final double C_ACCEPTABLE_GOAL_OFFSET = .3;
        public static final double C_ACCEPTABLE_DEGREE_DISTANCE = 1; // acceptable degree offset for
        // alignment

        //        public static double getHoodAngle(double distance) {
        //            return distance; // use linear regression
        //        }
    }

    public static final class ShooterConstants {
        // CAN Ports
        public static final int P_LEFT_SHOOTER = 21;
        public static final int P_RIGHT_SHOOTER = 22;
        // P_HOOD = 20,
        // P_HOOD_LIMIT = 1;

        public static final double C_GEAR_RATIO = 18.0 / 64.0;

        public static final double C_MIN_ANGLE = 0;
        public static final double C_MAX_ANGLE = 26;

        public static final PIDController m_PID = new PIDController(0.000208, 0.00000208, 0.00000208);

        public static final double[][] C_SHOT_MAP = {
            {13.69, 5600}, {9.9, 6200}, {4.6, 6500}, {0, 6860}, {-3.884, 7370}, {-7.61, 8130}, {-11, 9700}
        };
        public static final SimpleMotorFeedforward m_FeedForward =
                new SimpleMotorFeedforward(0.074769211, 0.00003846418, 0.0000000000670188214223);

        // shooter PID constants
        /*
         * public static final double C_LEFT_SHOOTER_kP = 0.0, C_LEFT_SHOOTER_kI = 0.0,
         * C_LEFT_SHOOTER_kD = 0.0;
         *
         * public static final double C_RIGHT_SHOOTER_kP = 0.01, C_RIGHT_SHOOTER_kI =
         * 0.0, C_RIGHT_SHOOTER_kD = 0.0;
         */
    }

    public static final class MagazineConstants {
        // motor ports, denoted with 1, then number
        public static final int P_TOP_MAGAZINE = 23;
        public static final int P_BOTTOM_MAGAZINE = 24;

        public static final double C_BELT_MAX_SPEED = 0.25;
    }

    public static final class IntakeConstants {
        public static final int P_INTAKE_ARM = 31;
        public static final int P_INTAKE_BELT = 30;
        public static final double C_INTAKE_ARM_VOLTAGE = 7; // In Volts, 10 Volts normally
        public static final double C_INTAKE_BELT_VOLTAGE = 5; // In Volts
    }
}
