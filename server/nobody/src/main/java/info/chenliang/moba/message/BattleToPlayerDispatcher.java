package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public class BattleToPlayerDispatcher {
public static void dispatch(DataInputStream dis, BattleToPlayerProxy proxy) throws Exception{
int fid = (int)(dis.read()&0xFFFF);
switch(fid)
{
case 0:
{
battlePing(dis, proxy);
}
break;
case 1:
{
battlePong(dis, proxy);
}
break;
case 2:
{
onJoinArena(dis, proxy);
}
break;
case 3:
{
sync(dis, proxy);
}
break;
}
}
protected static void battlePing(DataInputStream dis, BattleToPlayerProxy proxy) throws Exception{
proxy.battlePing();
}
protected static void battlePong(DataInputStream dis, BattleToPlayerProxy proxy) throws Exception{
proxy.battlePong();
}
protected static void onJoinArena(DataInputStream dis, BattleToPlayerProxy proxy) throws Exception{
int arenaId;
Snapshot snapshot;
arenaId = dis.readInt();
snapshot = new Snapshot();
snapshot.deserialize(dis);
proxy.onJoinArena(arenaId,snapshot);
}
protected static void sync(DataInputStream dis, BattleToPlayerProxy proxy) throws Exception{
Sync sync;
sync = new Sync();
sync.deserialize(dis);
proxy.sync(sync);
}
}
