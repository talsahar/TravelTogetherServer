package connectionHolders;

import java.util.HashMap;

import pack.Packet;

// Maps key to each group
public class MainGroupHolder {

	HashMap<String, GroupHolder> holder;

	public MainGroupHolder() {
		holder = new HashMap<String, GroupHolder>();
	}

	public synchronized void addSocket(UserSocket socket) {
		if(!holder.containsKey(socket.gid)) {
			GroupHolder newGroup = new GroupHolder();
			newGroup.addSocket(socket.uid, socket);
			holder.put(socket.gid, newGroup);
		} else 
			holder.get(socket.gid).addSocket(socket.uid, socket);
	}

	public void send(Packet pack) {
		holder.get(pack.getGid()).sendToAllExcept(pack);
	}

	public void removeUserFromGroup(String gid, String uid) {
		holder.get(gid).removeClosed(uid);
	}

	public void stop() {
		holder.values().forEach(group -> group.stop());
		holder.clear();
	}

	
}
