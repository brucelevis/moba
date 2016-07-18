package info.chenliang.moba.message;
import java.io.*;
import info.chenliang.talky.*;
public abstract class LoginToClientStub {
protected abstract DataOutputStream begin();
protected abstract void end();
public void onLogin(Player player) throws Exception{
DataOutputStream dos = begin();
dos.write(0);
player.serialize(dos);
end();
}
}
