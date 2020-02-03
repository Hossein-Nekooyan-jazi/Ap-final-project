import java.awt.*;

public class Shot extends MovingThing {
    final static int RADIUS = 10;
    final static int LIFE = 100;

    int age = Shot.LIFE;

    Shot(int x, int y, double direction) {
        super(x, y, 3, 0, direction, new Rectangle(x - Shot.RADIUS, y - Shot.RADIUS,
                Shot.RADIUS * 2, Shot.RADIUS * 2));
    }

    void draw(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        this.rec = new Rectangle(this.x - Shot.RADIUS, this.y - Shot.RADIUS,
                Shot.RADIUS * 2, Shot.RADIUS * 2);
        graphics.fillRect(this.x - Shot.RADIUS, this.y - Shot.RADIUS,
                Shot.RADIUS * 2, Shot.RADIUS * 2);
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    void growOld() {
        this.age--;
    }

    boolean isDead() {
        return this.age <= 0;
    }

    int getRadius() {
        return Shot.RADIUS;
    }
}
