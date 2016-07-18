package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class PlayerToBattleDispatcher {
public static void dispatch(DataInputStream dis, PlayerToBattleProxy proxy) throws Exception{
int fid = (int)(dis.read()&0xFFFF);
switch(fid)
{
case 0:
{
playerPing(dis, proxy);
}
break;
case 1:
{
playerPong(dis, proxy);
}
break;
case 2:
{
play(dis, proxy);
}
break;
case 3:
{
moveTo(dis, proxy);
}
break;
}
}
protected static void playerPing(DataInputStream dis, PlayerToBattleProxy proxy) throws Exception{
proxy.playerPing();
}
protected static void playerPong(DataInputStream dis, PlayerToBattleProxy proxy) throws Exception{
proxy.playerPong();
}
protected static void play(DataInputStream dis, PlayerToBattleProxy proxy) throws Exception{
proxy.play();
}
protected static void moveTo(DataInputStream dis, PlayerToBattleProxy proxy) throws Exception{
float x;
float z;
x = dis.readFloat();
z = dis.readFloat();
proxy.moveTo(x,z);
}
}
