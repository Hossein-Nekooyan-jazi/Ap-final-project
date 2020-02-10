import java.awt.*;

public class Wall extends Thing {
    final static int WIDTHtype1 =25; // px
    final static int WIDTHtype2=1;
    boolean IStype1;
    int i1, i2, j;
    boolean isVertical;

    public Wall(int x, int y, int length, boolean isVertical,boolean type) {
        super(null);
        this.isVertical = isVertical;
        this.IStype1 = type;
        if(IStype1) {
            if (isVertical) {
                this.i1 = y;
                this.i2 = y + length;
                this.j = x;
                this.shape = new Rectangle(x, y, Wall.WIDTHtype1, length);

            } else {
                this.i1 = x;
                this.i2 = x + length;
                this.j = y;
                this.shape = new Rectangle(x, y, length, Wall.WIDTHtype1);
            }
        }
        else{
            if (isVertical) {
                this.i1 = y;
                this.i2 = y + length;
                this.j = x;
                this.shape = new Rectangle(x, y, Wall.WIDTHtype2, length);

            } else {
                this.i1 = x;
                this.i2 = x + length;
                this.j = y;
                this.shape = new Rectangle(x, y, length, Wall.WIDTHtype2);
            }
        }
    }

    public void draw(Graphics graphics) {

        graphics.setColor(Color.BLACK);
        int width = (this.isVertical) ? Wall.WIDTHtype1 : this.i2 - this.i1;
        int height = (this.isVertical) ? this.i2 - this.i1 : Wall.WIDTHtype1;
        graphics.fillRect(this.getX(), this.getY(), width, height);
        this.shape = new Rectangle(this.getX(), this.getY(), width, height);
    }
}
