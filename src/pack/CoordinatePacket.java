package pack;

import java.util.Date;

import common.Protocol;
import data.Coordinate;

public class CoordinatePacket extends Packet {
	
	Coordinate coordinate;
	
	public CoordinatePacket(String gid, String uid, Coordinate coordinate, Date date) {
		super(gid,uid,date);
		this.coordinate = coordinate;
	}
		
	@Override
	public String toString() {
		return "" + Protocol.CoordinatePACK + ","+uid + ","+coordinate.getX() + "," + coordinate.getY()+"";
	}
}
