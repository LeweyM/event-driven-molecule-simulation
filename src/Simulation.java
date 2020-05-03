import processing.core.PApplet;

import java.util.Random;

public class Simulation {

    private final Particle[] particles;

    public Simulation() {
        particles = new Particle[100];
        Random randomGenerator = new Random();
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle(randomGenerator);
        }
    }

    public void draw(PApplet sketch) {
        for (Particle particle : particles) {
            particle.draw(sketch);
        }
    }
}
