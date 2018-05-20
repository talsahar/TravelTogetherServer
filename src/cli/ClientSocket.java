package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import common.Protocol;
//Client socket for testing
public class ClientSocket {

	static String serverIp = "10.0.0.30";
	
	public static void readLoop(BufferedReader in) {
		try {
			String msg;
			while((msg = in.readLine()) != null) {
				System.out.println(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int portNumber = Protocol.SERVERPORT;
		try (
				Socket kkSocket = new Socket(serverIp, portNumber);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
				BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
				) {
			new Thread(() -> readLoop(in)).start();
			String input;
			while ((input = userIn.readLine()) != null)
				out.println(input);

			userIn.close();
			kkSocket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
