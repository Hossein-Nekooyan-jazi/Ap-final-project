import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Firstpage extends JFrame {
    private static Firstpage firstpage;
    boolean start;
    JFrame frame;
   private static int shotslimit=9;
   private static int roundstowin = 3;
   private static int map = 1;
    private Firstpage() {
        JFrame jframe = new JFrame("Battle of Tanks");
        this.frame = jframe;
        //submit button
        JButton start = new JButton("Start");
        start.setBounds(150, 100, 140, 40);
        JButton setting = new JButton("Setting");
        setting.setBounds(150, 160, 140, 40);
        JButton exit = new JButton("Exit");
        exit.setBounds(150, 220, 140, 40);
        //enter name label
        JLabel label = new JLabel();
        label.setText("Lets Start the Game");
        label.setBounds(10, 60, 150, 100);
        //empty label which will show event after button clicked
        JLabel label1 = new JLabel();
        label1.setText("Set the Settings");
        label1.setBounds(10, 120, 200, 100);

        JLabel label2 = new JLabel();
        label2.setText("Goodbye");
        label2.setBounds(10, 180, 200, 100);
        //add to frame
        jframe.add(label1);
        jframe.add(label);
        jframe.add(label2);
        jframe.add(start);
        jframe.add(setting);
        jframe.add(exit);
        jframe.setSize(500, 500);
        jframe.setLayout(null);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
              frame.setVisible(false);
                Game.game(Firstpage.this.shotslimit,Firstpage.this.roundstowin,Firstpage.this.map);
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // stop program
                frame.dispose(); // close window
                frame.setVisible(false);
            }
        });
        setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Setting.Settingpage(Firstpage.shotslimit,Firstpage.roundstowin,Firstpage.map);
            }
        });
    }

    static Firstpage getFirstpage() {
        if (firstpage == null) {
            firstpage = new Firstpage();
            return firstpage;
        } else
            return firstpage;
    }

     static void firstpag() {
        Firstpage firstpage = Firstpage.getFirstpage();
        if (!firstpage.frame.isVisible())
            firstpage.frame.setVisible(true);
    }

    public static void  setShotslimit(int shotslimit) {
        Firstpage.shotslimit = shotslimit;
    }

    public static void setRoundstowin(int roundstowin) {
        Firstpage.roundstowin = roundstowin;
    }

    public static void setMap(int map) {
        Firstpage.map = map;
    }
}

