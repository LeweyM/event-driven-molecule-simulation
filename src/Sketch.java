import processing.core.PApplet;

public class Sketch extends PApplet {

    private Simulation simulation;

    @Override
    public void settings() {
        size(400,400);
        simulation = new Simulation();
    }

    @Override
    public void draw() {
        background(128);
        simulation.draw(this);
    }

    public static void main (String... args) {
        Sketch pt = new Sketch();
        PApplet.runSketch(new String[]{"Event Simulation Sketch"}, pt);
    }

}
