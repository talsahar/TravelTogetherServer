package connectionHolders;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

//sockets waiting for routing info (gid,uid)
public class WaitingList {
	
	Set<UserSocket> waitingList;
	Consumer<UserSocket> onUpdate;
	
	public WaitingList(Consumer<UserSocket> onUpdate) {
		waitingList = new HashSet<UserSocket>();
		this.onUpdate = onUpdate;
	}
	
	public void add(UserSocket socket) {
		socket.groupMsgDelegate = sock -> {
			waitingList.remove(sock);
			onUpdate.accept(sock);
		};
		waitingList.add(socket);
	}
}