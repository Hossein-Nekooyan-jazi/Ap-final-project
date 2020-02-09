public class Player {
    Tank tank = new Tank(0, 0, 0);
    int points = 0;
    String name;
    BoardofScores board;
    int shotsfired = 0;
    Player(String name, int x, int y ,int shotsfired  ) {
        this.name = name;
        this.shotsfired = shotsfired;
        this.board = new BoardofScores(this.points, x, y, this.name);
    }

    void newRound(boolean hasWon) {
        if (hasWon) {
            this.points++;
            board.point++;
        }
        this.tank.jump(
                20 + (int) (Math.random() * (Game.WIDTH - 40)),
                100 + (int) (Math.random() * (Game.HEIGHT - 120))
        );
        this.shotsfired=0;
    }

    Tank getTank() {
        return this.tank;
    }
}