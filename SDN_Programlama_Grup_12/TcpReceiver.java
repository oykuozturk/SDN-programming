import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;
public class TcpReceiver {
	private static ServerSocket serverSocket;
	private static final int PORT = 2001;
	private static final int PORT2 = 2006;
	public static int PortNo = 0;
	public static void main(String[] args) {
		System.out.println("Port açılıyor...");
		Random sayi = new Random();
		int randomSayi = sayi.nextInt(2);
		if (randomSayi == 0) {
			try {
				serverSocket = new ServerSocket(PORT);
				System.out.println("Gidilen Router 1 ");
			}
			catch (IOException ioEx) {
				System.out.println("Receiver bağlantı noktasina bağlanamıyor...");
				System.exit(1);
			}
		}
		else {
			try {
				serverSocket = new ServerSocket(PORT2);
				System.out.println("Gidilen Router 6 ");
			}
			catch (IOException ioEx) {
				System.out.println("Receiver bağlantı noktasina bağlanamıyor.");
				System.exit(1);
			}
		}
		handleRouter();
	}
	private static void handleRouter() {
		Socket link = null;
		try {
			link = serverSocket.accept();
			Scanner input = new Scanner(link.getInputStream());
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);
			int numMessages = 0;
			String message = input.nextLine();
			while (!message.equals("Kapatıldı."))
			{
				output.println("ACK" + numMessages);
				numMessages++;
				System.out.println(numMessages + ":" + message);
				message = input.nextLine();
			}
		}
		catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
		finally {
			try {
				System.out.println("Bağlanti kesiliyor...");
				link.close();
			} catch (IOException ioEx) {
				System.out.println("Baglantı kesilemiyor!");
				System.exit(1);
			}
		}
	}
}
