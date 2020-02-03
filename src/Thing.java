import java.awt.*;

public abstract class Thing {
    int x; //px
    int y;//px
    int prex = 0;
    int prey = 0;
    Rectangle rec;

    Thing(int x, int y, Rectangle rec) {
        this.x = x;
        this.y = y;
        this.rec = rec;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    abstract void draw(Graphics graphics);
}