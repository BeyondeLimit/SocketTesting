import javax.swing.*;
import java.awt.*;

public class createServer extends JFrame{
    public createServer() {
        windowOpen();

    }
    public void windowOpen(){
        JFrame f = new JFrame("Creating Server");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Enter Server's name : ");
        JTextField txtFl = new JTextField();
        JPanel pan = new JPanel();
        JButton create = new JButton("Create");

        f.setMinimumSize(new Dimension(400,300));
        pan.setVisible(true);
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        f.setVisible(true);
        f.setLocationRelativeTo(null);

        pan.add(label);
        pan.add(txtFl);
        f.add(pan);

    }
}
