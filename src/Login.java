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
    private void createServer(){
        new createServer();
    }
    private void CreateLoginWindow(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e) {
            e.printStackTrace();
        }

        f = new JFrame("Login");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String servers[] = {"","Helsinki"};
        JLabel lbName = new JLabel("Name:");
        JTextField txtName = new JTextField("Me");
        JLabel lbAddress = new JLabel("IP Address:");
        JComboBox txtAddress = new JComboBox(servers);
        JButton btnLogin = new JButton("Login");
        JButton createServ = new JButton("Create Server");
        lbName.setBounds(80,10,50,50);
        lbAddress.setBounds(60,70,200,50);
        txtName.setBounds(30,50,150,20);
        txtAddress.setBounds(30,120,150,20);
        btnLogin.setBounds(50,150,100,30);
        createServ.setBounds(25,250,150,30);
        f.add(lbName);
        f.add(txtName);
        f.add(lbAddress);
        f.add(txtAddress);
        f.add(btnLogin);
        f.add(createServ);
        f.setSize(200,350);
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String address = "";
                int port = 2552;
                if(txtAddress.getSelectedIndex() == 0){
                address = "localhost";
                }else if(txtAddress.getSelectedIndex() == 1){
                        address = "95.216.138.187";
                    }
                System.out.println(address);
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
