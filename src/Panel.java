import Algorithm.bin_morf.BinClosing;
import Algorithm.bin_morf.BinOpening;
import Algorithm.filters.FilterTest;
import Algorithm.gray_morf.GrayClosing;
import Algorithm.gray_morf.GrayOpening;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MSI on 2016-03-30.
 */
public class Panel extends JPanel implements ActionListener {

    public static final int HEIGHT = 50;
    public static final int WIDTH = 500;

    private JButton btn1, btn2;
    private JFileChooser fileChooser;
    private JTextArea t_text, g_text;
    private JLabel t, g;

    private String path;

    public Panel() {
        btn1 = new JButton("Compute");
        btn2 = new JButton("Open image");
        fileChooser = new JFileChooser(System.getProperty("user.home") + "\\Pictures\\Mokrzycki");
        FileFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        //t_text = new JTextArea(1,5);
        //g_text = new JTextArea(1,5);
        //t = new JLabel("t");
        //g = new JLabel("g");

        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        btn1.addActionListener(this);
        btn2.addActionListener(this);

        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //add(t);
        //add(t_text);
        //add(g);
        //add(g_text);
        add(btn2);
        add(btn1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1 && !path.isEmpty()) {
//            BinErosion okrawanie = new BinErosion(path);
//            Test test = new Test(path);
//            okrawanie.run();
//            okrawanie.saveImage(okrawanie.getTemplateImage(), "_dylatacja");
            BinOpening opening = new BinOpening(path);
            BinClosing closing = new BinClosing(path);
//            GrayOpening grayOpening = new GrayOpening(path);
//            GrayClosing grayClosing = new GrayClosing(path);

//            FilterTest filterTest = new FilterTest(path);
            System.out.println("Zapisano plik!");
        }
        else if (e.getSource() == btn2) {
            int returnVal = fileChooser.showOpenDialog(Panel.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                path = fileChooser.getSelectedFile().toString();
                System.out.println("Otworzono plik!");
            }
        }

    }

}