
import java.io.*;
import java.net.*;
import java.util.*;
public class Router2 {
    private static ServerSocket serverSocket;
    private static InetAddress host;
    private static final int PORT = 2002;
    private static final int PORT2 = 2003;
    private static final int PORT3 = 2005;
    private static Socket link2 = null;
    public static void main(String[] args) {
        System.out.println("Port Açık");
        Random sayi = new Random();
        int randomSayi = sayi.nextInt(2);
        {
            try {
                host = InetAddress.getLocalHost();
                System.out.println("Receiver IP adresini girin:");
            }
            catch (Exception uhEx) {
                System.out.println("Host ID bulunamadi!");
                System.exit(1);
            }
        }
        if (randomSayi == 0) {
            try {
                serverSocket = new ServerSocket(PORT2);
                link2 = new Socket(host, PORT);
                System.out.println("Gidilen Router 3");
            }
            catch (IOException ioEx) {
                System.out.println("Router baglanti noktasina baglanamiyor!");
                System.exit(1);
            }
        }
        else {
            try {
                serverSocket = new ServerSocket(PORT3);
                link2 = new Socket(host, PORT);
                System.out.println("Gidilen Router 5");
            }
            catch (IOException ioEx) {
                System.out.println("Router baglanti noktasina baglanamiyor!");
                System.exit(1);
            }
        }
        handleClient();
    }
    private static String handleClient() {
        Socket link = null;
        String str2 = null;
        try {
            link = serverSocket.accept();
            Scanner input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);
            String message = input.nextLine();
            Scanner input2 = new Scanner(link2.getInputStream());
            PrintWriter output2 = new PrintWriter(link2.getOutputStream(), true);
            while (!message.equals("KAPALI")) {
                System.out.println("Sender mesaj:"+ message);
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(100);
                System.out.println("Paket icin olusturulan rastgele sayi:" + randomInt);
                if (randomInt > 19) {
                    output2.println(message);
                    String str = input2.nextLine();
                    System.out.println("Receiver mesaj:"+ str);
                    output.println(str);
                }
                else {
                    output.println(str2);
                }
                message = input.nextLine();
            }
        }
        catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        finally {
            try {
                System.out.println("Baglanti kapatildi.");
                link.close();
                link2.close();
            }
            catch (IOException ioEx) {
                System.out.println("Baglanti kapatilamadi!");
                System.exit(1);
            }
        }
        return null;
    }
}
