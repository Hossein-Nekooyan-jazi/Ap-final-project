public class Player {
    Tank tank = new Tank(0, 0, 0);
    int points = 0;
    String name;
    BoardofScores board;
    int shotsfired = 0;
    private int timershot =0;
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
        this.shotsfired=0;
        this.timershot=0;
        this.getTank().setShotisReady(true);
    }

    Tank getTank() {
        return this.tank;
    }


    public void setTimershot(int timershot) {
        this.timershot = timershot;
    }
    public void Timeradd()
    {
        this.timershot++;
    }

    public int getTimershot() {
        return timershot;
    }
}