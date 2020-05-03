import processing.core.PApplet;

import java.util.Random;

public class Simulation {

    private final Particle[] particles;

    public Simulation() {
        particles = new Particle[40];
        Random randomGenerator = new Random();
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle(randomGenerator);
        }
    }

    public void draw(PApplet sketch) {
        for (Particle particle : particles) {
            //naive quadratic collision detection
            for (Particle other : particles) {
                if (particle.overlaps(other)) {
                    particle.bounceOff(other);
                }
            }

            particle.move(2);
            particle.draw(sketch);
        }
    }
}
