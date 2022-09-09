package frc.robot.subsystems;

import static frc.robot.Constants.C_JOYSTICK_EASE_SPEED_ACCEL;
import static frc.robot.Constants.C_JOYSTICK_EASE_SPEED_BRAKE;

public class LimitedJoystick {
    public double posX;
    public double posY;

    private double lastX;
    private double lastY;

    private final double accLim = C_JOYSTICK_EASE_SPEED_ACCEL;
    private final double brakeLim = C_JOYSTICK_EASE_SPEED_BRAKE;

    public LimitedJoystick() {
        this.posX = 0;
        this.posY = 0;

        this.lastX = 0;
        this.lastY = 0;
    }

    public void computeX(double x) {
        double change = x - this.posX;

        boolean accelDirection; // 0 -> Negative, 1 -> Positive

        if ()

        if (change > limit) change = limit;
        if (change < -limit) change = -limit;

        this.posX += change;
        this.lastX = x;
    }

    public void computeY(double y) {
        double change = y - this.posY;
        if (change > limit) change = limit;
        if (change < -limit) change = -limit;

        this.posY += change;
        this.lastY = y;
    }

    public void compute(double x, double y) {
        this.computeX(x);
        this.computeY(y);
    }
}
