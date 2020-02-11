import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Setting extends JFrame {

    private static Setting setting ;
    private int shotslimit ;
    private int  Roundstowin;
    private int maptochoose;
    private JFrame frame;
    private  JList<String> map;

    private JFormattedTextField shotslimittext;
    private JFormattedTextField roundstowintext;
    private KeyEvent A_key;

    private Setting(int shotlimit , int roundstowin , int Maptochoose)
    {

     this.shotslimit = shotlimit;
     this.Roundstowin = roundstowin;
     this.maptochoose = Maptochoose;


     JFrame frame = new JFrame("Setting");
     this.frame = frame;
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setLayout(null);
        strtbutton();

        JLabel shotslimitlabel = new JLabel("Shots Limit:");
     shotslimitlabel.setBounds(50,100,100,20);
        JLabel roundstowinlabel = new JLabel("Rounds to Win:");
        roundstowinlabel.setBounds(50,150,100,20);

        NumberFormat numberFormat = new DecimalFormat("#;");
        numberFormat.setMaximumIntegerDigits(1);
        numberFormat.setMinimumIntegerDigits(1);

        this.shotslimittext = new JFormattedTextField(numberFormat);
        shotslimittext.setValue(shotlimit);
        shotslimittext.setColumns(10);
       shotslimittext.setBounds(150,100,100,20);
       this.roundstowintext = new JFormattedTextField(numberFormat);
        roundstowintext.setValue(roundstowin);
        roundstowintext.setColumns(10);
        roundstowintext.setBounds(150,150,100,20);

        frame.add(shotslimitlabel);
        frame.add(shotslimittext);
        frame.add(roundstowinlabel);
        frame.add(roundstowintext);

        final DefaultListModel<String> maplist = new DefaultListModel<>();
        maplist.addElement("Map1");
        maplist.addElement("Map2");
        maplist.addElement("Map3");

        map = new JList<>(maplist);
        map.setSelectedIndex(0);
        map.setBounds(300,100, 75,75);

         BufferedImage image = null;
        BufferedImage image2 = null;
        BufferedImage image3 = null;


        try {
            image = ImageIO.read(new File("/Users/hossein/Downloads/Ap-final-project-Teacher-Revision/Map1.png"));
            image2 =ImageIO.read(new File("/Users/hossein/Downloads/Ap-final-project-Teacher-Revision/map2.png"));
            image3 = ImageIO.read(new File("/Users/hossein/Downloads/Ap-final-project-Teacher-Revision/map3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel lbl = new JLabel();
        lbl.setBounds(300,200,200,200);
        frame.add(lbl);

        frame.add(map);


        BufferedImage finalImage = image;
        BufferedImage finalImage1 = image3;
        BufferedImage finalImage2 = image2;
        map.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (map.getSelectedIndex() == 0) {
                    lbl.setIcon(new ImageIcon(finalImage));
                }
                if (map.getSelectedIndex()==1)
                    lbl.setIcon(new ImageIcon(finalImage2));

                if (map.getSelectedIndex()==2)
                    lbl.setIcon(new ImageIcon(finalImage1));

            }
        });
        confirmb();
    }
    private void strtbutton()
    {
        JButton start = new JButton("Back");
        start.setBounds(30, 10, 80, 50);
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                Firstpage.setShotslimit(Setting.this.shotslimit);
                Firstpage.setRoundstowin(Setting.this.Roundstowin);
                Firstpage.setMap(Setting.this.maptochoose);
                Firstpage.firstpag();
            }
        });
        frame.add(start);
    }

    private void confirmb()
    {
        JButton confirmb = new JButton("Confirm");
        confirmb.setBounds(400,10,80,80);
        confirmb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Setting.this.shotslimit = Integer.parseInt(Setting.this.shotslimittext.getText());
                Setting.this.Roundstowin = Integer.parseInt(Setting.this.roundstowintext.getText());
                Setting.this.maptochoose = Setting.this.map.getSelectedIndex()+1;
            }
        });
        frame.add(confirmb);
    }
    public static void Settingpage(int shotslimit , int Roundstowin , int Maptochoose)
    {
        if(Setting.setting==null)
             Setting.setting = new Setting(shotslimit,Roundstowin,Maptochoose);
        Setting.setting.frame.setVisible(true);

    }
}
