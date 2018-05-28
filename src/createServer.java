import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class createServer {
    public createServer(JFrame jFrame) {
        windowOpen(jFrame);
    }

    public void windowOpen(JFrame jFrame){
        JFrame frame = new JFrame("Creating Server");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JLabel label = new JLabel("Enter Server's name : ");
        JTextField txtFl = new JTextField();
        JButton btnCreate = new JButton("Create");
        JButton btnBack = new JButton("Back");

        frame.setMinimumSize(new Dimension(300,300));
        txtFl.setMaximumSize(new Dimension(300,20));
        txtFl.setLocation(100,100);
        btnCreate.setLocation(50,50);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.add(label);
        frame.add(txtFl);
        frame.add(btnCreate);
        frame.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setEnabled(true);
                frame.dispose();

            }
        });

    }
}
