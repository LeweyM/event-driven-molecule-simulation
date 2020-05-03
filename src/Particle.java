import processing.core.PApplet;

import java.util.Random;

public class Particle {
    private float px;
    private float py;
    private float radius;

    public Particle(Random r) {
        this.px = r.nextFloat() * 400;
        this.py = r.nextFloat() * 400;
        this.radius = 10;
    }

    public void draw(PApplet sketch) {
        sketch.ellipse(px, py, radius, radius);
    }

}
