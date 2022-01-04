import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class router_adem{

    
    //client görevi
    static Socket serverSocket;
    static  DataInputStream dataInputStream;

    //server görevi
    static ServerSocket adem;
    static Socket client = new Socket();
    static Socket serverclient = new Socket();
    static String a;
    static DataOutputStream dataOutputStream;
    static int send=1000;

    public static void main(String[] args) throws UnknownHostException, IOException {
    
        //servere bağlanıyor
        serverSocket = new Socket("localhost", 1450);
        adem = new ServerSocket(1451);
        //server bağlantı isteği bekliyor
        serverclient = adem.accept();
        //client bağlantı isteği bekliyor
        client = adem.accept();


        TimerTask task1 = new TimerTask() {

            @Override
            public void run() {
                try {
                    DataInputStream dataInputStream1 = new DataInputStream(serverSocket.getInputStream());
                     a = dataInputStream1.readUTF();
                     System.out.println(a);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    

            } };


        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                                   try {
                        dataOutputStream = new DataOutputStream(client.getOutputStream());
                        dataOutputStream.writeUTF(a+"->r1 ");
                        Thread.sleep(new Random().nextInt(1234));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
             


            } };


        Timer timer1 = new Timer();
        timer1.schedule(task1, 0,1234);

        Timer timer2 = new Timer();
        timer2.schedule(task2, 0,send);



}}
