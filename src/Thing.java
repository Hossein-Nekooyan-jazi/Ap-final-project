import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class Thing {
    Shape shape;

    Thing(Shape shape) {
        this.shape = shape;
    }

    boolean contacts(Thing thing) {
        Area i = new Area(this.shape);
        i.intersect(new Area(thing.shape));
        return ! i.isEmpty();
    }

    void draw(Graphics2D graphics) {
        graphics.fill(this.shape);
    }

    int getX() {
        return (int) this.shape.getBounds2D().getCenterX();
    }

    int getY() {
        return (int) this.shape.getBounds2D().getCenterY();
    }

    void move(int x, int y) {
        AffineTransform t = new AffineTransform();
        t.translate(x, y);
        this.shape = t.createTransformedShape(this.shape);
    }

    void jump(int x, int y) {
        AffineTransform t = new AffineTransform();
        t.translate(x - this.getX(), y - this.getY());
        this.shape = t.createTransformedShape(this.shape);
    }
}