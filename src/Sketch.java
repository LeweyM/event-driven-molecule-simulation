import processing.core.PApplet;

public class Sketch extends PApplet {

    @Override
    public void settings() {
        size(400,400);
    }

    @Override
    public void draw() {
        background(128);
    }

    public static void main (String... args) {
        Sketch pt = new Sketch();
        PApplet.runSketch(new String[]{"Event Simulation Sketch"}, pt);
    }

}
