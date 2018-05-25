package boot;

import java.util.function.Consumer;
import common.Protocol;
import connectionHolders.MainGroupHolder;
import connectionHolders.UserSocket;
import connectionHolders.WaitingList;
import pack.PacketHandlerQueue;
import pack.Packet;
public class ServerManager {

	ListenerSocket listener;
	WaitingList waitingList;
	MainGroupHolder mainHolder;
	PacketHandlerQueue queue;

	public ServerManager() {
		Consumer<UserSocket> waitingListDelegate = userSocket -> mainHolder.addSocket(userSocket);
		waitingList = new WaitingList(waitingListDelegate);
		mainHolder = new MainGroupHolder();
		
		queue = new PacketHandlerQueue(20000, pack -> mainHolder.send(pack));

		listener = new ListenerSocket(Protocol.SERVERPORT, sock -> {
			Consumer<Packet> onNewPackageDelegate = (pack) -> queue.add(pack);
			Consumer<String[]> onCloseDelegate = (msg) -> mainHolder.removeUserFromGroup(msg[0], msg[1]);
			UserSocket userSocket = new UserSocket(sock, null, onNewPackageDelegate, onCloseDelegate);
			waitingList.add(userSocket);
			userSocket.startReading();
			//
		});
	}

	public void start() {
		listener.start();
		queue.start();
	}

	public void stop() {
		listener.stop();
		queue.stop();
		mainHolder.stop();
	}
}
