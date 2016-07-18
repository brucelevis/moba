using System;
using System.IO;
using MiscUtil;
using MiscUtil.IO;
using MiscUtil.Conversion;
using Talky;
namespace info.chenliang.moba.message{
public abstract class BattleToPlayerProxy {
public abstract void battlePing();
public abstract void battlePong();
public abstract void onJoinArena(Int32 arenaId,Sync sync,Int32 entityId);
public abstract void sync(Sync sync);
public abstract void playerSpawned(Int32 entityId,SyncItem item);
}
}
