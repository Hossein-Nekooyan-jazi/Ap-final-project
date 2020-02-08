import util.PowerUptype;

import java.awt.*;

public class Powrup extends Thing {

    PowerUptype type;

    Powrup() {
        super(null);
        int[] cordinates = Game.roundmx();
        this.shape = new Rectangle(cordinates[0], cordinates[1], 10, 10);
        if (Math.round(Math.random() * 2) % 2 == 0)
            this.type = PowerUptype.FragBomb;
        else
            this.type = PowerUptype.Mine;

    }

    @Override
    void draw(Graphics2D graphics) {
        if (this.type == PowerUptype.FragBomb)
            graphics.setColor(Color.blue);
        else
            graphics.setColor(Color.RED);

        super.draw(graphics);
    }
}
