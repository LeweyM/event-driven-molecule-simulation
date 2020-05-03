import processing.core.PApplet;

import java.util.Random;

public class Particle {
    private float vx;
    private float vy;
    private float px;
    private float py;
    private final float radius;

    public Particle(Random r) {
        this.vx = r.nextFloat();
        this.vy = r.nextFloat();
        this.px = r.nextFloat() * 400;
        this.py = r.nextFloat() * 400;
        this.radius = 10;
    }

    public void move(float dt) {
        px = px + (dt * vx);
        py = py + (dt * vy);
    }

    public void draw(PApplet sketch) {
        sketch.ellipse(px, py, radius, radius);
    }

}
