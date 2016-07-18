package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class LoginToClientDispatcher {
public static void dispatch(DataInputStream dis, LoginToClientProxy proxy) throws Exception{
int fid = (int)(dis.read()&0xFFFF);
switch(fid)
{
case 0:
{
onLogin(dis, proxy);
}
break;
}
}
protected static void onLogin(DataInputStream dis, LoginToClientProxy proxy) throws Exception{
Player player;
player = new Player();
player.deserialize(dis);
proxy.onLogin(player);
}
}
