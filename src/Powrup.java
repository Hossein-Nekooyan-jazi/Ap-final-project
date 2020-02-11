import util.PowerUptype;

import java.awt.*;

public class Powrup extends Thing {

    PowerUptype type;

    Powrup() {
        super(null);
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

    void setShape( Shape shape)
    {
        this.shape = shape;
    }
}
