import javax.swing.*;

/**
 * Created by MSI on 2016-03-30.
 */
public class ActionFrame extends JFrame {

    public ActionFrame() {
        super("Operacje logiczne na obrazach binarnych - negacja obrazu");

        Panel panel = new Panel();

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
