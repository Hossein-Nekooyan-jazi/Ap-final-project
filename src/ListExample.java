import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ListExample
{
    ListExample() throws IOException {
        JFrame f= new JFrame();
        final JLabel label = new JLabel();
        label.setSize(500,100);
        JButton b=new JButton("Show");
        b.setBounds(200,150,80,30);

        final DefaultListModel<String> maplist = new DefaultListModel<>();
        maplist.addElement("Map1");
        maplist.addElement("Map2");
        maplist.addElement("Map3");

        final JList<String> map = new JList<>(maplist);
        map.setSelectedIndex(0);
        map.setBounds(100,100, 75,75);

        BufferedImage image =  ImageIO.read(new File("/Users/hossein/Downloads/Ap-final-project-Teacher-Revision/Map1.png"));

        JLabel lbl = new JLabel();

        lbl.setBounds(200,200,300,300);
        f.add(lbl);

        f.add(map);f.add(b); f.add(label);
        f.setSize(500,500);
        f.setLayout(null);
        f.setVisible(true);
        map.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (map.getSelectedIndex() == 1) {
                    lbl.setIcon(new ImageIcon(image));
                }
                if (map.getSelectedIndex()==0)
                    lbl.setIcon(null);
            }
        });

    }
    public static void main(String args[]) throws IOException {
        new ListExample();
    }} 