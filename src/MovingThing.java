import java.awt.*;

public abstract class MovingThing extends Thing{
         double direction; // rad
         float velocity; // px/step
         float angularVelocity; // rad/step

         MovingThing(int x, int y, float v, float aV, double d , Rectangle rec) {
         super(x, y,rec);
         this.velocity = v;
         this.direction = d;
         this.angularVelocity = aV;
         }

         private void changeDirection(double amount) {
         this.direction = this.direction + amount;
        }

         public void turnLeft() {
         this.changeDirection(this.angularVelocity);
        }

         public void turnRight() {
         this.changeDirection(- this.angularVelocity);
        }
         void step() {
          this.x += Math.round(this.velocity * Math.cos(this.direction));
         this.y += Math.round(this.velocity * Math.sin(this.direction));
        }
    void bounceAgainst(Wall wall)
    {
        if (wall.isVertical)
                this.direction = Math.PI - this.direction;
        else

                this.direction =  - this.direction;
    }
    abstract int getRadius();

    }
