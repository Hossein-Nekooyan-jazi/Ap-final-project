import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        firstpag();
    }

    private static void game() {
        Game game = new Game();
        game.addKeyListener(new GameActionListener());
        game.setVisible(true);


        new Timer(10,
                e -> {
                    game.updateState();
                    game.repaint();
                }).start();
    }


    private static void firstpag() {
        Firstpage firstpage = Firstpage.getFirstpage();
        if (!firstpage.isVisible())
            firstpage.setVisible(true);
           /* new Timer(10, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e1) {
                    if(firstpage.start)
                    {
                        ((Timer)e1.getSource()).stop();
                        game();
                    }

                }
            }).start();*/
    }
}
