import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

    public class Game extends JFrame {
        final static int WIDTH = 500, HEIGHT = 550;

        List<Thing> everyThing = new ArrayList<>();
        Player player1 = new Player("Player 1", 10,30);
        Player player2 = new Player("Player 2" , 400,30);
        List<Shot> shotsInTheAir = new ArrayList<>();
        List<Wall> walls = new LinkedList<>();

        Game() {
            this.setSize(Game.WIDTH, Game.HEIGHT);
            Wall leftEdge = new Wall(0, 50, Game.HEIGHT-50, true);
            this.everyThing.add(leftEdge);
            this.walls.add(leftEdge);
            Wall topEdge = new Wall(0, 50, Game.WIDTH, false);
            this.everyThing.add(topEdge);
            this.walls.add(topEdge);
            Wall rightedge = new Wall(490, 50, Game.HEIGHT-50, true);
          this.everyThing.add(rightedge);
            this.walls.add(rightedge);
            Wall downedge = new Wall(0, 540, Game.WIDTH, false);
           this.everyThing.add(downedge);
            this.walls.add(downedge);

            this.player1.newRound(false);
            this.player2.newRound(false);
            this.player1.tank.x += 50;
            this.everyThing.add(player1.getTank());
            this.everyThing.add(player2.getTank());
            this.everyThing.add(player1.board);
            this.everyThing.add(player2.board);

        }

        void updateState() {
            boolean contact= false;
            Tank p1Tank = this.player1.getTank();
            Tank p2Tank = this.player2.getTank();
            for (int n = 0; (n < shotsInTheAir.size() && !contact); n++) {
                Shot shot = shotsInTheAir.get(n);
                for (Wall wall : this.walls) {
                    if (p2Tank.contacts(shot)) {
                        contact = true;
                        break;
                    }
                    if (wall.contacts(shot))
                    {
                        shot.bounceAgainst(wall);
                        break;
                    }
                }
                if(contact)
                    break;
                shot.step();
            }
            if(contact)
            {
                this.player1.newRound(true);
                 this.player2.newRound(false);
               while(shotsInTheAir.size()!=0)
                {
                    this.everyThing.remove(shotsInTheAir.get(0));
                   this.shotsInTheAir.remove(shotsInTheAir.get(0));
               }
            }

            this.shotsInTheAir.forEach(Shot::growOld);
                for (int n = 0; n < shotsInTheAir.size(); n++) {
                    Shot shot = shotsInTheAir.get(n);
                    if (shot.isDead()) {
                        shotsInTheAir.remove(shot);
                        everyThing.remove(shot);
                    }

                }

                GameActionListener listener = (GameActionListener)
                        this.getKeyListeners()[0];
                this.addKeyListener(listener);
                if (listener.p1Move ) {
                  if(player1.tank.contacts(p2Tank))
                     p1Tank.direction = Math.PI + p1Tank.direction;
                    for (Wall wall : this.walls) {
                            if(p1Tank.rec.intersects(wall.rec))
                                p1Tank.bounceAgainst(wall);
                    }
                    p1Tank.step();
                }


                if (listener.p1Right)
                    p1Tank.turnRight();

                if (listener.p1Left)
                    p1Tank.turnLeft();

                if (listener.p1Fire) {
                    Shot shot = new Shot(
                            p1Tank.getGunX(), p1Tank.getGunY(), p1Tank.direction
                    );
                    this.shotsInTheAir.add(shot);
                    this.everyThing.add(shot);
                }

            }

            public void paint (Graphics graphics){
                super.paint(graphics);
                this.everyThing.forEach(thing -> thing.draw(graphics));
                Toolkit.getDefaultToolkit().sync();
            }

         static void game()
        {
            Game game = new Game();
            game.addKeyListener(new GameActionListener());
            game.setVisible(true);
            new Timer(10,
                    e->{ game.updateState(); game.repaint();
                    } ).start();
        }
        }


