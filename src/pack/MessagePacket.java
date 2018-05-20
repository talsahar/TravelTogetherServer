package pack;

import java.util.Date;

import common.Protocol;

public class MessagePacket extends Packet {
	
	String message;

	public MessagePacket(String gid, String uid, String msg, Date date) {
		super(gid, uid, date);
		message = msg;
	}

	@Override
	public String toString() {
		return "" + Protocol.msgPACK + ","+uid + ","+message+"";
	}
}
