package common;

public class Protocol {

public static final int SERVERPORT = 5555;
public static String closeConnectionReq = "#BB?";
public static String closeConnectionRes = "#BBOK";
//FIRST MSG: "GROUPID,USERID"
//PackageFormat: "#COORDINATE,X,Y"
public static final String CoordinatePACK = "#COORDINATE";
//PackageFormat: "#MSG,message"
public static final String msgPACK = "#MSG";
    public static final String outofrangePACK = "#OutOfRange";

}
