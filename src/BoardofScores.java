import java.awt.*;

public class BoardofScores extends Thing {
    int point;
    String name;

    BoardofScores(int point, int x, int y, String string) {
        super(x, y, new Rectangle(0, 0, 0, 0));
        this.name = string;
        this.point = point;
    }

    void draw(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawString(this.name, this.x, this.y);
        graphics.fillRect(this.x + 20, this.y, 20, 20);
        graphics.setColor(Color.WHITE);
        graphics.drawString(String.valueOf(this.point), this.x + 20, this.y + 10);
    }
}
