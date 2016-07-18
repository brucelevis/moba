package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public abstract class PlayerToBattleStub {
protected abstract DataOutputStream begin();
protected abstract void end();
public void playerPing() throws Exception{
DataOutputStream dos = begin();
dos.write(0);
end();
}
public void playerPong() throws Exception{
DataOutputStream dos = begin();
dos.write(1);
end();
}
public void play() throws Exception{
DataOutputStream dos = begin();
dos.write(2);
end();
}
public void moveTo(float x,float z) throws Exception{
DataOutputStream dos = begin();
dos.write(3);
dos.writeFloat(x);
dos.writeFloat(z);
end();
}
}
