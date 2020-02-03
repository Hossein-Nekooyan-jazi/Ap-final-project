public class Player {
    Tank tank = new Tank( 200,300,0);
    int points = 0;
    String name;
    BoardofScores board;
    Player (String name,int x , int y)
    {
        this.name =name;
        this.board =  new BoardofScores(this.points , x , y ,this.name);
    }
    void newRound(boolean hasWon) {
        if (hasWon) {
            this.points++;
            board.point++;
        }
        this.tank.x = 20+(int) (Math.random() * (Game.WIDTH-40));
        this.tank.y= 100+(int)(Math.random() * (Game.HEIGHT-120));
    }
    Tank getTank(){return this.tank;}
}