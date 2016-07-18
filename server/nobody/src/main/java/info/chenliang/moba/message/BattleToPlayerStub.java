package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public abstract class BattleToPlayerStub {
protected abstract DataOutputStream begin();
protected abstract void end();
public void battlePing() throws Exception{
DataOutputStream dos = begin();
dos.write(0);
end();
}
public void battlePong() throws Exception{
DataOutputStream dos = begin();
dos.write(1);
end();
}
public void onJoinArena(int arenaId,Snapshot snapshot) throws Exception{
DataOutputStream dos = begin();
dos.write(2);
dos.writeInt(arenaId);
snapshot.serialize(dos);
end();
}
public void sync(Sync sync) throws Exception{
DataOutputStream dos = begin();
dos.write(3);
sync.serialize(dos);
end();
}
}
