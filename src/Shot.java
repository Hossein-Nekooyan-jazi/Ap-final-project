import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Shot extends MovingThing {
    final static int RADIUS = 5;
    final static int LIFE = 100;

    int age = Shot.LIFE;

    Shot(int x, int y, double direction) {
        super(10, 0, direction, new Ellipse2D.Double(x - Shot.RADIUS, y - Shot.RADIUS,
                Shot.RADIUS * 2, Shot.RADIUS * 2));
    }

    void draw(Graphics2D graphics) {
        graphics.setColor(Color.RED);
        super.draw(graphics);
    }

    void growOld() {
        this.age--;
    }

    boolean isDead() {
        return this.age <= 0;
    }
}
