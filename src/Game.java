import util.FileHandler;
import util.PowerUptype;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game extends JFrame {
    final static int WIDTH = 500, HEIGHT = 600;
    int time = 0;
    List<Thing> everyThing = new ArrayList<>();
    Player player1 = new Player("Player 1", 10, 30,0);
    Player player2 = new Player("Player 2", 400, 30,0);
    List<Shot> shot_and_frag1 = new ArrayList<>();
    List<Shot> shot_and_frag2 = new ArrayList<>();
    List<Wall> walls = new LinkedList<>();
    List<Powrup> powrups = new LinkedList<>();
    JLabel shotsalarmplayer1 = new JLabel();
    JLabel shotsalarmplayer2 = new JLabel();
    List<Thing> innerthings = new LinkedList<>();
    static boolean frofbomb_is_on1 = false;
    static boolean frofbomb_is_on2 = false;

    int shotlimit;

    int roundtoWin;

    Game(int shotlimit , int roundtoWin ,int map_number) {
        this.shotlimit = shotlimit;
        this.roundtoWin =roundtoWin;
        this.shotsalarmplayer1.setBounds(60,0,80,20);
        this.shotsalarmplayer2.setBounds(300,2,80,20);
        this.add(shotsalarmplayer1);
        this.add(shotsalarmplayer2);

        JLabel exit = new JLabel();
        exit.setText("E to exit");
        exit.setBounds(60, 5, 80, 30);
        JLabel firstpage = new JLabel();
        firstpage.setText("R to firstpage");
        firstpage.setBounds(300, 5, 100, 30);
        this.add(exit);
        this.add(firstpage);

        this.setSize(Game.WIDTH, Game.HEIGHT);
        this.setLayout(null);


        map_reader(map_number);
        this.player1.newRound(false);
        this.player2.newRound(false);
        int[] cordinates = roundmx(innerthings);
        this.player1.getTank().jump(cordinates[0] , cordinates[1]);
        cordinates = roundmx(innerthings);
        this.player2.getTank().jump(cordinates[0], cordinates[1]);


       this.everyThing.add(player1.getTank());
       this.everyThing.add(player2.getTank());
       this.innerthings.add(player2.getTank());
       this.innerthings.add(player1.getTank());
       this.everyThing.add(player1.board);
       this.everyThing.add(player2.board);


    }

    static void game(int shotlimit , int roundtoWin , int map) {
        Game game = new Game(shotlimit,roundtoWin,map);
        game.addKeyListener(new GameActionListener());
        game.setVisible(true);
        new Timer(10,
                e -> {
                    game.updateState(e);
                    game.repaint();
                }).start();
    }

    static int[] roundmx(List<Thing> innerthings) {
        int[] cordinates = new int[2];
        cordinates[0] = 50 + (int) (Math.random() * (Game.WIDTH - 100));
        cordinates[1] = 100 + (int) (Math.random() * (Game.HEIGHT - 150));
       Rectangle roundomshape =  new Rectangle(cordinates[0], cordinates[1] ,
                25, 25);

       boolean noContact = true;
       while(true) {
           for (Thing thing : innerthings) {

                    if(thing.shape.intersects(roundomshape)) {
                        noContact = false;
                        break;
                    }
           }
           if(noContact)
               break;
            noContact = true;
           cordinates[0] = 50 + (int) (Math.random() * (Game.WIDTH - 100));
           cordinates[1] = 100 + (int) (Math.random() *  (Game.HEIGHT - 150));
            roundomshape =  new Rectangle(cordinates[0], cordinates[1],25,25);
       }
        return cordinates;
    }

    void updateState(ActionEvent event
    ) {
        RoundChecker(event);
        this.time++;
        powerupMaker();
       Timershotfunction();

        boolean contactplayer2 = false;
        boolean contactplayer1 = false;
        Tank p1Tank = this.player1.getTank();
        Tank p2Tank = this.player2.getTank();


        for (Wall wall : this.walls) {
            if(p1Tank.shape.intersects((Rectangle)wall.shape)) {
                p1Tank.bounceAgainst(wall);
             p1Tank.step();p1Tank.step();
            }

            if(p2Tank.shape.intersects((Rectangle)wall.shape)) {
                p2Tank.bounceAgainst(wall);
                p2Tank.step();p2Tank.step();
            }

        }






        for (int n = 0; (n < shot_and_frag1.size() && !contactplayer2); n++) {
            Shot shot = shot_and_frag1.get(n);
            for (Wall wall : this.walls) {
                if (p2Tank.contacts(shot)) {
                    contactplayer2 = true;
                    break;
                }
                if (wall.contacts(shot)) {
                    if (!(shot instanceof Fragbomb)) {
                        shot.bounceAgainst(wall);
                        shot.step();
                        break;
                    } else {
                        this.shot_and_frag1.remove(shot);
                        this.everyThing.remove(shot);
                        Shot shot1 = new Shot(shot.getX(), shot.getY(), shot.direction + Math.PI / 3 , Color.red);
                        this.shot_and_frag1.add(shot1);
                        this.everyThing.add(shot1);
                        Shot shot2 = new Shot(shot.getX(), shot.getY(), shot.direction - Math.PI / 3 ,Color.red);
                        this.shot_and_frag1.add(shot2);
                        this.everyThing.add(shot2);
                        Shot shot3 = new Shot(shot.getX(), shot.getY(), shot.direction + Math.PI / 4 , Color.red);
                        this.shot_and_frag1.add(shot3);
                        this.everyThing.add(shot3);
                        Shot shot4 = new Shot(shot.getX(), shot.getY(), shot.direction - Math.PI / 4 , Color.red);
                        this.shot_and_frag1.add(shot4);
                        this.everyThing.add(shot4);
                    }
                    break;
                }
            }
            if (contactplayer2)
                break;
            shot.step();
        }
        for (int n = 0; (n < shot_and_frag2.size() && !contactplayer2); n++) {
            Shot shot = shot_and_frag2.get(n);
            for (Wall wall : this.walls) {
                if (p2Tank.contacts(shot)) {
                    contactplayer2 = true;
                    break;
                }
                if (wall.contacts(shot)) {
                    if (!(shot instanceof Fragbomb)) {
                        shot.bounceAgainst(wall);
                        shot.step();
                        break;
                    } else {
                        this.shot_and_frag2.remove(shot);
                        this.everyThing.remove(shot);
                        Shot shot1 = new Shot(shot.getX(), shot.getY(), shot.direction + Math.PI / 3 , Color.red);
                        this.shot_and_frag2.add(shot1);
                        this.everyThing.add(shot1);
                        Shot shot2 = new Shot(shot.getX(), shot.getY(), shot.direction - Math.PI / 3 , Color.red);
                        this.shot_and_frag2.add(shot2);
                        this.everyThing.add(shot2);
                        Shot shot3 = new Shot(shot.getX(), shot.getY(), shot.direction + Math.PI / 4 , Color.red);
                        this.shot_and_frag2.add(shot3);
                        this.everyThing.add(shot3);
                        Shot shot4 = new Shot(shot.getX(), shot.getY(), shot.direction - Math.PI / 4 , Color.red);
                        this.shot_and_frag2.add(shot4);
                        this.everyThing.add(shot4);
                    }
                    break;
                }
            }
            if (contactplayer1)
                break;
            shot.step();
        }



        if (contactplayer2 || contactplayer1) {
            newround(contactplayer1,contactplayer2);
        }

        this.shot_and_frag1.forEach(Shot::growOld);
        this.shot_and_frag2.forEach(Shot::growOld);
        for (int n = 0; n < shot_and_frag1.size(); n++) {
            Shot shot = shot_and_frag1.get(n);
            if (shot.isDead()) {
                shot_and_frag1.remove(shot);
                everyThing.remove(shot);
            }



        }
        for (int n = 0; n < shot_and_frag2.size(); n++) {
            Shot shot = shot_and_frag2.get(n);
            if (shot.isDead()) {
                shot_and_frag2.remove(shot);
                everyThing.remove(shot);
            }

        }

        GameActionListener listener = (GameActionListener)
                this.getKeyListeners()[0];
       keyresponse(listener , p1Tank,p2Tank,event);
       keyresponsePlayer2(listener,p1Tank,p2Tank);

    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        this.everyThing.forEach(thing -> thing.draw((Graphics2D) graphics));
        Toolkit.getDefaultToolkit().sync();
    }

 private void Timershotfunction()
 {
     if(player1.getTimershot()!=0)
         player1.Timeradd();
     if(player1.getTimershot()%100 ==0)
     {
         player1.setTimershot(0);
         player1.getTank().setShotisReady(true);
     }
     if(player2.getTimershot()!=0)
         player2.Timeradd();
     if(player2.getTimershot()%100 ==0)
     {
         player2.setTimershot(0);
         player2.getTank().setShotisReady(true);
     }
 }
    void powerupMaker()
    {
        if(this.time%300 ==0) {
            if(powrups.size()!=0) {
                this.everyThing.remove(powrups.get(0));
                this.innerthings.remove(powrups.get(0));
                powrups.remove(0);

            }
            Powrup powrup = new Powrup();

            int[] cordinates = roundmx(this.innerthings);
            Shape shape = new Rectangle(cordinates[0], cordinates[1], 10, 10);
            powrup.setShape(shape);
            this.everyThing.add(powrup);
            this.powrups.add(powrup);
        }
    }
    void keyresponse(GameActionListener listener , Tank p1Tank , Tank p2Tank , ActionEvent event)
    {

        if (listener.p1Move ) {
            if(player1.tank.contacts(p2Tank))
                p1Tank.direction = Math.PI + p1Tank.direction;
            //mohammad
            for (Powrup powrup : this.powrups) {
                if (p1Tank.shape.intersects((Rectangle)powrup.shape)){
                    this.powrups.remove(powrup);
                    this.everyThing.remove(powrup);
                    if (powrup.type == PowerUptype.FragBomb)
                        Game.frofbomb_is_on1 = true;
                    else {}
                    break;
                }
            }
            //mohammad

            p1Tank.step();
        }


        if (listener.p1Right)
            p1Tank.turnRight();

        if (listener.p1Left)
            p1Tank.turnLeft();
//
        if (listener.p1Fire && player1.shotsfired< this.shotlimit && player1.getTank().isShotisReady() && !(Game.frofbomb_is_on1)) {
            Shot shot = new Shot(
                    p1Tank.getGunX(), p1Tank.getGunY(), p1Tank.direction , Color.RED
            );
            player1.setTimershot(1);
            player1.getTank().setShotisReady(false);
            this.shot_and_frag1.add(shot);
            this.everyThing.add(shot);
            this.player1.shotsfired++;
        }
        if(listener.p1Fire && Game.frofbomb_is_on1) {
            listener.p1Fire = false;
            Game.frofbomb_is_on1 = false;
            Shot shot = new Fragbomb(p1Tank.getGunX(), p1Tank.getGunY(), p1Tank.direction, Color.GREEN);
            this.everyThing.add(shot);
            this.shot_and_frag1.add(shot);
        }

        shotlimitchecker(this.player1,this.player2);
        if(listener.exit)
        {
            this.dispose(); // close window
            this.setVisible(false);
            System.exit(0); // stop program
        }
        if(listener.firstpage)
        {
            ((Timer)event.getSource()).stop();
            this.setVisible(false);
            this.dispose();
            Firstpage.firstpag();


        }

    }
    void keyresponsePlayer2(GameActionListener listener ,Tank p1Tank , Tank p2Tank)
    {
        if(listener.p2Move)
        {
            if(player1.tank.contacts(p2Tank))
                p2Tank.direction = Math.PI + p2Tank.direction;
            for (Powrup powrup : this.powrups) {
                if (p2Tank.shape.intersects((Rectangle)powrup.shape)){
                    this.powrups.remove(powrup);
                    this.everyThing.remove(powrup);
                    if (powrup.type == PowerUptype.FragBomb)
                        Game.frofbomb_is_on2 = true;
                    else {}
                    break;
                }
            }

            p2Tank.step();
        }

        if(listener.p2Right)
            p2Tank.turnRight();
        if(listener.p2Left)
            p2Tank.turnLeft();

        if (listener.p2Fire && player2.shotsfired< this.shotlimit && player2.getTank().isShotisReady() && !(Game.frofbomb_is_on2)) {
            Shot shot = new Shot(
                    p2Tank.getGunX(), p2Tank.getGunY(), p2Tank.direction,Color.blue
            );

            player2.setTimershot(1);
            player2.getTank().setShotisReady(false);

            this.shot_and_frag2.add(shot);
            this.everyThing.add(shot);
            this.player2.shotsfired++;
        }
        //mohammad
        if(listener.p2Fire && Game.frofbomb_is_on2) {
            listener.p2Fire = false;
            Game.frofbomb_is_on2 = false;
            Shot shot = new Fragbomb(p1Tank.getGunX(), p1Tank.getGunY(), p1Tank.direction, Color.GREEN);
            this.everyThing.add(shot);
            this.shot_and_frag2.add(shot);
        }

    }

    void newround(boolean contactplayer1,boolean contactplayer2)
    {
        if(contactplayer2) {
            this.player1.newRound(true);
            this.player2.newRound(false);
        }
        if(contactplayer1)
        {
            this.player1.newRound(false);
            this.player2.newRound(true);
        }
        if(!contactplayer1&&!contactplayer2)
        {
            this.player1.newRound(false);
            this.player2.newRound(false);
        }
        while(shot_and_frag1.size()!=0)
        {
            this.everyThing.remove(shot_and_frag1.get(0));
            this.shot_and_frag1.remove(shot_and_frag1.get(0));
        }

        while(shot_and_frag2.size()!=0)
        {
            this.everyThing.remove(shot_and_frag2.get(0));
            this.shot_and_frag2.remove(shot_and_frag2.get(0));
        }
        shotsalarmplayer1.setText("");
        shotsalarmplayer2.setText("");

        int[] cordinates = roundmx(innerthings);
        this.player1.getTank().jump(cordinates[0] , cordinates[1]);
        cordinates = roundmx(innerthings);
        this.player2.getTank().jump(cordinates[0], cordinates[1]);
    }

    void shotlimitchecker(Player player1 , Player player2)
    {
        if(player1.shotsfired>= this.shotlimit)
            this.shotsalarmplayer1.setText("No Bullets");

        if(player2.shotsfired>=this.shotlimit)

            this.shotsalarmplayer2.setText("No Bullets");
        if(player1.shotsfired>= this.shotlimit && player2.shotsfired>=this.shotlimit)
            newround(false,false);

    }


    void RoundChecker(ActionEvent event)
    {
        if (player1.points == this.roundtoWin) {
            ((Timer) event.getSource()).stop();
            JLabel Wininglabel = new JLabel();
            Wininglabel.setBounds(100, 250, 400, 100);
            Wininglabel.setText("Player 1 Wins " + "\n" + "Press E to Exit or R to First Page");
            this.add(Wininglabel);

            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_R:
                            Game.this.setVisible(false);
                            Game.this.dispose();
                            Firstpage.firstpag();
                            break;
                        case KeyEvent.VK_E:
                            Game.this.dispose(); // close window
                            Game.this.setVisible(false);
                            System.exit(0);
                    }
                }
            });
        }
        if (player2.points == this.roundtoWin)
        {
            ((Timer)event.getSource()).stop();
            JLabel Wininglabel  = new JLabel();
            Wininglabel.setBounds(200,250,200,100);
            Wininglabel.setText("Player 2 Wins " +"/n" + "Press E to Exit or R to First Page");
            this.add(Wininglabel);
        }
    }


    void map_reader(int map_number)
    {
        String map_name = "map" + String.valueOf(map_number);

        try {
            String[][] map = FileHandler.parseWithSpaces(map_name);
            for(int row =0 ; row <map.length ; row++) {
                for (int column = 0; column < map[row].length; column++) {
                    if (map[row][column].equals("|")) {
                        Wall wall = new Wall(column * 25, row * 25, 25, true, true);
                        this.everyThing.add(wall);
                        this.walls.add(wall);
                        if ( column!= 0 && column!= map[2].length-1)
                            this.innerthings.add(wall);

                    }
                    if (map[row][column].equals("-")) {
                        Wall wall = new Wall(column * 25, row * 25, 25, false, true);
                        if(row!= 2 && row != map.length-1)
                            this.innerthings.add(wall);
                        this.everyThing.add(wall);
                        this.walls.add(wall);
                    }
                    if (map[row][column].equals("_"))
                    {
                       Wall wall;
                       if(map[row+1][column].equals(" "))
                         wall = new Wall(column * 25, row * 25, 25, false,false);
                       else
                           wall = new Wall(column * 25 , row * 25 +24, 25, false,false);
                        this.everyThing.add(wall);
                        this.walls.add(wall);
                        this.innerthings.add(wall);
                    }
                    if (map[row][column].equals("/"))
                    {
                        Wall wall;
                        if(map[row][column+1].equals(" "))
                              wall = new Wall(column * 25, row * 25, 25, true,false);
                        else
                             wall = new Wall(column * 25 +24, row * 25, 25, true,false);
                        this.everyThing.add(wall);
                        this.walls.add(wall);
                        this.innerthings.add(wall);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


