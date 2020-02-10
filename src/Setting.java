import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Setting extends JFrame {

    private static Setting setting ;
    private int shotslimit ;
    private int  Roundstowin;
    private int maptochoose;
    private JFrame frame;
    private Setting(int shotlimit , int roundstowin , int Maptochoose)
    {

     this.shotslimit = shotlimit;
     this.Roundstowin = Roundstowin;
     this.maptochoose = Maptochoose;


     JFrame frame = new JFrame("Setting");
     this.frame = frame;
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setLayout(null);

        JButton start = new JButton("Back");
        start.setBounds(30, 10, 80, 50);
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
               frame.setVisible(false);
                Firstpage.firstpag();
            }
        });


        JLabel shotslimitlabel = new JLabel("Shots Limit");
     shotslimitlabel.setBounds(50,100,100,20);
        JLabel roundstowinlabel = new JLabel("Rounds to Win");
        roundstowinlabel.setBounds(50,150,100,20);

        JFormattedTextField shotslimittext = new JFormattedTextField(new DecimalFormat("#;"));
        shotslimittext.setValue(shotlimit);
        shotslimittext.setColumns(10);
       shotslimittext.setBounds(200,100,100,20);

        JFormattedTextField roundstowintext = new JFormattedTextField(new DecimalFormat("#;"));
        roundstowintext.setValue(roundstowin);
        roundstowintext.setColumns(10);
        roundstowintext.setBounds(200,150,100,20);

        frame.add(shotslimitlabel);
        frame.add(shotslimittext);
        frame.add(roundstowinlabel);
        frame.add(roundstowintext);
        frame.add(start);

        frame.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                Object source = e.getSource();
                if (source == shotslimittext) {
                    Setting.this.shotslimit = ((int) shotslimittext.getValue());
                }
                else if (source == roundstowintext) {
                    Setting.this.Roundstowin = ((int) roundstowintext.getValue());
                }
            }
        });

    }



    public static void Settingpage(int shotslimit , int Roundstowin , int Maptochoose)
    {
        if(Setting.setting==null)
             Setting.setting = new Setting(shotslimit,Roundstowin,Maptochoose);
        Setting.setting.frame.setVisible(true);

    }
}
