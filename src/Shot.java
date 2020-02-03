import java.awt.*;

public class Shot extends MovingThing {
    final static int RADIUS = 10;
    final static int LIFE = 100;

    int age = Shot.LIFE;

    Shot(int x, int y, double direction) {
        super(3, 0, direction, new Rectangle(x - Shot.RADIUS, y - Shot.RADIUS,
                Shot.RADIUS * 2, Shot.RADIUS * 2));
    }

    void draw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        super.draw(graphics);
    }

    void growOld() {
        this.age--;
    }

    boolean isDead() {
        return this.age <= 0;
    }
}
