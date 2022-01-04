import java.io.*;
import java.net.*;
import java.util.*;
public class Router3 {
    private static ServerSocket serverSocket;
    private static InetAddress host;
    private static final int PORT = 2003;
    private static final int PORT2 = 2009;
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
                System.out.println("Host ID bulunamadı!");
                System.exit(1);
            }
        }
            try {
                serverSocket = new ServerSocket(PORT2);
                link2 = new Socket(host, PORT);
                System.out.println("Gidilen Client");
            }
            catch (IOException ioEx) {
                System.out.println("Router bağlantı noktasına bağlanamıyor!");
                System.exit(1);
            }
        handleClient();
    }
    private static String handleClient() {
        Socket link = null;
        String str2 = null;
        try {
            link = serverSocket.accept();
            Scanner input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter( link.getOutputStream(), true);
            String message = input.nextLine();
            Scanner input2 = new Scanner(link2.getInputStream());
            PrintWriter output2 = new PrintWriter( link2.getOutputStream(), true);
            while (!message.equals("KAPALI")) {
                System.out.println("Sender mesaj:" + message);
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(100);
                System.out.println("Paket için oluşturulan rastgele sayı:" + randomInt);
                if (randomInt > 19) {
                    output2.println(message);
                    String str = input2.nextLine();
                    System.out.println("Receiver mesaj:" + str);
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
                System.out.println("Bağlantı kapatıldı.");
                link.close();
                link2.close();
            }
            catch (IOException ioEx) {
                System.out.println("Bağlantı kapatılamadı!");
                System.exit(1);
            }
        }
        return null;
    }
}
