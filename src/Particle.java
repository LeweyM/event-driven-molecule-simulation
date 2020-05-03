import processing.core.PApplet;

import java.util.Random;

public class Particle {
    private double vx;
    private double vy;
    private double px;
    private double py;
    private final double radius;
    private final double mass;

    public Particle(Random r) {
        this.radius = 15;
        this.vx = r.nextDouble() - .5;
        this.vy = r.nextDouble() - .5;
        this.px = r.nextDouble() * (400 - radius*2) + radius;
        this.py = r.nextDouble() * (400 - radius*2) + radius;
        this.mass = 1;
    }

    public void move(double dt) {
        if (px - (radius/2) < 0 || px + radius/2 > 400) vx *= -1;
        if (py - (radius/2) < 0 || py + radius/2 > 400) vy *= -1;

        px = px + (dt * vx);
        py = py + (dt * vy);
    }

    public void bounceOff(Particle other) {
        double dx = other.px - this.px, dy = other.py - this.py;
        double dvx = other.vx - this.vx, dvy = other.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        double dist = radius + other.radius;
        double J = 2 * mass * other.mass * dvdr / ((mass + other.mass) * dist);
        double Jx = J * dx / dist;
        double Jy = J * dy / dist;

        this.vx += Jx / this.mass;
        this.vy += Jy / this.mass;
        other.vx += Jx / other.mass;
        other.vy += Jy / other.mass;
    }

    public void draw(PApplet sketch) {
        sketch.ellipse((float) px, ((float) py), ((float) radius), ((float) radius));
    }

    public boolean overlaps(Particle other) {
        return Math.abs(px - other.px) < radius
            && Math.abs(py - other.py) < radius;
    }
}
