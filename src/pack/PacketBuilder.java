package pack;

import java.util.Date;

import common.Protocol;
import data.Coordinate;
import pack.CoordinatePacket;
import pack.MessagePacket;
import pack.Packet;

public class PacketBuilder {

	public static Packet buildByString(String gid, String uid, String msg) {
		Date date = new Date();
		String[] params = msg.split(",");
		switch (params[0]) {
		case Protocol.CoordinatePACK:
			Coordinate coordinate = new Coordinate(Double.parseDouble(params[1]), Double.parseDouble(params[2]));
			return new CoordinatePacket(gid,uid,coordinate,date);
		case Protocol.msgPACK:
			String msgContent = params[1];
			return new MessagePacket(gid,uid,msgContent,date);
			case Protocol.outofrangePACK:
				return new OutOfRangePacket(gid, uid, date);
		default:
			System.out.println("Unknown msg type: " + msg);
			return null;
		}
	}
}