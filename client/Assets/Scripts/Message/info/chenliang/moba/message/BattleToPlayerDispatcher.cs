namespace info.chenliang.moba.message{
using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
public class BattleToPlayerDispatcher {
public static void Dispatch(MemoryEndianBinaryReader reader, BattleToPlayerProxy proxy) {
int fid = reader.ReadByte();
switch(fid)
{
case 0:
{
battlePing(reader, proxy);
}
break;
case 1:
{
battlePong(reader, proxy);
}
break;
case 2:
{
onJoinArena(reader, proxy);
}
break;
case 3:
{
sync(reader, proxy);
}
break;
case 4:
{
playerSpawned(reader, proxy);
}
break;
}
}
protected static void battlePing(MemoryEndianBinaryReader reader, BattleToPlayerProxy proxy){
proxy.battlePing();
}
protected static void battlePong(MemoryEndianBinaryReader reader, BattleToPlayerProxy proxy){
proxy.battlePong();
}
protected static void onJoinArena(MemoryEndianBinaryReader reader, BattleToPlayerProxy proxy){
Int32 arenaId;
Sync sync;
Int32 entityId;
arenaId = reader.ReadInt32();
sync = new Sync();
sync.Deserialize(reader);
entityId = reader.ReadInt32();
proxy.onJoinArena(arenaId,sync,entityId);
}
protected static void sync(MemoryEndianBinaryReader reader, BattleToPlayerProxy proxy){
Sync sync;
sync = new Sync();
sync.Deserialize(reader);
proxy.sync(sync);
}
protected static void playerSpawned(MemoryEndianBinaryReader reader, BattleToPlayerProxy proxy){
Int32 entityId;
SyncItem item;
entityId = reader.ReadInt32();
item = new SyncItem();
item.Deserialize(reader);
proxy.playerSpawned(entityId,item);
}
}
}
