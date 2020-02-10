import util.FileHandler;

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
    final static int WIDTH = 500, HEIGHT = 550;
    int time = 0;
    List<Thing> everyThing = new ArrayList<>();
    Player player1 = new Player("Player 1", 10, 30,0);
    Player player2 = new Player("Player 2", 400, 30,0);
    List<Shot> shotsInTheAirplayer1 = new ArrayList<>();
    List<Shot> ShotsInTheAirplayer2 = new ArrayList<>();
    List<Wall> walls = new LinkedList<>();
    List<Powrup> powrups = new LinkedList<>();
    JLabel shotsalarmplayer1 = new JLabel();
    JLabel shotsalarmplayer2 = new JLabel();

    int shotlimit;
    boolean stop;
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

        this.everyThing.add(player1.getTank());
        this.everyThing.add(player2.getTank());
        this.everyThing.add(player1.board);
        this.everyThing.add(player2.board);


    }

    static void game() {
        Game game = new Game(30,3,1);
        game.addKeyListener(new GameActionListener());
        game.setVisible(true);
        new Timer(10,
                e -> {
                    game.updateState(e);
                    game.repaint();
                }).start();
    }

    static int[] roundmx() {
        int[] cordinates = new int[2];
        cordinates[0] = 20 + (int) (Math.random() * (Game.WIDTH - 40));
        cordinates[1] = 100 + (int) (Math.random() * (Game.HEIGHT - 120));
        return cordinates;
    }

    void updateState(ActionEvent event
    ) {
        RoundChecker(event);
        this.time++;
        powerupMaker();
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

        for (int n = 0; (n < shotsInTheAirplayer1.size() && !contactplayer2); n++) {
            Shot shot = shotsInTheAirplayer1.get(n);
            for (Wall wall : this.walls) {
                if (p2Tank.contacts(shot)) {
                    contactplayer2 = true;
                    break;
                }
                if (wall.contacts(shot)) {
                    shot.bounceAgainst(wall);
                    break;
                }
            }
            if (contactplayer2)
                break;
            shot.step();
        }

        for (int n = 0; (n < ShotsInTheAirplayer2.size() && !contactplayer2); n++) {
            Shot shot = ShotsInTheAirplayer2.get(n);
            for (Wall wall : this.walls) {
                if (p1Tank.contacts(shot)) {
                    contactplayer1 = true;
                    break;
                }
                if (wall.contacts(shot)) {
                    shot.bounceAgainst(wall);
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

        this.shotsInTheAirplayer1.forEach(Shot::growOld);
        this.ShotsInTheAirplayer2.forEach(Shot::growOld);
        for (int n = 0; n < shotsInTheAirplayer1.size(); n++) {
            Shot shot = shotsInTheAirplayer1.get(n);
            if (shot.isDead()) {
                shotsInTheAirplayer1.remove(shot);
                everyThing.remove(shot);
            }



        }
        for (int n = 0; n < ShotsInTheAirplayer2.size(); n++) {
            Shot shot = ShotsInTheAirplayer2.get(n);
            if (shot.isDead()) {
                ShotsInTheAirplayer2.remove(shot);
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


    void powerupMaker()
    {
        if(this.time%300 ==0) {
            if(powrups.size()!=0) {
                this.everyThing.remove(powrups.get(0));
                powrups.remove(0);
            }
            Powrup powrup = new Powrup();
            this.everyThing.add(powrup);
            this.powrups.add(powrup);
        }
    }
    void keyresponse(GameActionListener listener , Tank p1Tank , Tank p2Tank , ActionEvent event)
    {

        if (listener.p1Move ) {
            if(player1.tank.contacts(p2Tank))
                p1Tank.direction = Math.PI + p1Tank.direction;

            p1Tank.step();
        }


        if (listener.p1Right)
            p1Tank.turnRight();

        if (listener.p1Left)
            p1Tank.turnLeft();

        if (listener.p1Fire && player1.shotsfired< this.shotlimit) {
            Shot shot = new Shot(
                    p1Tank.getGunX(), p1Tank.getGunY(), p1Tank.direction , Color.RED
            );
            this.shotsInTheAirplayer1.add(shot);
            this.everyThing.add(shot);
            this.player1.shotsfired++;
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

            p2Tank.step();
        }

        if(listener.p2Right)
            p2Tank.turnRight();
        if(listener.p2Left)
            p2Tank.turnLeft();

        if (listener.p2Fire && player2.shotsfired< this.shotlimit) {
            Shot shot = new Shot(
                    p2Tank.getGunX(), p2Tank.getGunY(), p2Tank.direction,Color.blue
            );
            this.ShotsInTheAirplayer2.add(shot);
            this.everyThing.add(shot);
            this.player2.shotsfired++;
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
        while(shotsInTheAirplayer1.size()!=0)
        {
            this.everyThing.remove(shotsInTheAirplayer1.get(0));
            this.shotsInTheAirplayer1.remove(shotsInTheAirplayer1.get(0));
        }

        while(ShotsInTheAirplayer2.size()!=0)
        {
            this.everyThing.remove(ShotsInTheAirplayer2.get(0));
            this.ShotsInTheAirplayer2.remove(ShotsInTheAirplayer2.get(0));
        }
        shotsalarmplayer1.setText("");
        shotsalarmplayer2.setText("");
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
            Wininglabel.setBounds(250, 250, 200, 100);
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
            Wininglabel.setBounds(250,250,200,100);
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
                        Wall wall = new Wall(column * 25, row * 25, 25, true);
                        this.everyThing.add(wall);
                        this.walls.add(wall);
                    }
                    if (map[row][column].equals("-")) {
                        Wall wall = new Wall(column * 25, row * 25, 25, false);
                        this.everyThing.add(wall);
                        this.walls.add(wall);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


