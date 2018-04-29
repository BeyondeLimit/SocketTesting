import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JFrame f;
    public Login(){
        CreateLoginWindow();
    }
    private void login(String name, String address, int port) {
        f.dispose();
        new ClientUI(name,address,port);
    }
    private void CreateLoginWindow(){
        f = new JFrame("Login");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lbName = new JLabel("Name:");
        JTextField txtName = new JTextField("Me");
        JLabel lbAddress = new JLabel("IP Address:");
        JTextField txtAddress = new JTextField("127.0.0.1");
        JLabel lbPort = new JLabel("Port:");
        JTextField txtPort = new JTextField("2552");
        JButton btnLogin = new JButton("Login");
        lbName.setBounds(80,10,50,50);
        lbAddress.setBounds(60,70,200,50);
        lbPort.setBounds(80,130,50,50);
        txtName.setBounds(30,50,150,20);
        txtAddress.setBounds(30,120,150,20);
        txtPort.setBounds(30,180,150,20);
        btnLogin.setBounds(50,250,100,30);
        f.add(lbName);
        f.add(txtName);
        f.add(lbAddress);
        f.add(txtAddress);
        f.add(lbPort);
        f.add(txtPort);
        f.add(btnLogin);
        f.setSize(200,350);
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String address = txtAddress.getText();
                int port = Integer.parseInt(txtPort.getText());
                login(name,address,port);
            }
        });
    }
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Login();
    }


}
