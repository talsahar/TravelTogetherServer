package connectionHolders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

import pack.PacketBuilder;
import common.Protocol;
import pack.Packet;

public class UserSocket {

	String gid;
	String uid;
	Socket socket;
	Thread sockThread;
	PrintWriter out;
	BufferedReader in;
	Consumer<UserSocket> groupMsgDelegate; //the server first receive the "gid,uid"
	Consumer<Packet> packageDelegate; //[gid,uid,inputLine]
	Consumer<String[]> onClose; //[gid,uid]

	public UserSocket(Socket socket, Consumer<UserSocket> groupMsgDelegate, Consumer<Packet> packageDelegate, Consumer<String[]> onClose) {
		this.socket = socket;
		this.groupMsgDelegate = groupMsgDelegate;
		this.packageDelegate = packageDelegate;
		this.onClose = onClose;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sockThread = new Thread(() -> {
			try {
				readFirstMsg(in);
				String inputLine;
				while ((inputLine = in.readLine()) != null && 
						!inputLine.equals(Protocol.closeConnectionReq))
				{
					Packet pack = PacketBuilder.buildByString(gid, uid, inputLine);
					if(pack != null)
						packageDelegate.accept(pack);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			closeNow();
		});
	}

	private void readFirstMsg(BufferedReader in) throws IOException {
		String userInfo = in.readLine();
		String[] params = userInfo.split(",");
		gid = params[0];
		uid = params[1];
		groupMsgDelegate.accept(this);
		System.out.println("gid:"+gid+",uid:"+uid);
	}
	
	public void startReading() {
		sockThread.start();
	}

	public void send(Packet pack) {
		out.println(pack.toString());
	}

	public void closeNow() {
		String[] closeParams = {gid, uid};
		onClose.accept(closeParams);
		if(socket.isConnected()) {
			out.println(Protocol.closeConnectionRes);
			try {
				Thread.sleep(2000);
				socket.close();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
