
import java.io.*;
import java.net.*;
import java.util.*;

public class TcpSender extends Thread {
	private static InetAddress host;

	public static void main(String[] args) {
		try {
			host = InetAddress.getLocalHost();
			System.out.println("Router için IP Adresi giriniz:");
	
		} catch (Exception uhEx) {
			System.out.println("Girilen IP adresi bulunamýyor");
			System.exit(1);
		}
		accessServer();
	}

	private static void accessServer() {
		Socket link = null;
		try {
			link = new Socket(host, 2009);
			Scanner input = new Scanner(link.getInputStream());

			PrintWriter output = new PrintWriter(link.getOutputStream(), true);

			for (int i = 0; i < 6; i++) {

				System.out.println("Gönderilecek paket sayýsýný giriniz: ");
				Scanner userEntry = new Scanner(System.in);
				String message, str2, response;
				int number;

				response = userEntry.nextLine();
				number = Integer.parseInt(response);
				int counter = 0, attempt = 0;
				long startTime = System.nanoTime();
				do {

					message = "PCK";

					output.println(message + counter);
					attempt++;

					link.setSoTimeout(4000); 

					String str = input.nextLine();

					str2 = str.substring(0, 3);

					while (!str2.equals("ACK")) {
						System.out.println(message + counter + "Tekrar gönderiliyor...");
						output.println(message + counter);
						attempt++;
						str = input.nextLine();
						str2 = str.substring(0, 3);
					}

					System.out
							.println(str + " Reciver'dan baþarýlý bir þekilde alýndý");

					counter++;

				} while (counter < number);

				long endTime = System.nanoTime();
				System.out.println("Toplam deneme sayýsý: " + attempt);
				System.out.println("Tüm paketlerin gönderilme ve alýnma süresi: "
						+ (endTime - startTime) + " nanosaniye.");
			}
			output.println("Kapatýldý");
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		} finally {
			try {
				System.out.println("\n* Baðlantý kesiliyor...");
				link.close();
			} catch (IOException ioEx) {
				System.out.println("Baðlantý kesilemiyor");
				System.exit(1);
			}
		}
	}
}
