import processing.core.PApplet;

import java.util.Random;

public class Simulation {

    private final Particle[] particles;

    public Simulation() {
        particles = new Particle[100];
        Random randomGenerator = new Random();
        //remove overlapping particles in setup
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle(randomGenerator);
            while (overlapsOtherParticle(particles[i], i)) {
                particles[i] = new Particle(randomGenerator);
            }
        }
    }

    private boolean overlapsOtherParticle(Particle particle, int end) {
        for (int i = 0; i < end; i++) {
            if (particle.overlap(particles[i])) {
                return true;
            }
        }
        return false;
    }

    public void draw(PApplet sketch) {
        double kineticSum = 0;
        for (Particle particle : particles) {

            //naive quadratic collision detection
            for (Particle other : particles) {
                if (particle.overlap(other)) {
                    particle.bounceOff(other);
                }
            }

            particle.move(0.5);
            particle.draw(sketch);

            kineticSum += particle.kineticEnergy();
        }

        System.out.println("kinetic sum: " + kineticSum);
    }
}
