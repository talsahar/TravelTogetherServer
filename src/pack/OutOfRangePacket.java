package pack;

import common.Protocol;

import java.util.Date;

public class OutOfRangePacket extends Packet {

    public OutOfRangePacket(String gid, String uid, Date date) {
        super(gid, uid, date);
    }

    @Override
    public String toString() {
        return "" + Protocol.outofrangePACK + ","+uid;
    }
}
