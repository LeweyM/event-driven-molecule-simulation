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
        this.radius = 10;
        this.vx = (r.nextDouble() - .5);
        this.vy = (r.nextDouble() - .5);
        this.px = r.nextDouble() * (400 - radius*2) + radius;
        this.py = r.nextDouble() * (400 - radius*2) + radius;
        this.mass = 100;
    }

    public void move(double dt) {
        if (px - (radius/2) < 0 || px + radius/2 > 400) vx *= -1;
        if (py - (radius/2) < 0 || py + radius/2 > 400) vy *= -1;

        px = px + (dt * vx);
        py = py + (dt * vy);
    }

    public void bounceOff(Particle other) {

        //roll back clock here...
        double t = timeToHit(other);
        move(t);

        double dx  = other.px - this.px;
        double dy  = other.py - this.py;
        double dvx = other.vx - this.vx;
        double dvy = other.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;             // dv dot dr
        double dist = this.radius + other.radius;   // distance between particle centers at collison

        // magnitude of normal force
        double magnitude = 2 * this.mass * other.mass * dvdr / ((this.mass + other.mass) * dist);

        // normal force, and in x and y directions
        double fx = magnitude * dx / dist;
        double fy = magnitude * dy / dist;

        // update velocities according to normal force
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        other.vx -= fx / other.mass;
        other.vy -= fy / other.mass;

        // roll forward again
        move(-t);
    }

    public double timeToHit(Particle other) {
        if (this == other) return Double.POSITIVE_INFINITY;
        double dx  = other.px - this.px;
        double dy  = other.py - this.py;
        double dvx = other.vx - this.vx;
        double dvy = other.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        if (dvdr > 0) return Double.POSITIVE_INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        if (dvdv == 0) return Double.POSITIVE_INFINITY;
        double drdr = dx*dx + dy*dy;
        double sigma = this.radius + other.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
//        if (d < 0) return Double.POSITIVE_INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    public void draw(PApplet sketch) {
        sketch.ellipse((float) px, ((float) py), ((float) radius), ((float) radius));
    }

    public boolean overlap(Particle other) {
        if (this == other) return false;
        double dx  = other.px - this.px;
        double dy  = other.py - this.py;
        double drdr = dx*dx + dy*dy;
        double sigma = this.radius + other.radius;
        return (drdr < sigma*sigma);
    }

    public double kineticEnergy() {
        return 0.5 * mass * (vx*vx + vy*vy);
    }
}
