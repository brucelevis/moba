namespace info.chenliang.moba.message{
using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
public abstract class BattleToPlayerStub {
protected abstract MemoryEndianBinaryWriter Begin();
protected abstract void End();
public void battlePing(){
MemoryEndianBinaryWriter writer = Begin();
writer.WriteByte(0);
End();
}
public void battlePong(){
MemoryEndianBinaryWriter writer = Begin();
writer.WriteByte(1);
End();
}
public void onJoinArena(Int32 arenaId,Sync sync,Int32 entityId){
MemoryEndianBinaryWriter writer = Begin();
writer.WriteByte(2);
writer.WriteInt32(arenaId);
sync.Serialize(writer);
writer.WriteInt32(entityId);
End();
}
public void sync(Sync sync){
MemoryEndianBinaryWriter writer = Begin();
writer.WriteByte(3);
sync.Serialize(writer);
End();
}
public void playerSpawned(Int32 entityId,SyncItem item){
MemoryEndianBinaryWriter writer = Begin();
writer.WriteByte(4);
writer.WriteInt32(entityId);
item.Serialize(writer);
End();
}
}
}
