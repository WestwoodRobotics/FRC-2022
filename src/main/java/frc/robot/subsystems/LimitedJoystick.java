package frc.robot.subsystems;

import static frc.robot.Constants.C_JOYSTICK_EASE_SPEED;

public class LimitedJoystick {
    public double posX;
    public double posY;

    private double lastX;
    private double lastY;

    private final double limit = C_JOYSTICK_EASE_SPEED;

    public LimitedJoystick() {
        this.posX = 0;
        this.posY = 0;

        this.lastX = 0;
        this.lastY = 0;
    }

    public void computeX(double x) {
        double change = x - this.lastX;
        if (change > limit) change = limit;
        if (change < -limit) change = -limit;

        this.posX += change;
        this.lastX = x;
    }

    public void computeY(double y) {
        double change = y - this.lastY;
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