import edu.princeton.cs.algs4.StdRandom;
import processing.core.PApplet;

public class Particle {
    private double vx;
    private double vy;
    private double px;
    private double py;
    private final double radius;
    private final double mass;
    private int count;

    public Particle() {
        px     = StdRandom.uniform(0.0, 1.0);
        py     = StdRandom.uniform(0.0, 1.0);
        vx     = StdRandom.uniform(-0.005, 0.005);
        vy     = StdRandom.uniform(-0.005, 0.005);
        radius = 0.02;
        mass   = 0.5;
    }

    public void move(double dt) {
        px += (dt * vx);
        py += (dt * vy);
    }

    public void bounceOff(Particle other) {
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

        this.count++;
        other.count++;
    }

    public double timeToHit(Particle that) {
        if (this == that) return Double.POSITIVE_INFINITY;
        double dx  = that.px - this.px;
        double dy  = that.py - this.py;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        if (dvdr > 0) return Double.POSITIVE_INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        if (dvdv == 0) return Double.POSITIVE_INFINITY;
        double drdr = dx*dx + dy*dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        // if (drdr < sigma*sigma) StdOut.println("overlapping particles");
        if (d < 0) return Double.POSITIVE_INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    public void draw(PApplet sketch) {
        sketch.ellipse((float) px * 400, ((float) py * 400), ((float) radius * 400), ((float) radius * 400));
    }

    public double kineticEnergy() {
        return 0.5 * mass * (vx*vx + vy*vy);
    }

    public double timeToHitVerticalWall() {
        if      (vx > 0) return (1.0 - px - radius) / vx;
        else if (vx < 0) return (radius - px) / vx;
        else             return Double.POSITIVE_INFINITY;
    }

    public double timeToHitHorizontalWall() {
        if      (vy > 0) return (1.0 - py - radius) / vy;
        else if (vy < 0) return (radius - py) / vy;
        else             return Double.POSITIVE_INFINITY;
    }

    public void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }

    public void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }

    public int count() {
        return count;
    }
}
