package connectionHolders;

import java.util.HashMap;

import pack.Packet;

public class GroupHolder {

	HashMap<String, UserSocket> holder;

	public GroupHolder() {
		holder = new HashMap<String, UserSocket>();
	}

	public void addSocket(String id, UserSocket socket) {
		holder.put(id, socket);
	}

	public void removeClosed(String id) {
		holder.remove(id);
	}

	// send a package to all open connections except the package owner id
	public void sendToAllExcept(Packet pack) {
		holder.entrySet().stream().filter(map -> !map.getKey().equals(pack.getUid()))
				.forEach(entry -> entry.getValue().send(pack));
	}

	public void stop() {
		holder.values().forEach(sock -> sock.closeNow());
		holder.clear();
	}
}
