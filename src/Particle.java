import processing.core.PApplet;

import java.util.Random;

public class Particle {
    private double vx;
    private double vy;
    private double px;
    private double py;
    private final double radius;

    public Particle(Random r) {
        this.vx = r.nextDouble() - .5;
        this.vy = r.nextDouble() - .5;
        this.px = r.nextDouble() * 400;
        this.py = r.nextDouble() * 400;
        this.radius = 10;
    }

    public void move(double dt) {
        if (px - (radius/2) < 0 || px + radius/2 > 400) vx *= -1;
        if (py - (radius/2) < 0 || py + radius/2 > 400) vy *= -1;

        px = px + (dt * vx);
        py = py + (dt * vy);
    }

    public void draw(PApplet sketch) {
        sketch.ellipse((float) px, ((float) py), ((float) radius), ((float) radius));
    }

}
