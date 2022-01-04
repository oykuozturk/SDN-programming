import java.io.*;
import java.net.*;
import java.util.*;
//6 icin
public class Router6 {
    private static ServerSocket serverSocket;
    private static InetAddress host;
    private static final int PORT = 2006;
    private static final int PORT2 = 2007;
    private static final int PORT3 = 2004;
    private static Socket link2 = null;

    public static void main(String[] args) {
        System.out.println("Port aciliyor");
        Random r = new Random();
        int randomSayi = r.nextInt(2);
        {
            try {
                host = InetAddress.getLocalHost();
                System.out.println("Receiver IP Adresini giriniz:");

            } catch (Exception uhEx) {
                System.out.println("Host ID adresi bulunamadi.");
                System.exit(1);
            }

        }
        if (randomSayi == 0) {
            try {
                serverSocket = new ServerSocket(PORT2); // Step 1.
                link2 = new Socket(host, PORT);
                System.out.println("Gidilen Router 7");
            } catch (IOException ioEx) {
                System.out.println(
                        "Receiver baglanti noktasina baglanamiyor");
                System.exit(1);
            }
        } else {
            try {
                serverSocket = new ServerSocket(PORT3); // Step 1.
                link2 = new Socket(host, PORT);
                System.out.println("Gidilen Router 4");
            } catch (IOException ioEx) {
                System.out.println(
                        "Receiver baglanti noktasina baglanamiyor");
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
            PrintWriter output = new PrintWriter(
                    link.getOutputStream(), true);

            String message = input.nextLine();

            Scanner input2 = new Scanner(link2.getInputStream());

            PrintWriter output2 = new PrintWriter(
                    link2.getOutputStream(), true);

            while (!message.equals("Kapatildi")) {
                System.out.println("Sender'in mesaji " + message);

                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(100);
                System.out.println("Paket icin uretilen rastgele sayi: " + randomInt);
                if (randomInt > 19) { // for random probability 20%,each packet has a random number between 0 to 99

                    output2.println(message);

                    String str = input2.nextLine();
                    System.out.println("Receiver'in mesaji: " + str);
                    output.println(str);
                } else {
                    output.println(str2);
                }
                message = input.nextLine();

            }

        }

        catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                System.out.println(
                        "\n* Baglanti kesiliyor...");
                link.close();
                link2.close();
            } catch (IOException ioEx) {
                System.out.println(
                        "Baglanti kesilemedi");
                System.exit(1);
            }
        }
        return null;
    }
}