import java.awt.*;

public class Tank extends MovingThing {
    final static int RADIUS = 25;
    final static int GUN_LENGTH = 30;
    BoardofScores board;
    Tank(int x, int y, double direction) {
        super(x, y, 2f, 0.1f, direction , new Rectangle(x - Tank.RADIUS,
                y - Tank.RADIUS,
                Tank.RADIUS * 2,
                Tank.RADIUS * 2));
    }

    public void draw(Graphics graphics) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(
                    this.x - Tank.RADIUS,
                    this.y - Tank.RADIUS,
                    Tank.RADIUS * 2,
                    Tank.RADIUS * 2
            );
            this.rec = new Rectangle(this.x - Tank.RADIUS,
                    this.y - Tank.RADIUS,
                    Tank.RADIUS * 2,
                    Tank.RADIUS * 2);
            graphics.drawLine(this.x, this.y, this.getGunX(), this.getGunY());
            Toolkit.getDefaultToolkit().sync();

    }
    int getGunX() {
        return(int) Math.round(this.x +
                (Tank.GUN_LENGTH * Math.cos(this.direction)));
    }

    int getGunY() {  return(int) Math.round(this.y +
            (Tank.GUN_LENGTH * Math.sin(this.direction)));
    }
    boolean contacts(MovingThing thing)
    {

        if(this.rec.intersects(thing.rec))
            return true;
        else
            return false;
    }
    int getRadius(){return Tank.RADIUS;}
}