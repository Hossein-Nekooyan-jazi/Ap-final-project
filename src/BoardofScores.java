import java.awt.*;

public class BoardofScores extends Thing {
    int point;
    String name;

    BoardofScores(int point, int x, int y, String string) {
        super(new Rectangle(x, y, 20, 20));
        this.name = string;
        this.point = point;
    }

    void draw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawString(this.name, this.getX() - 10, this.getY() - 10);
        super.draw(graphics);
        graphics.setColor(Color.WHITE);
        graphics.drawString(String.valueOf(this.point), this.getX(), this.getY());
    }
}
