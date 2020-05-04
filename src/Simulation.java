import edu.princeton.cs.algs4.MinPQ;
import processing.core.PApplet;

public class Simulation {

    private final Particle[] particles;
    private final PApplet sketch;
    private MinPQ<Event> pq;
    private double t;
    private double HZ = 0.5;

    public Simulation(PApplet s) {
        sketch = s;
        particles = new Particle[100];
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle();
        }

        pq = new MinPQ<>();

        for (int i = 0; i < particles.length; i++) {
            predict(particles[i], 100000);
        }
        pq.insert(new Event(0, null, null));
    }

    public void draw() {
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw(sketch);
        }
        if (t < 100000) {
            pq.insert(new Event(t + 1.0 / HZ, null, null));
        }
        System.out.println(t);
    }

    public void simulateFrame(double limit) {
        // the main event-driven simulation loop
        while (!pq.isEmpty()) {

            // get impending event, discard if invalidated
            Event e = pq.delMin();
            if (!e.isValid()) continue;
            Particle a = e.a;
            Particle b = e.b;

            // physical collision, so update positions, and then simulation clock
            for (int i = 0; i < particles.length; i++)
                particles[i].move(e.time - t);
            t = e.time;

            // process event
            if      (a != null && b != null) a.bounceOff(b);              // particle-particle collision
            else if (a != null && b == null) a.bounceOffVerticalWall();   // particle-wall collision
            else if (a == null && b != null) b.bounceOffHorizontalWall(); // particle-wall collision
            else if (a == null && b == null) {
                return;
            }

            // update the priority queue with new collisions involving a or b
            predict(a, limit);
            predict(b, limit);
        }
    }

    private void predict(Particle particle, double limit) {
        if (particle == null) return;

        // particle-particle collisions
        for (int i = 0; i < particles.length; i++) {
            double dt = particle.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, particle, particles[i]));
        }

        double dtX = particle.timeToHitVerticalWall();
        double dtY = particle.timeToHitHorizontalWall();
        pq.insert(new Event(t + dtX, particle, null));
        pq.insert(new Event(t + dtY, null, particle));
    }

}
