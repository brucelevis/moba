package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public abstract class BattleToPlayerProxy {
public abstract void battlePing();
public abstract void battlePong();
public abstract void onJoinArena(int arenaId,Snapshot snapshot);
public abstract void sync(Sync sync);
}
