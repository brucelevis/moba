package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class ClientToLoginDispatcher {
public static void dispatch(DataInputStream dis, ClientToLoginProxy proxy) throws Exception{
int fid = (int)(dis.read()&0xFFFF);
switch(fid)
{
case 0:
{
login(dis, proxy);
}
break;
}
}
protected static void login(DataInputStream dis, ClientToLoginProxy proxy) throws Exception{
String username;
String password;
username = dis.readUTF();
password = dis.readUTF();
proxy.login(username,password);
}
}
