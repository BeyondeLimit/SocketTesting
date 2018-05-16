
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ClientUI extends JFrame implements Runnable {

    private JTextArea history;
    private JTextField inputTxt;
    private String message;

    private Client client;

    private Thread listen,run;
    private boolean running = false;

    public PublicKey publicKey;

    public ClientUI(String name,String address,int port){
        client = new Client(name,address,port);
        CreateWindow();
        if(!client.openConnection(address)){
            infoDisplay("Connection failed!");
        }else {
            infoDisplay("Connected");
            infoDisplay("Welcome : " + name);
        }
        String connection = "/c/" + name;
        client.sendInfo(connection.getBytes());
        running = true;
        run = new Thread(this,"Running thread");
        run.start();

    }

    private void CreateWindow(){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        history = new JTextArea();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JScrollPane scrlP = new JScrollPane(history);
        inputTxt = new JTextField();
        JButton btnSend = new JButton("send");

        //((DefaultCaret) history.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        f.setMinimumSize(new Dimension(500,200));
        history.setLineWrap(true);
        scrlP.setPreferredSize(new Dimension(300,100));
        scrlP.setMinimumSize(new Dimension(300,300));
        inputTxt.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));

        WindowAdapter listen = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                String disconnectID = "/d/" + client.getID() + "/e/";
                updateInfo(disconnectID,false);
                running = false;
                client.closingSocket();
            }
        };

        panel1.setVisible(true);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.X_AXIS));
        //panel1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panel2.setVisible(true);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        history.setEditable(false);



        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message = inputTxt.getText();
                updateInfo(message,true);
                panel2.updateUI();
            }
        });
        inputTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    message = inputTxt.getText();
                    updateInfo(message,true);
                    panel2.updateUI();

                }
            }
        });

        panel1.add(scrlP);
        panel2.add(inputTxt);
        f.add(panel1);
        panel1.add(panel2);
        panel2.add(btnSend);
        f.addWindowStateListener(listen);
        f.addWindowListener(listen);
        f.pack();
        inputTxt.requestFocusInWindow();
        panel2.updateUI();
    }

    public void run(){
        listen();
    }

    public void infoDisplay(String text){
        history.append(text+"\n\r");
    }

    private void updateInfo(String message,boolean isMess){
        if(message.equals("")){
            return;
        }
        if(isMess){
         message = client.getName() + " : " + message;
            try {
                byte[] newMessage = client.doEncryption(publicKey,message);
                String cryptedMessage = "/n/" + Base64.getEncoder().encodeToString(newMessage);
                client.sendInfo(cryptedMessage.getBytes());
                inputTxt.setText("");
                inputTxt.requestFocusInWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            client.sendInfo(message.getBytes());
        }

    }

    public void listen(){
        listen = new Thread("Listen") {
            public void run() {
                while (running) {
                   String message =  client.getInfo();
                    if(message.startsWith("/c/")){
                        client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
                        infoDisplay("Seccesfully connected to server! " + client.getID());
                    }else if(message.startsWith("/n/")){
                        String txt = message.substring(3);
                        txt = txt.split("/e/")[0];
                        infoDisplay(txt);
                    }else if(message.startsWith("/a/")){
                        String txt = "/a/" + client.getID() + "/e/";
                        updateInfo(txt,false);
                    }else if(message.startsWith("/k/")){
                        try {
                            String key = message.substring(3);
                            key = key.trim();
                            byte[] generKey = Base64.getDecoder().decode(key);
                            KeyFactory kf = null;
                            kf = KeyFactory.getInstance("RSA");
                            publicKey = kf.generatePublic(new X509EncodedKeySpec(generKey));
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeySpecException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }
        };
        listen.start();
    }
}
