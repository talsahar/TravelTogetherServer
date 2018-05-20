package boot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class ListenerSocket {

	ServerSocket listennerSocket;
	Thread thread;
	boolean isRunning;
	Consumer<Socket> onNewConnection;

	public ListenerSocket(int port, Consumer<Socket> onNewConnection) {
		this.onNewConnection = onNewConnection;
		try {
			listennerSocket = new ServerSocket(port);
			thread = new Thread(() -> {
				System.out.println("Server is running on port " + listennerSocket.getLocalPort());
				while (isRunning) {
					try {
						Socket socket = listennerSocket.accept();
						if (socket != null) {
							System.out.println("connection has been established");
							onNewConnection.accept(socket);
						}
					} catch (IOException e) {
						System.out.println(e);
                        isRunning = false;
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		isRunning = true;
		thread.start();
	}

	public void stop() {
		try {
			isRunning = false;
			listennerSocket.close();
			thread.join();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
