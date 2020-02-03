import util.PowerUptype;

import java.awt.*;

public class Powrup extends Thing {

    PowerUptype type;
     Powrup()
    {
        super( 0,0,new Rectangle(0,0,10,10));
          int [] cordinates = Game.roundmx();
          this.x=cordinates[0];this.y= cordinates[1];this.rec = new Rectangle(this.x,this.y,10,10);
        if (Math.round(Math.random()*2) %2==0)
            this.type= PowerUptype.FragBomb;
        else
            this.type=PowerUptype.Mine;

    }
    @Override
    void draw(Graphics graphics) {
        if(this.type==PowerUptype.FragBomb)
            graphics.setColor(Color.blue);
        else
            graphics.setColor(Color.RED);

        graphics.fillRect(this.x,this.y , 10,10);
    }
}
