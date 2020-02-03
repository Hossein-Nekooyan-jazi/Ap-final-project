import java.awt.*;

public class Tank extends MovingThing {
    final static int WIDTH = 25;
    final static int HEIGHT = 15;
    final static int GUN_LENGTH = 30;

    Tank(int x, int y, double direction) {
        super(2f, 0.06f, direction, new Rectangle(x - Tank.WIDTH/2,
                y - Tank.HEIGHT/2,
                Tank.WIDTH,
                Tank.HEIGHT));
    }

    public void draw(Graphics2D graphics) {
        graphics.drawLine(this.getX(), this.getY(), this.getGunX(), this.getGunY());
        super.draw(graphics);
    }

    int getGunX() {
        return (int) Math.round(this.getX() +
                (Tank.GUN_LENGTH * Math.cos(this.direction)));
    }

    int getGunY() {
        return (int) Math.round(this.getY() +
                (Tank.GUN_LENGTH * Math.sin(this.direction)));
    }
}