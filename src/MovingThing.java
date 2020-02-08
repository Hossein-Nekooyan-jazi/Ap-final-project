import java.awt.*;
import java.awt.geom.AffineTransform;

public class MovingThing extends Thing {
    double direction; // rad
    float velocity; // px/step
    float angularVelocity; // rad/step

    MovingThing(float v, float aV, double d, Shape shape) {
        super(shape);
        this.velocity = v;
        this.direction = d;
        this.angularVelocity = aV;
    }

    private void changeDirection(double amount) {
        this.direction = (this.direction + amount) % (2 * Math.PI);
        AffineTransform r = new AffineTransform();
        r.rotate(amount, this.getX(), this.getY());
        this.shape = r.createTransformedShape(this.shape);
    }

    public void turnLeft() {
        this.changeDirection(this.angularVelocity);
    }

    public void turnRight() {
        this.changeDirection(-this.angularVelocity);
    }

    void step() {
        AffineTransform t = new AffineTransform();
        t.translate(
                Math.round(this.velocity * Math.cos(this.direction)),
                Math.round(this.velocity * Math.sin(this.direction))
        );
        this.shape = t.createTransformedShape(this.shape);
    }

    void bounceAgainst(Wall wall) {
        if (wall.isVertical)
            this.changeDirection(-2 * this.direction + Math.PI);
        else

            this.changeDirection(-2 * this.direction);
    }
}
