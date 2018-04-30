import javax.crypto.Cipher;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.*;

public class Client {

    private String name,address;
    private int port;

    private DatagramSocket socket;
    private InetAddress ip;

    private Thread thrdSend;

    private int ID = -1;


    public Client(String name, String address,int port){
        this.name = name;
        this.address = address;
        this.port = port;

    }

    public String getName(){
        return name;
    }
    public String getAddress(){
        return address;
    }
    public int getPort(){
        return port;
    }


       public boolean openConnection(String address){
           try{
               socket = new DatagramSocket();
               ip = InetAddress.getByName(address);
           }catch(Exception e){
               e.printStackTrace();
               return false;
           }
           return true;
       }
       public String getInfo(){
           byte [] data = new byte[1024];
           DatagramPacket packet = new DatagramPacket(data,data.length);
           try {
               socket.receive(packet);
           }catch (IOException e){
               e.printStackTrace();
           }
           String message = new String(packet.getData());

           return message;
       }
       public void sendInfo(final byte[] data){
           thrdSend = new Thread("Send") {
               public void run(){
                   DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
                   try {
                       socket.send(packet);
                   }catch(IOException e){
                       e.printStackTrace();
                   }
               }
           };
           thrdSend.start();
       }
       public void setID(int ID){
            this.ID = ID;
       }
       public int getID(){
        return ID;
       }
        public void closingSocket() {
            new Thread() {
                public void run() {
                synchronized (socket)
                {
                    socket.close();
                }
            }
        }.start();
        }






        public byte[] doEncryption(PublicKey pubKey,String message) throws Exception {
            byte[] encrypt = encrypt(pubKey,message);
            return encrypt;
        }
        public static byte[] encrypt(PublicKey publicKey,String message)throws  Exception{
            Cipher chiper = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            chiper.init(Cipher.ENCRYPT_MODE,publicKey);
            return chiper.doFinal(message.getBytes());
        }


}
