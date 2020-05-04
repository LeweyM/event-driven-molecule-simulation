public class Event implements Comparable<Event> {

    public final double time;
    public final Particle a;
    public final Particle b;
    private final int countA;
    private final int countB;

    public Event(double t, Particle a, Particle b) {
        this.time = t;
        this.a    = a;
        this.b    = b;
        if (a != null) countA = a.count();
        else           countA = -1;
        if (b != null) countB = b.count();
        else           countB = -1;

    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.time, o.time);
    }

    public boolean isValid() {
        if (a != null && a.count() != countA) return false;
        if (b != null && b.count() != countB) return false;
        return true;
    }
}
