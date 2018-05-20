package pack;

import java.util.Date;

public abstract class Packet {

	protected String gid;
	protected String uid;
	protected Date date;

	public Packet(String gid, String uid, Date date) {
		this.gid = gid;
		this.uid = uid;
		this.date = date;
	}

	public abstract String toString();

	public Date getDate() {
		return date;
	}

	public String getGid() {
		return gid;
	}

	public String getUid() {
		return uid;
	}

}
