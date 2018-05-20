package boot;

import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		ServerManager server=new ServerManager();
		server.start();
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter 'q' to shutdown the server");
		while(!(scanner.nextLine()).equals("q"));
		scanner.close();
		server.stop();
	}
}
